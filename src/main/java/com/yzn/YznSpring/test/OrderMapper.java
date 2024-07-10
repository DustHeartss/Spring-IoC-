package com.yzn.YznSpring.test;

import com.yzn.YznSpring.Spring.Component;

/**
 * ClassName: OrderMapper
 * Package: com.yzn.YznSpring.test
 * Description:
 *
 * @Author yzn
 * @Create 2024/7/10 13:09
 * @Version: 1.0
 */
@Component("orderMapper")
public class OrderMapper {
    public String getById(){
        return "数据库查询中....";
    }
}
