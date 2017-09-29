package user.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yan.frame.commonuse.SimpleService;
import com.yan.frame.pojo.TUserInfo;
import exception.CustomException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pojo.SimplePojo;
import system.controller.BaseController;
import user.controller.validation.VaildatorGroup1;
import user.dto.TDemo;
import user.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("TestController")
@Controller
public class TestController extends BaseController {

    @Autowired
    public TestService testService;

    /**
     * @param model
     * @param req
     * @return
     * @Authod
     * @Method 测试model
     */
    @RequestMapping(value = "/testFirst")
    public String testFirst(Model model, HttpServletRequest req) {
        model.addAttribute("aa", "123123");
        return "test";
    }


    /**
     * 测试 dao
     *
     * @param td
     * @param model
     * @return
     */
    @RequestMapping(value = "/testDao",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<TDemo> testDao(TDemo td, Model model, HttpServletRequest request) throws UnsupportedEncodingException {

        log.error(td.getUserName()+new String( td.getUserName().getBytes("iso-8859-1"),"UTF-8"));
        log.info(  td.toString());
        List<TDemo> list = testService.queryList();
        return list;
    }


    /**
     * 测试 dao事务
     *
     * @param td
     * @param model
     * @return
     */
    @RequestMapping(value = "/testInsertDao",produces = "application/json;charset=utf-8")
    @ResponseBody
    public TDemo testInsertDao(TDemo td, Model model) {

        log.info("入参："+td.toString());
        int affect = testService.insertTdemo(td);
        return td;
    }


    /**
     * 测试数据绑定
     *
     * @param td
     * @param resp
     */
    @RequestMapping(value = "/testBound", method = RequestMethod.POST)
    public void testBound(@RequestParam(value = "aa", required = true, defaultValue = "默认值") String td, String bb, HttpServletResponse resp) {
        try {

            System.out.println(td + " " + bb);
            resp.getWriter().print(td + "   " + bb);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试自定义 时间类型转换器
     *
     * @param t
     * @param resp
     */
    @RequestMapping(value = "/testDemo", method = RequestMethod.POST)
    public void testDemo(TDemo t, HttpServletResponse resp) {
        try {

            DateTime dateTime = t.getCreateTime();
            System.out.println(dateTime.toString("yyyy-MM-dd"));
            resp.getWriter().print(t);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试  spring vildation 校验测试
     *
     * @param t
     * @param resp
     */
    @RequestMapping(value = "/testVildator", method = {RequestMethod.POST, RequestMethod.GET})
    public void testVildator(@Validated TDemo t, BindingResult bindingResult, HttpServletResponse resp) {
        try {

            if (bindingResult.hasErrors()) {

                int lenght = bindingResult.getAllErrors().size();

                for (int i = 0; i < lenght; i++) {

                    String aa = bindingResult.getAllErrors().get(i).getDefaultMessage();

                    aa = new String(aa.getBytes("ISO-8859-1"), "utf-8");

                    System.out.println(aa);

                }

            }

            resp.getWriter().print(t);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试  spring vildation 分给校验001
     *
     * @param t
     * @param resp
     */
    @RequestMapping(value = "/testVildatorGroup001", method = {RequestMethod.POST, RequestMethod.GET})
    public void testVildatorGroup001(@Validated(value = VaildatorGroup1.class) TDemo t, BindingResult bindingResult, HttpServletResponse resp) {
        try {
            TUserInfo userInfo = new TUserInfo();

            if (bindingResult.hasErrors()) {

                int lenght = bindingResult.getAllErrors().size();

                for (int i = 0; i < lenght; i++) {

                    String aa = bindingResult.getAllErrors().get(i).getDefaultMessage();

                    aa = new String(aa.getBytes("ISO-8859-1"), "utf-8");

                    System.out.println(aa);

                }

            }

            resp.getWriter().print(t);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //测试 页面直接取
    @ModelAttribute("tdemotest")
    public TDemo getModeAttribute() {

        TDemo t = new TDemo();
        t.setUserName("你好啊");

        return t;
    }


    /**
     * 测试  spring 默认回显数据
     *
     * @param t
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testRepairDisplay", method = {RequestMethod.POST, RequestMethod.GET})
    public String testRepairDisplay(@ModelAttribute(value = "items") TDemo t, BindingResult bindingResult, Model model) {
        try {

            System.out.println(t);

            model.addAttribute("msg", "返回成功");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "subform";

    }


    /**
     * 测试  spring 全局异常处理器 CustomExceptionResolver
     *
     * @param t
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testCustomExceptionResolver", method = {RequestMethod.POST, RequestMethod.GET})
    public String testCustomExceptionResolver(@ModelAttribute(value = "items") TDemo t, BindingResult bindingResult, Model model) throws CustomException {

        System.out.println(t);

        model.addAttribute("msg", "返回成功");
        if (true) {
//            测试用户自定义异常
//            throw new CustomException("测试异常");
        }
//            测试系统自定义异常
        int i = 1 / 0;

        return "subform";

    }


    /**A
     * 测试  图片上传
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testUploadPic", method = {RequestMethod.POST, RequestMethod.GET})
    public String testUploadPic(@RequestParam("testFile") MultipartFile uploadFile,HttpServletRequest request) throws CustomException, IOException {

        String originFileName = uploadFile.getOriginalFilename();

        //前半部分路径
        String leftPath =  request.getRealPath("/uploadimages");

        File file = new File(leftPath,originFileName);

        uploadFile.transferTo(file);

        return "subform";

    }



    /**A
     * 测试  json数据转送
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testJsonTranserf", method = {RequestMethod.POST, RequestMethod.GET})//,produces = "application/json")
    public @ResponseBody
    Map<String, Object> testJsonTranserf(@RequestBody    Map<String,Object> map) throws Exception, IOException {
        log.info("我是测试json");
        return map;
    }


    /**A
     * 测试  restful 支持
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testRestFul/{userName}/{age}", method = {RequestMethod.POST, RequestMethod.GET})//,produces = "application/json")
    public @ResponseBody
    SimplePojo testRestFul(@PathVariable("userName") String username, @PathVariable("age") int age) throws Exception, IOException {
        SimplePojo smpojo = new SimplePojo();
        smpojo.setUserName(username);
        smpojo.setAge(age);
        return smpojo;

    }


    private Logger log = LoggerFactory.getLogger(TestController.class);

    /**A
     * 测试  日志
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testLog/{userName}", method = {RequestMethod.POST, RequestMethod.GET})//,produces = "application/json")
    public @ResponseBody
    SimplePojo testLog(@PathVariable("userName") String username ) throws Exception, IOException {
        log.debug("试试...");
        SimplePojo smpojo = new SimplePojo();
        smpojo.setUserName(username);
        return smpojo;

    }

    /**A
     * 测试 maven相互依赖
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testMaven", method = {RequestMethod.POST, RequestMethod.GET})//,produces = "application/json")
    public @ResponseBody
    SimpleService testMaven() throws Exception, IOException {
        log.debug("试试...");
        SimpleService smpojo  =new SimpleService();

        return smpojo;

    }


    @RequestMapping("/test111")
    public ModelAndView enterTestHello(Model model){
        model.addAttribute("yan","yan");
        ModelAndView modelAndView =new ModelAndView("layuitest");
        modelAndView.addObject("name","123");
        return modelAndView;
    }

    @RequestMapping("/test222")
    public ModelAndView enterTestHello1(Model model){
        model.addAttribute("yan","yan");
        ModelAndView modelAndView =new ModelAndView("layuitest");
        modelAndView.addObject("name","123");
        return modelAndView;
    }


    @RequestMapping("/test333")
    public ModelAndView enterTestHello3(Model model){
        model.addAttribute("yan","yan");
        ModelAndView modelAndView =new ModelAndView("admin/index");
        modelAndView.addObject("name","123");
        return modelAndView;
    }
}
