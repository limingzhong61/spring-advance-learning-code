package top.jacktgq.proxy.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @Author XiYue
 * @Date 2022/4/11--8:03
 * @Description
 */
public class AopJdkProxyTest {
    @Test
    public void testJdkProxy() throws IOException {
        // jdk的动态代理，只能针对接口代理
        // 目标对象
        Target target = new Target();
        // 用来加载在运行期间动态生成的字节码
        ClassLoader loader = AopJdkProxyTest.class.getClassLoader();
        Foo fooProxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, (proxy, method, args) -> {
            System.out.println("before...");
            Object result = method.invoke(target, args);
            System.out.println("after...");
            return result;  // 让代理也返回目标方法执行的结果
        });
        System.out.println(fooProxy.getClass());
        fooProxy.foo();

        System.in.read();
        // 总结：代理对象和目标对象是兄弟关系，都继承了Foo接口，代理对象类型不能强转成目标对象类型。
    }
}

interface Foo {
    void foo();
}

@Slf4j
final class Target implements Foo {
    public void foo() {
        log.debug("target foo");
    }
}
