package com.xj.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xj.mapper")
public class MybatisPlusConfig {
}
