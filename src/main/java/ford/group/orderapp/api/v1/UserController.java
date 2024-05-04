package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.user.AuthRequestDTO;
import ford.group.orderapp.dto.user.UserInfoDto;
import ford.group.orderapp.service.security.JwtService;
import ford.group.orderapp.service.security.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    UserController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserInfoDto> addNewUser(@RequestBody UserInfoDto userInfo) {
        UserInfoDto response = service.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.email());
            return ResponseEntity.ok(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}