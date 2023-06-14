package top.jacktgq.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Author XiYue
 * @Date 2022/4/10--16:17
 * @Description
 */
@Aspect // ⬅️注意此切面并未被 Spring 管理，本项目pom文件中根本没有引入spring的相关类
@Slf4j
public class MyAspect {
    @Before("execution(* top.jacktgq.service.MyService.*())")
    public void before() {
        log.debug("before()");
    }
}
