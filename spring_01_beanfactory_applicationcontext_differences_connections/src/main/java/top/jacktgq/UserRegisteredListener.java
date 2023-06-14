package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * @Author XiYue
 * @Date 2022/3/24--19:36
 * @Description
 */
@Component
@Slf4j
public class UserRegisteredListener {
    @EventListener
    public void userRegister(UserRegisteredEvent event) {
        System.out.println("UserRegisteredEvent...");
        log.debug("{}", event);
    }
}
