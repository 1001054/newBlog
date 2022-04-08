package com.xj.handler;

import com.alibaba.fastjson.JSON;
import com.xj.pojo.SysUser;
import com.xj.service.LoginService;
import com.xj.utils.UserThreadLocal;
import com.xj.vo.ErrorCode;
import com.xj.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    //在调用controller方法(Handler)之前先执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求的接口路径是否为HandlerMethod (controller方法)
        if(!(handler instanceof HandlerMethod)){
            //handler可能是RequestResourceHandler
            //springboot程序访问静态资源 默认去classpath下的static目录下查询
            return true;
        }
        //检查是否登录
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //检查token是否合法（对照数据库中的信息）
        SysUser sysUser = loginService.checkToken(token);
        if(sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //将用户对象放入到ThreadLocal中
        UserThreadLocal.put(sysUser);
        //验证成功
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //将ThreadLocal中的信息删除，防止内存泄漏
        UserThreadLocal.remove();
    }
}
