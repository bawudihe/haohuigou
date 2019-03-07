package cn.hm.haohuigou.service;

import cn.hm.haohuigou.domain.Product;
import cn.hm.haohuigou.query.ProductQuery;
import cn.hm.haohuigou.util.PageList;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author huming
 * @since 2019-02-27
 */
public interface IProductService extends IService<Product> {

    PageList<Product> queryPage(ProductQuery query);
    /**
     *
     * @param productId 商品的id
     * @param skuProperties  sku的属性表里的数据
     * @param skuDatas  sku的值
     */
    void addSku(Object productId, List<Map<String, Object>> skuProperties, List<Map<String, Object>> skuDatas);
}
