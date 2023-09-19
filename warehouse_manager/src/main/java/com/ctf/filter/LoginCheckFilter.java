package com.ctf.filter;

import com.alibaba.fastjson.JSON;
import com.ctf.entity.Result;
import com.ctf.utils.WarehouseConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 9:07
 * @description:自定义登录限制过滤器
 */

public class LoginCheckFilter implements Filter {


    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //过滤器拦截到请求执行的方法
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request =  (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //白名单请求直接放行
        //创建list集合，放白名单url接口
        List<String> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");
        urlList.add("/logout");
        urlList.add("/product/img-upload");
        //过滤器拦截到的当前请求url接口
        String url = request.getServletPath();
        if (urlList.contains(url)||url.contains("/img/upload")) {//白名单请求，放行
            chain.doFilter(request, response);
            return;
        }

        //其他请求都校验是否携带token以及判断redis中是否存在token的键
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        //有说明已登录，请求放行
        if (StringUtils.hasText(token) && stringRedisTemplate.hasKey(token)) {
            chain.doFilter(request, response);
            return;
        }
        //没有，说明未登录或token过期，请求不放行，并给前端作出响应
//        Map<String, Object> data = new HashMap<>();
//        data.put("code", 401);
//        data.put("message","您尚未登录");
        //也可以将信息封装到map中，再转Json格式

        Result result = Result.err(Result.CODE_ERR_UNLOGINED, "您尚未登录");
        String jsonStr = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonStr);
        out.flush();
        out.close();

    }
}
