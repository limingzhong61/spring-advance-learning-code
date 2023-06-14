package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;

import java.io.IOException;

/**
 * @Author XiYue
 * @Date 2022/3/24--21:20
 * @Description 4种重要的ApplicationContext实现类
 */
@Slf4j
public class TestApplicationContext {
    @Test
    // ⬇️1.最为经典的容器，基于classpath 下 xml 格式的配置文件来创建
    public void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_bean.xml");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    @Test
    // ⬇️2.基于磁盘路径下 xml 格式的配置文件来创建
    public void testFileSystemXmlApplicationContext() {
        // 可以用绝对路径或者相对路径
        // FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("D:\\ideacode\\spring-source-study\\spring_02_02_applicationcontext_impl\\src\\main\\resources\\spring_bean.xml");
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src\\main\\resources\\spring_bean.xml");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    @Test
    // ⬇️模拟一下ClassPathXmlApplicationContext和FileSystemXmlApplicationContext底层的一些操作
    public void testMockClassPathAndFileSystemXmlApplicationContext() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        System.out.println("读取之前");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("读取之后");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // reader.loadBeanDefinitions("spring_bean.xml");
        // reader.loadBeanDefinitions(new ClassPathResource("spring_bean.xml"));
        reader.loadBeanDefinitions(new FileSystemResource("src\\main\\resources\\spring_bean.xml"));
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

    @Test
    // ⬇️3.较为经典的容器，基于java配置类来创建
    public void testAnnotationConfigApplicationContext() {
        // 会自动加上5个后处理器
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        // org.springframework.context.annotation.internalCommonAnnotationProcessor
        // org.springframework.context.event.internalEventListenerProcessor
        // org.springframework.context.event.internalEventListenerFactory
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    @Test
    // ⬇️4.较为经典的容器，基于java配置类来创建，并且还可以用于web环境
    // 模拟了 springboot web项目内嵌Tomcat的工作原理
    public void testAnnotationConfigServletWebServerApplicationContext() throws IOException {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        // 防止程序终止
        System.in.read();
    }
}

@Configuration
class WebConfig {
    @Bean
    // 1. WebServer工厂
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    // 2. web项目必备的DispatcherServlet
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    // 3. 将DispatcherServlet注册到WebServer上
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
    }

    @Bean("/hello")
    public Controller controller1() {
        return (request, response) -> {
            response.getWriter().println("hello");
            return null;
        };
    }
}

// 单元测试的过程中如果要解析一些Spring注解，比如@Configuration的时候不要把相关类定义到写单元测试类的内部类，会读取不到
@Configuration
class Config {
    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    public Bean2 bean2(Bean1 bean1) {
        Bean2 bean2 = new Bean2();
        bean2.setBean1(bean1);
        return bean2;
    }
}

class Bean1 {

}

class Bean2 {
    private Bean1 bean1;

    public Bean1 getBean1() {
        return bean1;
    }

    public void setBean1(Bean1 bean1) {
        this.bean1 = bean1;
    }
}
