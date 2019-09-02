import com.api.admin.AdminService;
import com.api.dict.DictService;
import com.backend.BackEndApp;
import com.backend.service.TestService;
import com.backend.service.menu.MenuService;
import com.backend.service.user.UserService;
import com.common.pojo.ShiroUser;
import com.common.pojo.dict.Dict;
import com.common.pojo.dict.DictDetail;
import com.common.pojo.role.Role;
import com.common.pojo.user.Menu;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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
        List<Menu> mm =  menuService.getMenu(m);
        System.out.println(mm);
        Assert.assertNotEquals(null, mm);

    }

    @Autowired
    private MenuService menuService;

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
        List<Menu> menu = menuService.getMenuListByUser(user);
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

    @Test
    public void testSaveMenu(){
        Menu menu = new Menu();
        menu.setName("用户管理新增");
        menu.setPId(0L);
        menu.setUserId(1L);
        menu.setDesc("新增菜单");
        menu.setType("M");
        menu.setRoleId(1L);
        Menu mm = menuService.saveMenu(menu);
        Assert.assertNotEquals(null, mm);
    }

    @Autowired
    private AdminService adminService;

    @Test
    public void testRoles(){
        Role role = new Role();
        role.setName("finance");
        List<Role> roles = adminService.getRoles(role);
        Assert.assertNotEquals(null, roles);

    }


    @Test
    public void testBatch(){
        Dict dict = new Dict();
        dict.setKeyType("测试名");
        dict.setValue("测试值");
        dict.setDescription(" 1");
        Dict dict2 = new Dict();
        dict2.setKeyType("测试名2");
        dict2.setValue("测试值2");
        dict2.setDescription(" 2");
        List<DictDetail> list = new ArrayList<>();
        DictDetail d1 = new DictDetail();
        d1.setKeyType("字典名1");
        d1.setValue("字典值1");
        d1.setDescription(" ");
        DictDetail d2 = new DictDetail();
        d2.setKeyType("字典名2");
        d2.setValue("字典值2");
        d2.setDescription(" 3");
        list.add(d1);list.add(d2);
        dict.setDetail(list);
        dict2.setDetail(list);
        List<Dict> dicts = new ArrayList<>();
        dicts.add(dict); dicts.add(dict2);
        Long num = dictService.importDict(dicts);
        Assert.assertNotEquals(null, num);
    }
}
