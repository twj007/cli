import com.api.dict.DictService;
import com.backend.BackEndApp;
import com.backend.service.TestService;
import com.backend.service.user.UserService;
import com.common.pojo.ShiroUser;
import com.common.pojo.dict.Dict;
import com.common.pojo.dict.DictDetail;
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

    @Test
    public void testMenuByUser(){
        ShiroUser user = new ShiroUser();
        user.setId(1L);
        List<Menu> menu = userService.getMenuListByUser(user);
        System.out.println(menu);
        Assert.assertNotEquals(null, menu);
    }

    @Test
    public void testTransaction(){
        ShiroUser user = new ShiroUser();
        user.setUsername("zzz");
        user.setPassword("213456");
        ShiroUser u = userService.register(user);
        System.out.println(u);
        Assert.assertNotEquals(null, u);
    }

    @Autowired
    DictService dictService;
    @Test
    public void testDict(){
        Dict dict = new Dict();
        dict.setKeyType("meat");
        List<Dict> dicts = dictService.getDicts(dict);
        System.out.println(dicts);
        Assert.assertNotEquals(null, dicts);
    }

    @Test
    public void testSaveDict(){
        Dict dict = new Dict();
        dict.setKeyType("json");
        dict.setValue("fast json");
        dict.setDescription("fast json desc");
        Dict dict1 = dictService.saveDict(dict);
        DictDetail detail = new DictDetail();
        detail.setParentId(dict1.getId());
        detail.setKeyType("content-Type");
        detail.setValue("application/json");
        DictDetail detail1 = dictService.saveDictDetail(detail);
        Assert.assertNotEquals(null, dict1);
        Assert.assertNotEquals(null, detail1);


    }
}
