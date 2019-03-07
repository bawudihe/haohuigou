package cn.hm.haohuigou.mapper;

import cn.hm.haohuigou.domain.Brand;
import cn.hm.haohuigou.query.BrandQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-02-27
 */
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 分页条件数据
     * @param query
     * @return
     */
    List<Brand> queryPage(BrandQuery query);


    /**
     * 分页条件查询:总的条数
     * @param query
     * @return
     */
    long queryPageCount( BrandQuery query);
}
