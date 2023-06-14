package top.jacktgq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jacktgq.aop.MyAspect;

/**
 * @Author XiYue
 * @Date 2022/4/10--16:13
 * @Description
 */

public class MyService {
    private static final Logger log = LoggerFactory.getLogger(MyAspect.class);
    public void foo() {
        log.debug("foo()");
    }
}
