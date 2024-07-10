package com.yzn.YznSpring.test;

import com.yzn.YznSpring.Spring.Autowired;
import com.yzn.YznSpring.Spring.Component;
import com.yzn.YznSpring.Spring.Scope;

/**
 * ClassName: UserService
 * Package: com.yzn.YznSpring.test
 * Description:
 *
 * @Author yzn
 * @Create 2024/7/9 21:46
 * @Version: 1.0
 */
@Component("userService")
//@Scope("prototype")
public class UserService {
    @Autowired
    private OrderMapper orderMapper;
    public String show(){
        System.out.println(orderMapper.getById());
        return "hello yznSpring";
    }

}
