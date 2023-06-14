package top.jacktgq;


import org.junit.Test;
import top.jacktgq.service.MyService;

/**
 * @Author XiYue
 * @Date 2022/4/10--16:53
 * @Description
 *       注意: 运行时需要在 VM options 里加入 -javaagent:D:\同步空间\repository\org\aspectj\aspectjweaver\1.9.7\aspectjweaver-1.9.7.jar
 *       把其中 D:\同步空间\repository 改为你自己 maven 仓库起始地址
 */
public class Aop_agent_Test {
    @Test
    public void testAopAgent() throws Exception {
        MyService myService = new MyService();
        myService.foo();
        System.in.read();
    }
}
