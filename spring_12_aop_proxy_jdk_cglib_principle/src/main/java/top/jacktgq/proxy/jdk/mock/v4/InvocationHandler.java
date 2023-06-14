package top.jacktgq.proxy.jdk.mock.v4;

import java.lang.reflect.Method;

/**
 * @Author XiYue
 * @Date 2022/4/14--0:04
 * @Description
 */
public interface InvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
