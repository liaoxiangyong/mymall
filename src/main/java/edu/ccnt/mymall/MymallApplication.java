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

//	@Bean
//	@Autowired
//	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource);
//		//mybatis.typeAliasesPackage：指定domain类的基包，即指定其在*Mapper.xml文件中可以使用简名来代替全类名（看后边的UserMapper.xml介绍）
////		sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
// 		/* mybatis.mapperLocations：指定*Mapper.xml的位置 如果不加会报org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.blog.mapper.MessageMapper.findMessageInfo异常 因为找不到*Mapper.xml，也就无法映射mapper中的接口方法。 */
//		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
//		return sqlSessionFactoryBean.getObject();
//	}
	public static void main(String[] args) {
		SpringApplication.run(MymallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
