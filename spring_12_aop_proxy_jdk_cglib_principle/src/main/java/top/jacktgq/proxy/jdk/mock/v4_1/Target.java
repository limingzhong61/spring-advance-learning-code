package top.jacktgq.proxy.jdk.mock.v4_1;

public final class Target implements Foo {
    public void foo() {
        System.out.println("target foo");
    }

    @Override
    public int bar() {
        System.out.println("target bar");
        return 1;
    }
}