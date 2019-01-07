package com.scloudyy.springbackend.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scloudyy.springbackend.dao.ProductDao;
import com.scloudyy.springbackend.dao.ProductImgDao;
import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ProductExecution;
import com.scloudyy.springbackend.entity.Product;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.enums.ProductStateEnum;
import com.scloudyy.springbackend.exceptions.ProductOperationException;
import com.scloudyy.springbackend.service.ProductService;
import com.scloudyy.springbackend.util.CodeUtil;
import com.scloudyy.springbackend.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    private final ProductService productService;

    private static final int IMAGEMAXCOUNT = 6;

    @Autowired
    public ProductManagementController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addproduct")
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Wrong verify code");
            return modelMap;
        }
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImgList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = objectMapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (thumbnail != null && productImgList.size() > 0 && product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Please input product information");
            return modelMap;
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
        throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                ImageHolder imageHolder = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                productImgList.add(imageHolder);
            }
        }
        return thumbnail;
    }
}
