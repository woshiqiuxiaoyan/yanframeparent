package user.controller;

import constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import system.controller.BaseController;
import user.service.ICtUserInfoService;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("IndexController")
@Controller
public class IndexController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public ICtUserInfoService ctUserInfoService;


    @RequestMapping("/index")
    public String enterTestHello3(Model model) {
        return view(model, Constant.Views.firstPage);
    }




}
