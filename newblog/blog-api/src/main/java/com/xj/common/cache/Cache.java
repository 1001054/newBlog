package com.xj.common.cache;

import java.lang.annotation.*;

//type代表可以放在类上面，Method代表可以放在方法上面
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    //过期时间
    long expire() default 1 * 60 * 1000;
    //缓存标识 key
    String name() default "";
}
