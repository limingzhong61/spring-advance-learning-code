package top.jacktgq.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author XiYue
 * @Date 2022/3/29--12:42
 * @Description
 */
@Component
@Slf4j
public class Bean2 {
    public Bean2() {
        log.debug("我被 Spring 管理啦");
    }
}
