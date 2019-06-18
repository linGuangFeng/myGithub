package author.author_server.controller;

import author.author_server.constants.FromLoginConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping(FromLoginConstant.LOGIN_PROCESSING_URL)
    public String processingURL() {
        return "登录请求！";
    }
}
