package user.controller;

import annotation.MenuAuthory;
import constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import system.controller.BaseController;
import user.service.ICtUserInfoService;

/**
 * Created by t on 2017/8/16.
 * @Author qxy
 */
@RequestMapping("SysStockController")
@org.springframework.stereotype.Controller
public class SysStockController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public ICtUserInfoService ctUserInfoService;



    /**
     * 进货页面
     *
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/inStockPage/{menu_code}")
    public String inStockPage(Model model) {

        return view(model, Constant.Views.inStockPage);
    }




}
