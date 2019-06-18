package demo.url.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * PathVariable请求路径测试
 */
@RestController
public class TestPathVariableController {

    @GetMapping("/parameter1/{name1}")
    public String method_01(@PathVariable("name1") String name1){
        return "hello " + name1;
    }
    @GetMapping("/parameter1/{name1}/parameter2/{name2}")
    public String method_02(@PathVariable("name1") String name1, @PathVariable("name2") String name2){
        return "hello " + name1 + " and " + name2;
    }

}
