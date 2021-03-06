package cn.hm.haohuigou.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author huming
 * @since 2019-03-05
 */
@TableName("t_specification")
public class Specification extends Model<Specification> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 规格名称
     */
    private String specName;
    /**
     * 1:显示属性；2：sku属性
     */
    private Integer type;
    @TableField("product_type_id")
    private Long productTypeId;
    //用于在添加、修改显示属性的时候，接受值的，与数据库的表没有关系
    @TableField(exist = false)
    private String value;
    @TableField(exist = false)
    private List<String> skuValues=new ArrayList<>();

    public List<String> getSkuValues() {
        return skuValues;
    }

    public void setSkuValues(List<String> skuValues) {
        this.skuValues = skuValues;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Specification{" +
        ", id=" + id +
        ", specName=" + specName +
        ", type=" + type +
        ", productTypeId=" + productTypeId +
        "}";
    }
}
