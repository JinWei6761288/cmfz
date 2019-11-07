package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

/*@Configuration//表明当前类是配置类
@Aspect //通知*/
public class RedisCacheAop {

    @Autowired
    private Jedis jedis;

    //环绕通知
    //返回值 包 类 方法 形参
    @Around("execution(* com.baizhi.service.impl.*.findAll(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
      /*  判断目标方法上是否存在RedisCache注解,
      如果存在需要缓存,
      如果不存在,不需要缓存,放行*/

        //获取目标方法
        Object target = proceedingJoinPoint.getTarget();//获取类的对象 target: com.baizhi.service.impl.BannerServiceImpl@7ae26489
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();//获取目标方法 signature: List com.baizhi.service.impl.BannerServiceImpl.findAll(Integer,Integer)
        Object[] args = proceedingJoinPoint.getArgs();//获取目标方法的参数
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if (b) {
            //目标方法上存在RedisCache注解
            //直接访问redis数据库,根据key获取对应的值
            String className = target.getClass().getName();//获取类名
            String methodName = method.getName();//获取方法名
            StringBuilder sb = new StringBuilder();
            sb.append(className).append(".").append(methodName).append("(");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i == args.length - 1) {
                    break;
                }
                sb.append(",");
            }
            sb.append(")");
            String key = sb.toString();
            System.out.println("key : " + key);
            //判断redis缓存中是否存在这个key
            if (jedis.exists(key)) {
                String result = jedis.get(key);
                return JSONObject.parse(result);//转为json
            } else {
                Object result = proceedingJoinPoint.proceed();//放行
                jedis.set(key, JSONObject.toJSONString(result));
                return result;
            }

        } else {
            //目标方法上不存在RedisCache注解
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }

    //后置通知,针对增删改操作
    @After("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.selectAll(..))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();//类的对象
        String className = target.getClass().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();//方法
        Object[] args = joinPoint.getArgs();//参数
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(ClearRedisCache.class);
        if (b) {
            //清除缓存
            Set<String> keys = jedis.keys("*");
            for (String key : keys) {
                if (key.startsWith(className)) {
                    jedis.del(key);

                }
            }
        }
    }
}
