package com.scloudyy.springbackend.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ProductExecution;
import com.scloudyy.springbackend.entity.Product;
import com.scloudyy.springbackend.entity.ProductCategory;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.enums.ProductStateEnum;
import com.scloudyy.springbackend.exceptions.ProductOperationException;
import com.scloudyy.springbackend.service.ProductCategoryService;
import com.scloudyy.springbackend.service.ProductService;
import com.scloudyy.springbackend.util.CodeUtil;
import com.scloudyy.springbackend.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private final ProductCategoryService productCategoryService;
    private static final int IMAGEMAXCOUNT = 6;

    @Autowired
    public ProductManagementController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
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

    /**
     * 通过商品id获取商品信息
     *
     * @param productId
     * @return
     */
    @GetMapping("/getproductbyid")
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 非空判断
        if (productId > -1) {
            // 获取商品信息
            Product product = productService.getProduct(productId);
            // 获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    /**
     * 商品编辑
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 是商品编辑时候调用还是上下架操作的时候调用
        // 若为前者则进行验证码判断，后者则跳过验证码判断
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        // 验证码判断
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImgList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            // 尝试获取前端传过来的表单string流并将其转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 非空判断
        if (product != null) {
            try {
                // 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 开始进行商品信息变更操作
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 通过店铺id获取该店铺下的商品列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取前台传过来的每页要求返回的商品数上限
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 从当前session中获取店铺信息，主要是获取shopId
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            // 获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
            // 筛选的条件可以进行排列组合
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
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

    /**
     * 封装商品查询条件到Product实例中
     *
     * @param shopId(mandatory)
     * @param productCategoryId(optional)
     * @param productName(optional)
     * @return
     */
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        // 若有指定类别的要求则添加进去
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        // 若有商品名模糊查询的要求则添加进去
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}
