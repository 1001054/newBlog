package com.xj.common.cache;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xj.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.Duration;
//aop 定义一个切面，切面定义了切点和通知的关系
@Aspect
@Component
@Slf4j
public class CacheAspect {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //切点，表示Cache注解放在哪里，哪里就是切点
    @Pointcut("@annotation(com.xj.common.cache.Cache)")
    public void pt(){}

    //环绕通知
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp){
        try {
            //类名
            String className = pjp.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = pjp.getSignature().getName();
            //参数类型
            Class[] parameterTypes = new Class[pjp.getArgs().length];
            //获取参数对象
            Object[] args = pjp.getArgs();
            //
            String params = "";
            //将参数对象转换为字符串
            //并把各个参数对象的类型放入类数组中
            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            //获取加注解的方法
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + "::" + className+"::"+methodName+"::"+params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)){
                log.info("读取缓存~~~,{},{}",className,methodName);
                Result result = JSON.parseObject(redisValue, Result.class);
                return result;
            }
            //缓存没有就运行方法
            Object proceed = pjp.proceed();
            //将方法的结果放入redis中
            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存~~~ {},{}",className,methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999,"系统错误");
    }

}

