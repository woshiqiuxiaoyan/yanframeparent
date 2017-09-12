package user.controller;

import annotation.HelloYan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pojo.User;
import redis.util.RedisClientTemplate;
import redis.util.RedisUtil;
import user.dto.TDemo;
import user.service.TestService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("TestController2")
@RestController
public class TestController2 {

    @Autowired
    public TestService testService;

    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Autowired
    private RedisUtil redisUtil ;


    /**
     * 测试  spring vildation 分给校验002
     *
     * @param t
     * @param resp
     */
    @RequestMapping(value = "/testVildatorGroup002", method = {RequestMethod.POST, RequestMethod.GET})
    public void testVildatorGroup001(@Validated TDemo t, BindingResult bindingResult, HttpServletResponse resp) {
        try {

            if (bindingResult.hasErrors()) {

                int lenght = bindingResult.getAllErrors().size();

                for (int i = 0 ; i < lenght; i++) {

                    String aa = bindingResult.getAllErrors().get(i).getDefaultMessage();

                    aa = new String(aa.getBytes("ISO-8859-1"),"utf-8");

                    System.out.println(aa);

                }

            }

            resp.getWriter().print(t);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试 redis 技持
     */
    @HelloYan
    @RequestMapping(value = "/testRedis")
    public User testRedis( ) {
        User user = new User();
        try {


            user.setId(123);
            user.setName("丘小燕");
            redisUtil.addSet("存入","123123123");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    


}
