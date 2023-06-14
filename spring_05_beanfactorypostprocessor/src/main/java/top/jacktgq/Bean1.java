package top.jacktgq;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author XiYue
 * @Date 2022/3/26--15:10
 * @Description
 */
@Slf4j
public class Bean1 {
    public Bean1() {
        log.debug("我被 Spring 管理啦");
    }
}
