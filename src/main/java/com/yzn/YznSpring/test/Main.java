package com.yzn.YznSpring.test;

import com.yzn.YznSpring.Spring.YznApplicationContext;

/**
 * ClassName: Main
 * Package: com.yzn.YznSpring.test
 * Description:
 *
 * @Author yzn
 * @Create 2024/7/9 21:41
 * @Version: 1.0
 */
public class Main {
    public static void main(String[] args) {
        YznApplicationContext applicationContext = new YznApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        UserService userService2 = (UserService) applicationContext.getBean("userService");
        System.out.println(userService2 == userService);
        System.out.println("userService = " + userService);
        System.out.println(userService.show());
    }
}
