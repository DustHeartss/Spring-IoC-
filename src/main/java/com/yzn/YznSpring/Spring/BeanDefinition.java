package com.yzn.YznSpring.Spring;

/**
 * ClassName: BeanDefinition
 * Package: com.yzn.YznSpring.Spring
 * Description:
 * 用来存放bean的属性信息
 * @Author yzn
 * @Create 2024/7/9 22:47
 * @Version: 1.0
 */
public class BeanDefinition {
    private Class clazz;
    private String scope;//多例 or 单例

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
