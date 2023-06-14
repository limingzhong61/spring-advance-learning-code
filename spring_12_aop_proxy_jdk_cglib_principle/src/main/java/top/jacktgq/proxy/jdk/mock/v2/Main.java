package top.jacktgq.proxy.jdk.mock.v2;

/**
 * @Author XiYue
 * @Date 2022/4/11--10:29
 * @Description
 */
public class Main {
    public static void main(String[] args) {
        Foo proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public void invoke() {
                // 1. 功能增强
                System.out.println("before...");
                // 2. 调用目标
                new Target().foo();
            }
        });
        proxy.foo();
    }
}
