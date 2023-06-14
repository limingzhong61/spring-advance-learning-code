package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author XiYue
 * @Date 2022/3/30--11:59
 * @Description
 */
@Configuration
@Slf4j
public class MyConfig2 implements ApplicationContextAware, InitializingBean {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        log.debug("注入 ApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("初始化");
    }

    @Bean
    public BeanFactoryPostProcessor processor2() {
        return beanFactory -> {
            log.debug("执行 processor1");
        };
    }
}
