package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @Author XiYue
 * @Date 2022/3/24--21:20
 * @Description
 */
public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // bean 的定义（即bean的一些描述信息，包含class：bean是哪个类，scope：单例还是多例，初始化、销毁方法等）
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);
        // 给 BeanFactory添加一些常用的后处理器，让它具备解析@Configuration、@Bean等注解的能力
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        // 从bean工厂中取出BeanFactory的后处理器，并且执行这些后处理器
        // BeanFactory 后处理器主要功能，补充了一些 bean 的定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            System.out.println(beanFactoryPostProcessor);
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        // 打印BeanFactory中Bean
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // 从BeanFactory中取出Bean1，然后再从Bean1中取出它依赖的Bean2
        // 可以看到结果为null，所以@Autowired注解并没有被解析
        // Bean1 bean1 = beanFactory.getBean(Bean1.class);
        // System.out.println(bean1.getBean2());

        // 要想@Autowired、@Resource等注解被解析，还要添加Bean的后处理器，可以针对Bean的生命周期的各个阶段提供扩展
        // 从bean工厂中取出Bean的后处理器，并且执行这些后处理器
        // BeanFactory 后处理器主要功能，补充了一些 bean 的定义
        // beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        // beanFactory.addBeanPostProcessors(beanFactory.getBeansOfType(BeanPostProcessor.class).values());
        // 改变Bean后处理器加入BeanFactory的顺序
        // 写法1：
        // ArrayList<BeanPostProcessor> list = new ArrayList<>(beanFactory.getBeansOfType(BeanPostProcessor.class).values());
        // Collections.reverse(list);
        // beanFactory.addBeanPostProcessors(list);
        // 写法2：
        beanFactory.addBeanPostProcessors(beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream().sorted(beanFactory.getDependencyComparator()).collect(Collectors.toCollection(ArrayList::new)));
        // 通过AnnotationConfigUtils给beanFactory添加一些后处理的时候会默认设置比较器，可以对BeanPostProcessor进行排序，排序的依据是BeanPostProcessor内部的order属性，其中internalAutowiredAnnotationProcessor的order属性的值为Ordered.LOWEST_PRECEDENCE - 2，internalCommonAnnotationProcessor的order属性的值为Ordered.LOWEST_PRECEDENCE - 3
        // internalAutowiredAnnotationProcessor:2147483645
        // internalCommonAnnotationProcessor:2147483644
        // internalCommonAnnotationProcessor的order值更小，所以排序的时候会排在前面
        System.out.println("internalAutowiredAnnotationProcessor:" + (Ordered.LOWEST_PRECEDENCE - 2));
        System.out.println("internalCommonAnnotationProcessor:" + (Ordered.LOWEST_PRECEDENCE - 3));

        // 准备好所有单例，get()前就把对象初始化好
        beanFactory.preInstantiateSingletons();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Bean1 bean1 = beanFactory.getBean(Bean1.class);
        System.out.println(bean1.getBean2());

        /**
         * 学到了什么：
         *      a. beanFactory 不会做的事
         *         1. 不会主动调用BeanFactory的后处理器
         *         2. 不会主动添加Bean的后处理器
         *         3. 不会主动初始化单例
         *         4. 不会解析BeanFactory，还不会解析 ${}, #{}
         *
         *      b. Bean后处理器会有排序的逻辑
         */
        System.out.println(bean1.getInter());
    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }
    }

    @Slf4j
    static class Bean1 {
        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;

        public Inter getInter() {
            return bean3;
        }

        public Bean1() {
            log.debug("构造 Bean1()");
        }
    }

    @Slf4j
    static class Bean2 {
        public Bean2() {
            log.debug("构造 Bean2()");
        }
    }

    interface Inter {

    }

    @Slf4j
    static class Bean3 implements Inter {
        public Bean3() {
            log.debug("构造 Bean3()");
        }
    }

    @Slf4j
    static class Bean4 implements Inter {
        public Bean4() {
            log.debug("构造 Bean4()");
        }
    }
}
