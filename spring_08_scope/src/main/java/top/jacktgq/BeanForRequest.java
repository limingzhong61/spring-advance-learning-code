package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @Author XiYue
 * @Date 2022/4/1--1:55
 * @Description
 */
@Slf4j
@Scope("request")
@Component
public class BeanForRequest {
    @PreDestroy
    public void destroy() {
        log.debug("destroy");
    }
}
