package edu.ccnt.mymall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by LXY on 2017/10/9.
 */
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {
}
