package com.scloudyy.springbackend.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @GetMapping("/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }

    @GetMapping("/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    @GetMapping("/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }

    @GetMapping("/productcategorymanagement")
    private String productCategoryManagement() {
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation() {
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement")
    public String productManagement() {
        return "shop/productmanagement";
    }
}
