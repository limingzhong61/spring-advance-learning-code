package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @Author XiYue
 * @Date 2022/3/24--21:20
 * @Description
 */
@Slf4j
public class TestInitAndDestroy {
    @Test
    public void testInitAndDestroy() throws Exception {
        // ⬇️GenericApplicationContext 是一个【干净】的容器，这里只是为了看初始化步骤，就不用springboot启动类进行演示了
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);
        // 解析@PostConstruct注解的bean后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 解析@Configuration、@Component、@Bean注解的bean工厂后处理器
        context.registerBean(ConfigurationClassPostProcessor.class);

        context.refresh();
        context.close();

        /**
         * 学到了什么
         *      a. spring 提供了多种初始化和销毁手段
         *      b. spirng 的面试有多么地卷 o(╯□╰)o，w h a t f u c k !!!
         */
    }
}

