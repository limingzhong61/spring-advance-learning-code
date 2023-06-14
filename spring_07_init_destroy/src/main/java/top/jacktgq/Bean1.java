package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @Author XiYue
 * @Date 2022/4/1--0:22
 * @Description
 */
@Slf4j
public class Bean1 implements InitializingBean {
    @PostConstruct
    public void init1() {
        log.debug("初始化1，@PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("初始化2，InitializingBean接口");
    }

    public void init3() {
        log.debug("初始化3，@Bean的initMethod");
    }
}
