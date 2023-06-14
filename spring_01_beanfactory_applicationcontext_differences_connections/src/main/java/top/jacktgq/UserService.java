package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @Author XiYue
 * @Date 2022/3/24--20:05
 * @Description
 */
@Component
@Slf4j
public class UserService {
    @Autowired
    private ApplicationEventPublisher context;
    public void register(String username, String password) {
        log.debug("新用户注册，账号：" + username + "，密码：" + password);
        context.publishEvent(new UserRegisteredEvent(this));
    }
}
