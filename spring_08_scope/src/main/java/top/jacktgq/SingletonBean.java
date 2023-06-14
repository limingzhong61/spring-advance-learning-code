package top.jacktgq;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @Author XiYue
 * @Date 2022/4/1--13:01
 * @Description
 */
@Scope("singleton")
@Component
public class SingletonBean {
    @Autowired
    private PrototypeBean prototypeBean;

    @Lazy
    @Autowired
    private PrototypeBean1 prototypeBean1;

    @Autowired
    private PrototypeBean2 prototypeBean2;

    @Autowired
    private ObjectFactory<PrototypeBean3> factory;

    @Autowired
    private ApplicationContext context;

    public PrototypeBean getPrototypeBean() {
        return prototypeBean;
    }

    public PrototypeBean1 getPrototypeBean1() {
        return prototypeBean1;
    }

    public PrototypeBean2 getPrototypeBean2() {
        return prototypeBean2;
    }

    public PrototypeBean3 getPrototypeBean3() {
        return factory.getObject();
    }

    public PrototypeBean4 getPrototypeBean4() {
        return context.getBean(PrototypeBean4.class);
    }
}
