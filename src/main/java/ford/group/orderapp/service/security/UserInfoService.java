package ford.group.orderapp.service.security;

import ford.group.orderapp.dto.user.UserInfoDto;
import ford.group.orderapp.entities.ERole;
import ford.group.orderapp.entities.Role;
import ford.group.orderapp.entities.User;
import ford.group.orderapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserInfoService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("");
        }

        return new UserInfo(userOptional.get());
    }

    public UserInfoDto addUser(UserInfoDto userInfo) {
        User user = new User(null, userInfo.name(), userInfo.email(), passwordEncoder.encode(userInfo.password()),
                Arrays.stream(userInfo.roles().split(" "))
                        .map(role -> new Role(ERole.valueOf(role)))
                        .collect(Collectors.toSet())
        );
        user = userRepository.save(user);
        return new UserInfoDto(user.getUsername(), user.getEmail(), userInfo.password(), userInfo.roles());
    }
}
