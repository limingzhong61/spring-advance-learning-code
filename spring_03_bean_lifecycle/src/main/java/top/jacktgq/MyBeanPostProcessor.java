package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author XiYue
 * @Date 2022/3/26--11:49
 * @Description
 */
@Slf4j
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    @Override
    // 实例化前（即调用构造方法前）执行的方法
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.debug("<<<<<<<<<<< 实例化前执行，如@PreDestroy");
        // 返回null保持原有对象不变，返回不为null，会替换掉原有对象
        return null;
    }

    @Override
    // 实例化后执行的方法
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean")) {
            log.debug("<<<<<<<<<<< 实例化后执行，这里如果返回 false 会跳过依赖注入阶段");
            // return false;
        }

        return true;
    }

    @Override
    // 依赖注入阶段执行的方法
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.debug("<<<<<<<<<<< 依赖注入阶段执行，如@Autowired、@Value、@Resource");
        return pvs;
    }

    @Override
    // 销毁前执行的方法
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean"))
            log.debug("<<<<<<<<<<<销毁之前执行");
    }

    @Override
    // 初始化之前执行的方法
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean"))
            log.debug("<<<<<<<<<<< 初始化之前执行，这里返回的对象会替换掉原本的bean，如 @PostConstruct、@ConfigurationProperties");
        return bean;
    }

    @Override
    // 初始化之后执行的方法
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean"))
            log.debug("<<<<<<<<<<< 初始化之后执行，这里返回的对象会替换掉原本的bean，如 代理增强");
        return bean;
    }
}
