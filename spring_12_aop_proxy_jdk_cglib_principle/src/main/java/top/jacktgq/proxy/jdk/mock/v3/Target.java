package top.jacktgq.proxy.jdk.mock.v3;
public final class Target implements Foo {
    public void foo() {
        System.out.println("target foo");
    }

    @Override
    public void bar() {
        System.out.println("target bar");
    }
}