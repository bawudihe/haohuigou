package cn.hm.haohuigou.controller;

import cn.hm.haohuigou.constants.GlobelConstants;
import cn.hm.haohuigou.domain.Brand;
import cn.hm.haohuigou.domain.ProductExt;
import cn.hm.haohuigou.domain.Specification;
import cn.hm.haohuigou.service.IProductExtService;
import cn.hm.haohuigou.service.IProductService;
import cn.hm.haohuigou.domain.Product;
import cn.hm.haohuigou.query.ProductQuery;
import cn.hm.haohuigou.service.ISpecificationService;
import cn.hm.haohuigou.util.AjaxResult;
import cn.hm.haohuigou.util.PageList;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sun.xml.internal.ws.runtime.config.TubelineFeatureReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.AssertFalse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ISpecificationService specificationService;
    @Autowired
    private IProductExtService productExtService;

    /**
     * 保存和修改公用的
     *
     * @param product 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product) {
        try {
            if (product.getId() != null) {
                productService.updateById(product);
            } else {
                productService.insert(product);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            productService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id) {
        return productService.selectById(id);
    }


    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Product> list() {

        return productService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query) {
        PageList<Product> pageList = productService.queryPage(query);
        return pageList;
    }

    //根据产品分类获取分类的显示属性：服务是给前台调用，不用feign
    @RequestMapping(value = "/viewProperties/{productTypeId}/{productId}", method = RequestMethod.GET)
    //要判断是新增还是修改: 判断是对当前产品的显示属性是添加还是新增:
    //productExt表中有viewProperties:修改 :需要前台传productId给我
    public List<Specification> viewProperties(@PathVariable("productTypeId") Long productTypeId, @PathVariable("productId") Long productId) {
        ProductExt productExt = productExtService.selectOne(new EntityWrapper<ProductExt>().eq("productId", productId));
        if (productExt != null && productExt.getViewProperties()!=null&& !productExt.getViewProperties().isEmpty()) {
            //有显示属性:是修改
            String strArrJson = productExt.getViewProperties();
            return JSONArray.parseArray(strArrJson, Specification.class);
        } else {
            return specificationService.selectList(new EntityWrapper<Specification>().eq("product_type_id", productTypeId).eq("type", 1));
        }

    }

    //存到t_product_ext的viewProperties，需要根据productId过滤
    @RequestMapping(value = "/saveViewProperties", method = RequestMethod.POST)
    public AjaxResult saveViewProperties(@RequestBody Map<String, Object> map) {
        try {
            Object productId = map.get(GlobelConstants.PRODUCTID);
            List<Specification> viewProperties = (List<Specification>) map.get(GlobelConstants.VIEWPROPERTIES);
            String jsonString = JSONArray.toJSONString(viewProperties);
            ProductExt productExt = new ProductExt();
            productExt.setViewProperties(jsonString);
            productExtService.update(productExt, new EntityWrapper<ProductExt>().eq("productId", productId));
            return AjaxResult.me().setMsg("显示属性保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("显示属性保存失败" + e.getMessage());
        }

    }
    //根据产品分类获取分类的sku属性：服务是给前台调用，不用feign
    @RequestMapping(value = "/skuProperties/{productTypeId}", method = RequestMethod.GET)
    //要判断是新增还是修改: 判断是对当前产品的显示属性是添加还是新增:
    //productExt表中有viewProperties:修改 :需要前台传productId给我
    public List<Specification> skuProperties(@PathVariable("productTypeId") Long productTypeId) {
        return specificationService.selectList(new EntityWrapper<Specification>().eq("product_type_id", productTypeId).eq("type", 2));
        }

    /**
     *
     * let params =
     * {"productId": productId, "skuProperties": this.skuProperties,"skuDatas":this.skuDatas};
     *
     * productId:63
     skuProperties:
     [
     {id=33, specName=颜色, type=2, productTypeId=9, value=null,skuValues=[yellow, green]},
     {id=34, specName=尺寸, type=2, productTypeId=9, value=null, skuValues=[26, 96]}
     ]

     skuDatas:
     [
     {颜色=yellow, 尺寸=26, price=26, availableStock=26},
     {颜色=yellow, 尺寸=96, price=96, availableStock=96},
     {颜色=green, 尺寸=26, price=62, availableStock=62},
     {颜色=green, 尺寸=96, price=69, availableStock=69}
     ]

     * @param map
     * @return
     */
    @RequestMapping(value = "/skuProperties",method = RequestMethod.POST)
    public AjaxResult saveSkuProperties(@RequestBody  Map<String,Object> map)
    {
        try {
            Object productId = map.get("productId");
            List<Map<String,Object>> skuProperties = (List<Map<String,Object>>) map.get("skuProperties");// List<>
            List<Map<String,Object>> skuDatas = (List<Map<String, Object>>) map.get("skuDatas");


            //调用方法:
            productService.addSku(productId,skuProperties,skuDatas);
            return AjaxResult.me().setMsg("SKU属性保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("SKU属性保存失败:"+e.getMessage());
        }

    }

    }

