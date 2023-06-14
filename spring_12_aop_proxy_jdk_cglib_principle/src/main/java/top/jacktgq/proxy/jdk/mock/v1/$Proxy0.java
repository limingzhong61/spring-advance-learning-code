package top.jacktgq.proxy.jdk.mock.v1;


import top.jacktgq.proxy.jdk.mock.v2.Foo;
import top.jacktgq.proxy.jdk.mock.v2.Target;

/**
 * @Author XiYue
 * @Date 2022/4/11--10:17
 * @Description
 */
public class $Proxy0 implements Foo {
    @Override
    public void foo() {
        // 1. 功能增强
        System.out.println("before...");
        // 2. 调用目标
        new Target().foo();
    }
}
