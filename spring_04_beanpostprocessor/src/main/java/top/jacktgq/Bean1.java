package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @Author XiYue
 * @Date 2022/3/26--15:10
 * @Description
 */
@Slf4j
public class Bean1 {
    private  String home;

    //@Autowired
    //private  String aaa;
    private Bean2 bean2;

    @Autowired
    public void setBean2(Bean2 bean2) {
        log.debug("@Autowired 生效：{}", bean2);
        this.bean2 = bean2;
    }

    @Autowired
    public void setJava_home(@Value("${JAVA_HOME}") String java_home) {
        log.debug("@Value 生效：{}", java_home);
        this.java_home = java_home;
    }

    private Bean3 bean3;

    @Resource
    public void setBean3(Bean3 bean3) {
        log.debug("@Resource 生效：{}", bean3);
        this.bean3 = bean3;
    }

    private String java_home;

    @PostConstruct
    public void init() {
        log.debug("@PostConstruct 生效：{}");
    }

    @PreDestroy
    public void destroy() {
        log.debug("@PreDestroy 生效：{}");
    }

    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", java_home='" + java_home + '\'' +
                '}';
    }
}
