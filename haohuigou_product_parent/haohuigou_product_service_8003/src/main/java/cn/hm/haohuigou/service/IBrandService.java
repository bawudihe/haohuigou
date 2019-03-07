package cn.hm.haohuigou.service;

import cn.hm.haohuigou.domain.Brand;
import cn.hm.haohuigou.query.BrandQuery;
import cn.hm.haohuigou.util.PageList;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author huming
 * @since 2019-02-27
 */
public interface IBrandService extends IService<Brand> {

    PageList<Brand> queryPage(BrandQuery query);

}
