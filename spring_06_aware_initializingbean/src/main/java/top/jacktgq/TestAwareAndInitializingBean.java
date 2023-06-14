package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @Author XiYue
 * @Date 2022/3/24--21:20
 * @Description
 */
@Slf4j
public class TestAwareAndInitializingBean {
    @Test
    public void testAware() throws Exception {
        /**
         * 1. Aware 接口用于注入一些与容器相关信息，例如
         *      a. BeanNameAware 注入 Bean 的名字
         *      b. BeanFactoryAware 注入 BeanFactory 容器
         *      c. ApplicationContextAware 注入 ApplicationContext 容器
         *      d. EmbeddedValueResolverAware 注入 解析器，解析${}
         */
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("myBean", MyBean.class);
        // 解析 @Autowired 注解的Bean后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 解析 @PostConstruct 注解的Bean后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        context.refresh();

        context.close();

        /**
         * 2. 有人可能会问：b、c、d的功能用 @Autowired注解就能实现啊，为啥还要用 Aware 接口呢？
         *      InititalizingBean 接口可以用 @PostConstruct注解实现
         *      简单得说：
         *          a. @Autowired 的解析需要用到 Bean 后处理器，属于扩展功能
         *          b. 而 Aware 接口属于内置功能，不加任何扩展，Spring就能识别
         *      某些情况下，扩展功能会失效，而内置功能不会失效
         *          例1：比如没有把解析@Autowired和@PostStruct注解的Bean的后处理器加到Bean工厂中，
         *          你会发现用 Aware 注入 ApplicationContext 成功， 而 @Autowired 注入 ApplicationContext 失败
         */
    }

    @Test
    public void testAware_MyConfig1() {
        GenericApplicationContext context = new GenericApplicationContext();
        // MyConfig1没有加上@
        context.registerBean("myConfig1", MyConfig1.class);
        // 解析 @Autowired 注解的Bean后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 解析 @PostConstruct 注解的Bean后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 解析@ComponentScan、@Bean、@Import、@ImportResource注解的后处理器
        // 这个后处理器不加出不来效果
        context.registerBean(ConfigurationClassPostProcessor.class);

        /**
         * 例2：Java配置类在添加了 bean 工厂后处理器后，
         *      你会发现用传统接口方式的注入和初始化依然成功，而 @Autowired 和 @PostConstruct 的注入和初始化失败
         */

        // 1. 添加beanfactory后处理器；2. 添加bean后处理器；3. 初始化单例。
        context.refresh();

        context.close();
    }

    @Test
    public void testAutowiredAndInitializingBean_MyConfig2() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("myConfig2", MyConfig2.class);

        /**
         * 例2：Java配置类在添加了 bean 工厂后处理器后，
         *      你会发现用传统接口方式的注入和初始化依然成功，而 @Autowired 和 @PostConstruct 的注入和初始化失败
         */

        // 1. 添加beanfactory后处理器；2. 添加bean后处理器；3. 初始化单例。
        context.refresh();

        context.close();

        /**
         *  学到了什么
         *      a. Aware 接口提供了一种【内置】 的注入手段，可以注入 BeanFactory，ApplicationContext
         *      b. InitializingBean 接口提供了一种 【内置】 的初始化手段
         *      c 内置的注入和初始化不收扩展功能的影响，总会被执行，因此 spring 框架内部的类常用它们
         */
    }
}

