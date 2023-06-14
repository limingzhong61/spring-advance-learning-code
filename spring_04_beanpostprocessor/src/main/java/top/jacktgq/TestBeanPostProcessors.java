package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author XiYue
 * @Date 2022/3/24--21:20
 * @Description 探究一下@Autowired、@Value、@Resource、@PostConstruct、@PreDestroy这些注解分别是由哪个后处理器来解析的
 */
@Slf4j
public class TestBeanPostProcessors {
    @Test
    public void testBeanPostProcessors() {
        // ⬇️GenericApplicationContext 是一个【干净】的容器，默认不会添加任何后处理器，方便做测试
        // 这里用DefaultListableBeanFactory也可以完成测试，只是会比使用GenericApplicationContext麻烦一些
        GenericApplicationContext context = new GenericApplicationContext();

        // 用原始方法注册三个Bean
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        // 设置解析 @Value 的解析器
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 添加解析 @Autowired 和 @Value 注解的后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 添加解析 @Resource、@PostConstruct、@PreDestroy 注解的后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 添加解析 @ConfigurationProperties注解的后处理器
        // ConfigurationPropertiesBindingPostProcessor后处理器不能像上面几种后处理器那样用context直接注册上去
        // context.registerBean(ConfigurationPropertiesBindingPostProcessor.class);
        // 需要反着来注册一下
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());


        // ⬇️初始化容器
        context.refresh();
        System.out.println(context.getBean(Bean4.class));

        // ⬇️销毁容器
        context.close();
    }

    @Test
    public void testAutowiredAnnotationBeanPostProcessor() throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 这里为了省事就不使用 beanFactory.registerBeanDefinition()方法去添加类的描述信息了
        // 直接使用 beanFactory.registerSingleton可以直接将Bean的单例对象注入进去，
        // 后面调用beanFactory.getBean()方法的时候就不会去根据Bean的定义去创建Bean的实例了，
        // 也不会有懒加载和依赖注入的初始化过程了。
        beanFactory.registerSingleton("bean2", new Bean2());
        beanFactory.registerSingleton("bean3", new Bean3());
        // 设置@Autowired注解的解析器
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 设置解析 @Value 注解中的 ${} 表达式的解析器
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        // 1. 查找哪些属性、方法加了 @Autowired，这称之为InjectionMetadata
        // 创建后处理器
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        // 后处理器在解析@Autowired和@Value的时候需要用到其他Bean，
        // 而BeanFactory提供了需要的Bean，所以需要把BeanFactory传给这个后处理器
        processor.setBeanFactory(beanFactory);
        // 创建Bean1
        Bean1 bean1 = new Bean1();
        System.out.println(bean1);
        // 解析@Autowired和@Value注解，执行依赖注入
        // PropertyValues pvs: 给注解的属性注入给定的值，这里不需要手动给定，传null即可
        // processor.postProcessProperties(null, bean1, "bean1");
        // postProcessProperties()方法底层原理探究
        // 通过查看源码得知 postProcessProperties()方法中调用了一个私有的方法findAutowiringMetadata(beanName, bean.getClass(), pvs); 会返回一个InjectionMetadata的对象，然后会调用InjectionMetadata.inject(bean1, "bean1", null)进行依赖注入
        // 通过反射调用一下
        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata",String.class, Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);
        // 获取Bean1上加了@Value @Autowired注解的成员变量和方法参数信息
        InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(processor, "bean1", Bean1.class, null);
        System.out.println(metadata);

        // 2. 调用 InjectionMetaData 来进行依赖注入，注入时按类型查找值
        metadata.inject(bean1, "bean1", null);
        System.out.println(bean1);

        // 3. 如何去Bean工厂里面按类型查找值
        // 由于InjectionMetadata.inject(bean1, "bean1", null)的源码调用链过长，摘出主要调用过程进行演示

        // 3.1 @Autowired加在成员变量上，InjectionMetatadata给Bean1注入Bean3的过程
        // 通过InjectionMetadata把Bean1加了@Autowired注解的属性的BeanName先拿到，这里假设拿到的BeanName就是 bean3
        // 通过BeanName反射获取到这个属性，
        Field bean3Field = Bean1.class.getDeclaredField("bean3");
        // 设置私有属性可以被访问
        bean3Field.setAccessible(true);
        // 将这个属性封装成一个DependencyDescriptor对象
        DependencyDescriptor dd1 = new DependencyDescriptor(bean3Field, false);
        // 再执行beanFactory的doResolveDependency
        Bean3 bean3Value = (Bean3) beanFactory.doResolveDependency(dd1, null, null, null);
        System.out.println(bean3Value);
        // 给Bean1的成员bean3赋值
        bean3Field.set(bean1, bean3Value);
        System.out.println(bean1);

        // 3.2 @Autowired加在方法上，InjectionMetatadata给Bean1注入Bean2的过程
        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2, 0), true);
        Bean2 bean2Value = (Bean2) beanFactory.doResolveDependency(dd2, "bean2", null, null);
        System.out.println(bean2Value);
        // 给Bean1的setBean2()方法的参数赋值
        setBean2.invoke(bean1, bean2Value);
        System.out.println(bean1);

        // 3.3 @Autowired加在方法上，方法参数为String类型，加了@Value，
        // InjectionMetadata给Bean1注入环境变量JAVA_HOME属性的值
        Method setJava_home = Bean1.class.getDeclaredMethod("setJava_home", String.class);
        DependencyDescriptor dd3 = new DependencyDescriptor(new MethodParameter(setJava_home, 0), true);
        String java_home = (String) beanFactory.doResolveDependency(dd3, null, null, null);
        System.out.println(java_home);
        setJava_home.invoke(bean1, java_home);
        System.out.println(bean1);
    }
}

