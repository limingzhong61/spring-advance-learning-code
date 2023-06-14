package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author XiYue
 * @Date 2022/3/24--21:20
 * @Description
 */
@Slf4j
@SpringBootApplication
public class ScopeApplicationContext {
    public static void main(String[] args) throws Exception {
        // testRequest_Session_Application_Scope();
        testSingletonPrototypeInvalidAndSolve();
    }

    // 演示 request, session, application作用域
    private static void testRequest_Session_Application_Scope() throws Exception {
        /**
         * singleton：单例
         * prototype：多例
         * request： web的请求
         * session： web的会话
         * application：web的ServletContext
         *
         * 演示 request, session, application 作用域
         * 打开不同的浏览器，访问 http://localhost:8080/test 即可查看效果
         * 如果 jdk > 8， 运行时请添加 --add-opens java.base/java.lang=ALL-UNNAMED
         */
        ConfigurableApplicationContext context = SpringApplication.run(ScopeApplicationContext.class);
    }

    // 演示单例中注入多例失效的情况，以及解决失效问题的方法
    private static void testSingletonPrototypeInvalidAndSolve() {
        ConfigurableApplicationContext context = SpringApplication.run(ScopeApplicationContext.class);
        SingletonBean singletonBean = context.getBean(SingletonBean.class);
        // 单例中注入多例失效的情况
        log.debug("{}", singletonBean.getPrototypeBean().getClass());
        log.debug("{}", singletonBean.getPrototypeBean());
        log.debug("{}", singletonBean.getPrototypeBean());
        log.debug("{}", singletonBean.getPrototypeBean());
        System.out.println("------------------------------------");

        // 解决方法1：在SingletonBean的PrototypeBean1属性上加@Lazy注解
        log.debug("{}", singletonBean.getPrototypeBean1().getClass());
        log.debug("{}", singletonBean.getPrototypeBean1());
        log.debug("{}", singletonBean.getPrototypeBean1());
        log.debug("{}", singletonBean.getPrototypeBean1());
        System.out.println("------------------------------------");

        // 解决方法2：在PrototypeBean2的类上的@Scope注解多配置一个属性，如，@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
        log.debug("{}", singletonBean.getPrototypeBean2().getClass());
        log.debug("{}", singletonBean.getPrototypeBean2());
        log.debug("{}", singletonBean.getPrototypeBean2());
        log.debug("{}", singletonBean.getPrototypeBean2());
        System.out.println("------------------------------------");

        // 解决方法3：使用ObjectFactory<PrototypeBean3>工厂类，在每次调用getProtypeBean3()方法中返回factory.getObject()
        log.debug("{}", singletonBean.getPrototypeBean3().getClass());
        log.debug("{}", singletonBean.getPrototypeBean3());
        log.debug("{}", singletonBean.getPrototypeBean3());
        log.debug("{}", singletonBean.getPrototypeBean3());
        System.out.println("------------------------------------");

        // 解决方法4：在SingletonBean中注入一个ApplicationContext，使用context.getBean(PrototypeBean4.class)获取对应的多例
        log.debug("{}", singletonBean.getPrototypeBean4().getClass());
        log.debug("{}", singletonBean.getPrototypeBean4());
        log.debug("{}", singletonBean.getPrototypeBean4());
        log.debug("{}", singletonBean.getPrototypeBean4());
        System.out.println("------------------------------------");
    }
}

