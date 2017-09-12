package system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("TestSystemController")
@Controller
public class TestSystemController {

    @RequestMapping(value = "/testFirst")
    public String testFirst() {
        System.out.println("-------------------------------------------");
        return "123123";
    }


}
