package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * @Author XiYue
 * @Date 2022/3/30--9:51
 * @Description
 */
@Slf4j
public class MyBean implements BeanNameAware, ApplicationContextAware, InitializingBean {
    @Override
    public void setBeanName(String name) {
        log.debug("当前bean：" + this + "，实现 BeanNameAware 调用的方法，名字叫：" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.debug("当前bean：" + this + "，实现 ApplicationContextAware 调用的方法，容器叫：" + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("当前bean：" + this + "，实现 InitializingBean 调用的方法，初始化");
    }

    @Autowired
    public void aaa(ApplicationContext applicationContext) {
        log.debug("当前bean：" + this +"，使用 @Autowired 容器是：" + applicationContext);
    }

    @PostConstruct
    public void init() {
        log.debug("当前bean：" + this + "，使用 @PostConstruct 初始化");
    }
}
