package cn.hm.haohuigou.service;

import cn.hm.haohuigou.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author huming
 * @since 2019-02-27
 */
public interface IProductTypeService extends IService<ProductType> {

    List<ProductType> treeData();

}
