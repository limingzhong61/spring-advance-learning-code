package top.jacktgq.proxy.jdk.mock.v4_1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author XiYue
 * @Date 2022/4/11--10:29
 * @Description
 */
public class Main {
    public static void main(String[] args) {
        Foo proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                // 1. 功能增强
                System.out.println("before...");
                // 2. 调用目标
                return method.invoke(new Target(), args);
            }
        });
        proxy.foo();
        System.out.println("bar()方法返回值：" + proxy.bar());
    }
}
