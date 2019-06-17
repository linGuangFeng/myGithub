package demo.anno_aop.controller;

import demo.handle_exception.annotion.VisitAnnotion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
public class TestController {

    @GetMapping(value = "method_01")
    @VisitAnnotion
    public Object method_01() {

        return "成功";
    }
}
