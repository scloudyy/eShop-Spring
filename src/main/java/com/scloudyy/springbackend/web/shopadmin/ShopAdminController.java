package com.scloudyy.springbackend.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @GetMapping("/shopoperation")
    public String ShopOperation() {
        return "shop/shopoperation";
    }
}
