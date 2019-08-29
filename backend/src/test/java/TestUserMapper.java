import com.backend.BackEndApp;
import com.backend.service.TestService;
import com.backend.service.user.UserService;
import com.common.pojo.ShiroUser;
import com.common.pojo.user.Menu;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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

    @Test
    public void testMenu(){
        Menu m = new Menu();
        m.setPId(0L);
        m.setUserId(1L);
        List<Menu> mm =  userService.getMenu(m);
        System.out.println(mm);
        Assert.assertNotEquals(null, mm);

    }

    @Test
    public void testSalave(){
        long status = userService.getRecord(1L);
        System.out.println(status);
        Assert.assertEquals(1L, status);
    }
}
