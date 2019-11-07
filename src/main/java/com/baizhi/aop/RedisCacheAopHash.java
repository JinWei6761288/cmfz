package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

@Configuration//表明当前类是配置类
@Aspect //通知
public class RedisCacheAopHash {

    @Autowired
    private Jedis jedis;

    //环绕通知
    //返回值 包 类 方法 形参
    @Around("execution(* com.baizhi.service.impl.*.findAll(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object target = proceedingJoinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if (b) {
            //目标方法上存在该注解
            String className = target.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder();
            String methodName = method.getName();
            stringBuilder.append(methodName).append("(");
            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(args[i]);
                if (i == args.length - 1) {
                    break;
                }
                stringBuilder.append(",");
            }
            stringBuilder.append(")");
            if (jedis.hexists(className, stringBuilder.toString())) {
                //判断redis中是否存在对应的key
                String hget = jedis.hget(className, stringBuilder.toString());
                return JSONObject.parse(hget);
            } else {
                Object result = proceedingJoinPoint.proceed();
                jedis.hset(className, stringBuilder.toString(), JSONObject.toJSONString(result));
                return result;
            }
        } else {
            //不存在该注解
            Object o = proceedingJoinPoint.proceed();
            return o;
        }
    }

    //后置通知,针对增删改操作
    @After("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.selectAll(..))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(ClearRedisCache.class);
        String className = target.getClass().getName();
        if(b){
            jedis.del(className);
        }
    }
}
