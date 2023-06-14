package top.jacktgq.aop;

//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author XiYue
 * @Date 2022/4/10--16:17
 * @Description
 */
@Aspect // ⬅️注意此切面并未被 Spring 管理，本项目pom文件中根本没有引入spring的相关类
public class MyAspect {
    private static final Logger log = LoggerFactory.getLogger(MyAspect.class);

    @Before("execution(* top.jacktgq.service.MyService.foo())")
    public void before() {
        log.debug("before()");
    }
}
