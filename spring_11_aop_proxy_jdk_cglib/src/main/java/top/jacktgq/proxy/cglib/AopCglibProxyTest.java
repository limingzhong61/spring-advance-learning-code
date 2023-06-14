package top.jacktgq.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;

/**
 * @Author XiYue
 * @Date 2022/4/11--8:03
 * @Description
 */
public class AopCglibProxyTest {
    @Test
    public void testCglibProxy1() {
        // 目标对象
        Target target = new Target();
        Foo fooProxy = (Foo) Enhancer.create(Target.class, (MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("before...");
            Object result = method.invoke(target, args); // 用方法反射调用目标
            System.out.println("after...");
            return result;
        });
        System.out.println(fooProxy.getClass());

        fooProxy.foo();
    }

    @Test
    public void testCglibProxy2() {
        // 目标对象
        Target target = new Target();
        Foo fooProxy = (Foo) Enhancer.create(Target.class, (MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("before...");
            // proxy 它可以避免反射调用
            Object result = proxy.invoke(target, args); // 需要传目标类 （spring用的是这种）
            System.out.println("after...");
            return result;
        });
        System.out.println(fooProxy.getClass());

        fooProxy.foo();
    }

    @Test
    public void testCglibProxy3() {
        // 目标对象
        Foo fooProxy = (Foo) Enhancer.create(Target.class, (MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("before...");
            // proxy 它可以避免反射调用
            Object result = proxy.invokeSuper(obj, args); // 不需要目标类，需要代理自己
            System.out.println("after...");
            return result;
        });
        System.out.println(fooProxy.getClass());

        fooProxy.foo();
    }
}

interface Foo {
    void foo();
}

@Slf4j
class Target implements Foo {
    public void foo() {
        log.debug("target foo");
    }
}
