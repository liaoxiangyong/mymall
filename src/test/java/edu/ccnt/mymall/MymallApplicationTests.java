package edu.ccnt.mymall;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MymallApplicationTests {

	@Autowired
	private IUserService iUserService;

//	@Autowired
//	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void contextLoads() {
//		ServerResponse<String> serverResponse = iUserService.checkAnswer("Tom","111","222");
//		System.out.println(0);
//		stringRedisTemplate.opsForValue().set("aaa", "111");
//		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
	}

}
