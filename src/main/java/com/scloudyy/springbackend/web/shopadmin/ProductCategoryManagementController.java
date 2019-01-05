package com.scloudyy.springbackend.web.shopadmin;

import com.scloudyy.springbackend.dto.Result;
import com.scloudyy.springbackend.entity.ProductCategory;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.enums.ShopStateEnum;
import com.scloudyy.springbackend.service.ProductCategoryService;
import com.scloudyy.springbackend.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    private final ProductCategoryService productCategoryService;


    @Autowired
    public ProductCategoryManagementController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/getproductcategorylist")
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Result<List<ProductCategory>> res = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            try {
                List<ProductCategory> list = productCategoryService.getProductCategoryList(currentShop.getShopId());
                res = new Result<>(true, list);
            } catch (Exception e) {
                res = new Result<>(false, ShopStateEnum.INNER_ERROR.getState(), e.getMessage());
            }
        } else {
            ShopStateEnum ss = ShopStateEnum.INNER_ERROR;
            res = new Result<>(false, ss.getState(), ss.getStateInfo());
        }
        return res;
    }
}
