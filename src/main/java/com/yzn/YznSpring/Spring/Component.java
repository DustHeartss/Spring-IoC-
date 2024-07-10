package com.yzn.YznSpring.Spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: Component
 * Package: com.yzn.YznSpring.Spring
 * Description:
 *
 * @Author yzn
 * @Create 2024/7/9 21:47
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String value() default "";
}
