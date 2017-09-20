package edu.ccnt.mymall;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("edu.ccnt.mymall.dao")
@SpringBootApplication
public class MymallApplication implements CommandLineRunner {

	private static final String TYPE_ALIASES_PACKAGE = "edu.ccnt.mymall.model";
	private static final String MAPPER_LOCATION = "classpath:/mybatis/*.xml";

	public static void main(String[] args) {
		SpringApplication.run(MymallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
