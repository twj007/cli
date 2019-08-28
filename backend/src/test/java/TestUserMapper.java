import com.backend.BackEndApp;
import com.backend.service.TestService;
import com.backend.service.user.UserService;
import com.common.pojo.ShiroUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@SpringBootTest(classes = BackEndApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserMapper {

    @Autowired
    UserService userService;

   @Autowired
    TestService testService;

    @Test
    public void testMapper(){
//        testService.test();
        ShiroUser u = new ShiroUser();
        u.setUsername("jien");
        u.setPassword("123456");
        u.setId(1L);
        u = userService.login(u);
        System.out.println(u);
        Assert.assertNotEquals(null, u);
    }
}
