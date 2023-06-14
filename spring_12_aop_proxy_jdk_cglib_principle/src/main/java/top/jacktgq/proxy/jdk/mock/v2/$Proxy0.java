package top.jacktgq.proxy.jdk.mock.v2;


/**
 * @Author XiYue
 * @Date 2022/4/11--10:17
 * @Description
 */
public class $Proxy0 implements Foo {
    private InvocationHandler h;

    public $Proxy0(InvocationHandler h) {
        this.h = h;
    }

    @Override
    public void foo() {
        h.invoke();
    }
}
