package dosa.counselor.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
