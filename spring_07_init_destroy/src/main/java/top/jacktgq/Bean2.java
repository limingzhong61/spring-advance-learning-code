package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author XiYue
 * @Date 2022/4/1--0:22
 * @Description
 */
@Slf4j
public class Bean2 implements DisposableBean {
    @PreDestroy
    public void destroy1() {
        log.debug("销毁1，@PreDestory");
    }

    @Override
    public void destroy() throws Exception {
        log.debug("销毁2，DisposableBean接口");
    }

    public void destroy3() {
        log.debug("销毁3，@Bean的destroyMethod");
    }
}
