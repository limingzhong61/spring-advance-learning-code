package top.jacktgq;

import org.springframework.context.ApplicationEvent;

/**
 * @Author XiYue
 * @Date 2022/3/24--19:28
 * @Description
 */
public class UserRegisteredEvent extends ApplicationEvent {
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
