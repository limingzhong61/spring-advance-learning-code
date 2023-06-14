package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/**
 * @Author XiYue
 * @Date 2022/3/24--21:20
 * @Description 探究一下@Autowired、@Value、@Resource、@PostConstruct、@PreDestroy这些注解分别是由哪个后处理器来解析的
 */
@Slf4j
public class TestBeanFactoryPostProcessors {
    @Test
    public void testBeanPostProcessors() throws IOException {
        // ⬇️GenericApplicationContext 是一个【干净】的容器，默认不会添加任何后处理器，方便做测试
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("config", Config.class);
        // 添加Bean工厂后处理器ConfigurationClassPostProcessor
        // 解析@ComponentScan、@Bean、@Import、@ImportResource注解
        context.registerBean(ConfigurationClassPostProcessor.class);
        // 添加Bean工厂后处理器MapperScannerConfigurer，解析@MapperScan注解
        context.registerBean(MapperScannerConfigurer.class, beanDefinition -> {
            // 指定扫描的包名
            beanDefinition.getPropertyValues().add("basePackage", "top.jacktgq.mapper");
        });

        // ⬇️初始化容器
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // ⬇️销毁容器
        context.close();
    }

    @Test
    // 模拟实现组件扫描，即@ComponentScan注解的解析
    public void testMockComponentScan() throws Exception {
        // ⬇️GenericApplicationContext 是一个【干净】的容器，默认不会添加任何后处理器，方便做测试
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("config", Config.class);
        // 把自定义组件扫描Bean工厂后处理器加进来
        context.registerBean(ComponentScanPostProcessor.class);

        // ⬇️初始化容器
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // ⬇️销毁容器
        context.close();
    }

    @Test
    // 模拟实现@Bean注解的解析
    public void testMockAtBeanAnnotation() throws Exception {
        // ⬇️GenericApplicationContext 是一个【干净】的容器，默认不会添加任何后处理器，方便做测试
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("config", Config.class);
        context.registerBean(CandyAtBeanPostProcessor.class);

        // ⬇️初始化容器
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // ⬇️销毁容器
        context.close();
    }

    @Test
    // 模拟实现@Mapper注解的解析
    public void testMockAtMapperAnnotation() throws Exception {
        // ⬇️GenericApplicationContext 是一个【干净】的容器，默认不会添加任何后处理器，方便做测试
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("config", Config.class);
        // 先解析@Bean注解，把SqlSessionFactory加到Bean工厂里面
        context.registerBean(CandyAtBeanPostProcessor.class);
        // 解析Mapper接口
        context.registerBean(CandyAtMapperPostProcessor.class);

        // ⬇️初始化容器
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // ⬇️销毁容器
        context.close();
    }
}

