package cn.hm.haohuigou.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "COMMON-PROVIDER",fallbackFactory = PageStaticFactory.class) //表示对哪一个服务进行处理
@RequestMapping("/common")
public interface PageStaticClient {

    /**
     * 生成模板:
     *  根据给定的数据,和指定的模板,最终生成一个html页面;
     *  RequestBody:只能有一个
     * @param map
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    void getPageStatic(@RequestBody Map<String, Object> map);


}
