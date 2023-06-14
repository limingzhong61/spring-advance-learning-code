package top.jacktgq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author XiYue
 * @Date 2022/4/1--1:58
 * @Description
 */
@RestController
public class MyController {
    @Lazy
    @Autowired
    private BeanForRequest beanForRequest;

    @Lazy
    @Autowired
    private BeanForSession beanForSession;

    @Lazy
    @Autowired
    private BeanForApplication beanForApplication;

    @GetMapping(value = "/test", produces = "text/html")
    public String test(HttpServletRequest request, HttpSession session) {
        // ServletContext sc = request.getServletContext();
        String result = "<ul>" +
                        "<li>request scope: " +  beanForRequest + "</li>" +
                        "<li>session scope: " +  beanForSession + "</li>" +
                        "<li>application scope: " +  beanForApplication + "</li>" +
                        "</ul>";
        return result;
    }
}
