package top.jacktgq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author XiYue
 * @Date 2022/3/28--0:19
 * @Description
 */
@ConfigurationProperties(prefix = "java")
@Slf4j
public class Bean4 {
    private String home;
    private String version;
}
