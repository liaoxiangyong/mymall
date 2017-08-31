package edu.ccnt.mymall;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MymallApplicationTests {

	@Autowired
	private IUserService iUserService;
	@Test

	public void contextLoads() {
		ServerResponse<String> serverResponse = iUserService.checkAnswer("Tom","111","222");
		System.out.println(0);
	}

}
