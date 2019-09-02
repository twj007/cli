package com.common.utils;

import com.common.pojo.user.Menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 权限数据处理
 * 
 * @author ruoyi
 */
public class TreeUtils
{

    public static void main(String[] args) {
        List<Menu> menus = new ArrayList<>();
        Menu m = new Menu();
        m.setPId(0L);
        m.setId(1L);
        m.setName("菜单1");
        Menu m2 = new Menu();
        m2.setPId(0L);
        m2.setId(2L);
        m2.setName("菜单2");
        Menu m3 = new Menu();
        m3.setPId(0L);
        m3.setId(3L);
        m3.setName("菜单3");
        Menu m4 = new Menu();
        m4.setPId(1L);
        m4.setId(4L);
        m4.setName("子菜单1");
        Menu m5 = new Menu();
        m5.setPId(4L);
        m5.setId(5L);
        m5.setName("子菜单1-1");
        Menu m6 = new Menu();
        m6.setPId(4L);
        m6.setId(6L);
        m6.setName("子菜单1-2");
        Menu m7 = new Menu();
        m7.setPId(5L);
        m7.setId(7L);
        m7.setName("子菜单1-1-1");
        menus.add(m);
        menus.add(m2);
        menus.add(m3);
        menus.add(m4);
        menus.add(m5);
        menus.add(m6);
        menus.add(m7);
        List<Menu> mm = getChildPerms(menus, m.getPId());
        System.out.println(mm);

    }

    /**
     * 根据父节点的ID获取所有子节点
     * 
     * @param list 分类表
     * @return String
     */
    public static List<Menu> getChildPerms(List<Menu> list, Long parentId)
    {
        List<Menu> returnList = new ArrayList<Menu>();
        //获取一级菜单
        List<Menu> parentList = list.stream().filter(l -> l.getPId() == parentId).collect(Collectors.toList());
        Iterator it = parentList.stream().iterator();
        while(it.hasNext()){
            Menu t = (Menu) it.next();
            recursion(list, t);
            returnList.add(t);
        }
        return returnList;
    }

    private static void recursion(List<Menu> list, Menu t){
        List<Menu> childList = list.stream().filter(p -> p.getPId() == t.getId()).collect(Collectors.toList());
        t.setChild(childList);
        for(Menu m : childList){
            recursion(list, m);
        }
    }

    /**
     * 递归列表
     * 
     * @param list
     */
    private static void recursionFn(List<Menu> list, Menu t)
    {
        // 得到子节点列表
        List<Menu> childList = getChildList(list, t);
        t.setChild(childList);
        for (Menu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<Menu> it = childList.iterator();
                while (it.hasNext())
                {
                    Menu n = (Menu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private static List<Menu> getChildList(List<Menu> list, Menu t)
    {

        List<Menu> tlist = new ArrayList<Menu>();
        Iterator<Menu> it = list.iterator();
        while (it.hasNext())
        {
            Menu n = (Menu) it.next();
            if (n.getPId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    List<Menu> returnList = new ArrayList<Menu>();

    /**
     * 根据父节点的ID获取所有子节点
     * 
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @param prefix 子节点前缀
     */
    public List<Menu> getChildPerms(List<Menu> list, int typeId, String prefix)
    {
        if (list == null)
        {
            return null;
        }
        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext();)
        {
            Menu node = (Menu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (node.getPId() == typeId)
            {
                recursionFn(list, node, prefix);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*
             * if (node.getParentId()==0) { recursionFn(list, node); }
             */
        }
        return returnList;
    }

    private void recursionFn(List<Menu> list, Menu node, String p)
    {
        // 得到子节点列表
        List<Menu> childList = getChildList(list, node);
        if (hasChild(list, node))
        {
            // 判断是否有子节点
            returnList.add(node);
            Iterator<Menu> it = childList.iterator();
            while (it.hasNext())
            {
                Menu n = (Menu) it.next();
                n.setName(p + n.getName());
                recursionFn(list, n, p + p);
            }
        }
        else
        {
            returnList.add(node);
        }
    }

    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<Menu> list, Menu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
