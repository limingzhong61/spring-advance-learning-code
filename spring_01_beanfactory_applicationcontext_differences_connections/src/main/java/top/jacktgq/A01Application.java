package top.jacktgq;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;


/**
 * @Author XiYue
 * @Date 2022/3/23--12:49
 * @Description BeanFactory与ApplicationContext的区别和联系
 */
@SpringBootApplication
public class A01Application {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(A01Application.class, args);
        /**
         * 1. 到底什么是BeanFactory
         *      - 它是ApplicationContext的父接口
         *      - 它才是 Spring 的核心容器，主要的 ApplicationContext 实现都 [组合]了他的功能
         */
        // 按 Ctrl + Alt + B 可以跳转到方法的实现中
        // System.out.println(context.getBean(Component1.class));
        // class org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext
        System.out.println(context.getClass());
        /**
         * 2. BeanFactory能干点啥
         *      - 表面上只有getBean
         *      - 实际上控制反转、基本的依赖注入、直至Bean的生命周期的各种功能都有它的实现类提供
         */
        Class<DefaultSingletonBeanRegistry> clazz = DefaultSingletonBeanRegistry.class;
        // DefaultSingletonBeanRegistry defaultSingletonBeanRegistry = clazz.getConstructor(null).newInstance(null);
        Field singletonObjects = (Field) clazz.getDeclaredField("singletonObjects");
        // 设置私有变量可以被访问
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 查看实际类型
        System.out.println(beanFactory.getClass());
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        // Map<String, Object> map = (Map<String, Object>) singletonObjects.get(defaultSingletonBeanRegistry);
        map.entrySet().stream().filter(entry -> entry.getKey().startsWith("component")).forEach(System.out::println);


        /**
         * 3. ApplicationContext 比 BeanFactory 多点啥
         * MessageSource: 国际化功能，支持多种语言
         * ResourcePatternResolver: 通配符匹配资源路径
         * EnvironmentCapable:  环境信息，系统环境变量，*.properties、*.application.yml等配置文件中的值
         * ApplicationEventPublisher: 发布事件对象
         */

        // MessageSource
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        // ResourcePatternResolver
        Resource[] resources = context.getResources("classpath:messages*.properties");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        // EnvironmentCapable
        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));

        // ApplicationEventPublisher
        // context.publishEvent(new UserRegisteredEvent(context));
        UserService userService = context.getBean(UserService.class);
        userService.register("张三", "123456");

        /**
         * 4. 学到了什么
         *      a. BeanFactory 与 ApplicationContext 不不仅仅是简单接口继承的关系，
         *         ApplicationContext 组合并扩展了 BeanFactory的功能
         *      练习：完成用户注册与发送短信之间的解耦，用事件方式和 AOP 方式分别实现
         */
    }
}
