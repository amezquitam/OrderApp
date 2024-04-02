package ford.group.orderapp.api.v1;


import ford.group.orderapp.service.ShippingDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/shipping")
public class ShippingDetailController {
    ShippingDetailService shippingDetailService;

    public ShippingDetailController(ShippingDetailService shippingDetailService) {
        this.shippingDetailService = shippingDetailService;
    }


}
