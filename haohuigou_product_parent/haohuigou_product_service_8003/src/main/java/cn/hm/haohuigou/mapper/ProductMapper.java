package cn.hm.haohuigou.mapper;

import cn.hm.haohuigou.domain.Product;
import cn.hm.haohuigou.query.ProductQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-02-27
 */
public interface ProductMapper extends BaseMapper<Product> {

    long queryPageCount(ProductQuery query);

    List<Product> queryPage(ProductQuery query);

}
