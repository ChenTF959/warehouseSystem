package com.ctf.controller;

import com.ctf.entity.*;
import com.ctf.service.AuthService;
import com.ctf.service.UserService;
import com.ctf.utils.DigestUtil;
import com.ctf.utils.TokenUtils;
import com.ctf.utils.WarehouseConstants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ChenTF
 * @Date: 2023/9/12 16:53
 * @description:
 */


@RestController
public class LoginController {

    //注入DefaultKaptcha的bean对象
    //@Autowired
    @Resource(name = "captchaProducer")
    private Producer producer;

    //注入redis模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    //生成验证码图片的url接口/captcha/captchaImage
    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) {

        ServletOutputStream out = null;
        try {
            //生成验证码图片的文件
            String text = producer.createText();
            //使用验证码文本生成验证码图片 -- BufferedImage对象就代表生成的验证码图片，再内存中
            BufferedImage image = producer.createImage(text);
            //将验证码文本作为键保存到redis, -- 设置键的过期时间为30分钟
            redisTemplate.opsForValue().set(text, "", 60*30, TimeUnit.SECONDS);

            //将验证码图片响应给前端
            //设置响应正文image/jpeg
            response.setContentType("image/jpeg");
            //将验证码图片写给前端
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);//使用响应对象的字节输出流写入验证码图片
            //刷新
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭字节流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //登录的url接口/login

    //参数@RequestBody LoginUser loginUser -- 表示接收并封装前端传递的登录用户信息的Json数据
    //返回值Result对象 -- 表示向前端响应响应结果Result对象转的json串，包含响应状态码 成功失败响应 响应信息 响应数据

    //注入UserService
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {

        //拿到用户输入的验证码
        String  code = loginUser.getVerificationCode();
        if (!redisTemplate.hasKey(code)) {
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码错误!");
        }

        //根据账号查询用户
        User user = userService.queryUserByCode(loginUser.getUserCode());
        if(user != null) {//账号存在
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {//用户已审核
                //拿到用户录入的密码
                String userPwd = loginUser.getUserPwd();
                //进行加密
                userPwd = DigestUtil.hmacSign(userPwd);
                if (userPwd.equals(user.getUserPwd())) { //密码正确
                    CurrentUser currentUser = new CurrentUser(user.getUserId(), user.getUserCode(), user.getUserName());
                    //生成jwt token,并存储到redis
                    String token = tokenUtils.loginSign(currentUser, userPwd);
                    //向客户端响应jwt token
                    return Result.ok("登录成功", token);
                } else {//密码错误
                    return Result.err(Result.CODE_ERR_BUSINESS, "密码错误!");
                }
            } else { //用户未审核
                return Result.err(Result.CODE_ERR_BUSINESS, "用户未审核!");
            }
        } else { //账号不存在
            return Result.err(Result.CODE_ERR_BUSINESS, "用户不存在!");
        }
    }
    //获取当前登录的用户信息的url接口/curr-user
    /*
        @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)
        表示将请求头Token的值(前端归还的token赋值给请求处理方法入参变量token)
     */
    @RequestMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //解析token拿到封装了当前登录用户信息的CurrentUser对象
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //响应
        return Result.ok(currentUser);
    }

    //加载用户权限菜单树的url接口/user/auth-list
    //注入AuthService
    @Autowired
    private AuthService authService;

    @RequestMapping("/user/auth-list")
    public Result loadAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //拿到当前登录用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        List<Auth> authTreeList = authService.authTreeByUid(userId);

        return Result.ok(authTreeList);
    }

    //登出的url接口/logout
    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //从redis中删除token键
        redisTemplate.delete(token);
        //响应
        return Result.ok("退出系统");

    }


}