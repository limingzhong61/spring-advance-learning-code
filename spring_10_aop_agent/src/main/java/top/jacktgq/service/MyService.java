package top.jacktgq.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author XiYue
 * @Date 2022/4/10--16:13
 * @Description
 */
@Slf4j
public class MyService {
    public void foo() {
        log.debug("foo()");
        bar();
    }
    public void bar() {
        log.debug("bar()");
    }
}
