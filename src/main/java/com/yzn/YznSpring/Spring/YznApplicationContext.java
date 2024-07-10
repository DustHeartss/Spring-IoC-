package com.yzn.YznSpring.Spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: YznApplicationContext
 * Package: com.yzn.YznSpring.Spring
 * Description:
 *
 * @Author yzn
 * @Create 2024/7/9 21:42
 * @Version: 1.0
 */
public class YznApplicationContext<T> {
    private Class appConfig;
    /**
     * key:beanName value:bean的属性信息
     */
    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String,T> singletonBeanMap = new HashMap<>();
    public <T> T getBean(String beanName){
        if (beanDefinitionMap.containsKey(beanName)) {
            String scope = beanDefinitionMap.get(beanName).getScope();
            if (scope.equals("singleton")){
                //单例
                T t = (T) singletonBeanMap.get(beanName);
                return t;
            }else {
                //非单例
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                Object newInstance = getNewInstance(beanDefinition.getClazz(),beanDefinition);
                return (T) newInstance;
            }
        }else {
            throw new NullPointerException("未找到beanName");
        }
    }

    public YznApplicationContext(Class appConfig) {
        this.appConfig = appConfig;
        if (!appConfig.isAnnotationPresent(ComponentScan.class)){
            //没有注解
            throw new RuntimeException("无法找到Bean路径!");
        }
        //解析配置类
        ComponentScan componentScan = (ComponentScan) appConfig.getAnnotation(ComponentScan.class);
        String path = componentScan.value();
        System.out.println("value = " + path);

        //扫描 -->寻找带@Component注解的 -> 需要扫描appClassLoader下的目录 ->将bean类信息放到beanDefinitionMap中
        String newPath = path.replace(".", "/");
        ClassLoader classLoader = YznApplicationContext.class.getClassLoader();
        URL url = classLoader.getResource(newPath);
        File file = new File(url.getFile());
        File[] files = file.listFiles();
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            if (absolutePath.endsWith(".class")) {
                String filePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"))
                        .replace("\\", ".");
                Class clazz = null;
                try {
                    clazz = classLoader.loadClass(filePath);
                    if (clazz.isAnnotationPresent(Component.class)) {
                        //将bean类信息放到beanDefinitionMap中
                        BeanDefinition beanDefinition = new BeanDefinition();
                        Component component = (Component) clazz.getAnnotation(Component.class);
                        String beanName = component.value();
                        if (clazz.isAnnotationPresent(Scope.class)){
                            //非单例
                            Scope scope = (Scope) clazz.getAnnotation(Scope.class);
                            String scopeValue = scope.value();
                            beanDefinition.setClazz(clazz);
                            beanDefinition.setScope(scopeValue);
                            beanDefinitionMap.put(beanName,beanDefinition);
                            if ("singleton".equals(scopeValue)){
                                //单例
                                createSingleton(beanDefinition,clazz,beanName);
                            }
                        }else {
                            //单例
                            createSingleton(beanDefinition, clazz, beanName);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

    private Object getNewInstance(Class clazz, BeanDefinition beanDefinition)  {
        try {
            Object o = clazz.getConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    //依赖注入
                    Autowired autowired = field.getAnnotation(Autowired.class);
//                    if (autowired.){
                        //找不到 是否报错
//                        if (this.beanDefinitionMap.containsKey(field.getName())) {
//                            BeanDefinition beanDefinition1 = beanDefinitionMap.get(field.getName());
//                            Class newBean = beanDefinition1.getClazz();
//                            field.setAccessible(true);
//                            field.set(o,newBean.getConstructor().newInstance());
//                        }else {
//                            throw new NullPointerException("未找到Bean");
//                        }
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(o,bean);
//                    }
                }else {
                    throw new NullPointerException("找不到Bean");
                }
            }
            return o;

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSingleton(BeanDefinition beanDefinition, Class clazz, String beanName) {
        //单例
        beanDefinition.setClazz(clazz);
        beanDefinition.setScope("singleton");
        beanDefinitionMap.put(beanName, beanDefinition);
        try {
            Object o = getNewInstance(clazz,beanDefinition);
            singletonBeanMap.put(beanName, (T) o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
