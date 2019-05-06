package com.scloudyy.springbackend.web.shopadmin;

import com.google.protobuf.ProtocolMessageEnum;
import com.scloudyy.springbackend.dto.ProductCategoryExecution;
import com.scloudyy.springbackend.dto.Result;
import com.scloudyy.springbackend.entity.ProductCategory;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.enums.ProductCategoryStateEnum;
import com.scloudyy.springbackend.enums.ShopStateEnum;
import com.scloudyy.springbackend.exceptions.ProductCategoryOperationException;
import com.scloudyy.springbackend.service.ProductCategoryService;
import com.scloudyy.springbackend.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

    @PostMapping("/addproductcategorys")
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                    HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() > 0) {
            for (ProductCategory pc : productCategoryList) {
                pc.setShopId(currentShop.getShopId());
                pc.setCreateTime(new Date());
            }
            if (productCategoryList != null && productCategoryList.size() > 0) {
                try {
                    ProductCategoryExecution execution = productCategoryService.batchAddProductCategory(productCategoryList);
                    if (execution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", execution.getStateInfo());
                    }
                } catch (ProductCategoryOperationException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.getMessage());
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "empty category list");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Invalid shop");
        }
        return modelMap;
    }

    @PostMapping("/removeproductcategory")
    @ResponseBody
    private Map<String, Object> removeProductCategory(long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() > 0) {
            try {
                ProductCategoryExecution execution = productCategoryService.deleteProductCategory(productCategoryId,
                        currentShop.getShopId());
                if (execution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "invalid shop");
        }
        return modelMap;
    }
}
