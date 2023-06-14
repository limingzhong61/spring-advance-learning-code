package top.jacktgq;

import org.junit.Test;
import top.jacktgq.service.MyService;

/**
 * @Author XiYue
 * @Date 2022/4/10--16:53
 * @Description jdk 版本选择了 java 8, 因为目前的 aspectj-maven-plugin 1.14.0 最高只支持到 java 16
 */
public class Aop_Ajc_Test {
    @Test
    public void testAopAjc() {
        new MyService().foo();
    }
}
