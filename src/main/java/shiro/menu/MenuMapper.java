package shiro.menu;

import java.util.List;

public interface MenuMapper {
	/**
	*查询：（menu）信息
	*@throws Exception
	**/
	public List<MenuBean> select(MenuBean menuBean);
	/**
	*新增：（menu）信息
	*@throws Exception
	**/
	public int insert(MenuBean menuBean);
	/**
	*更新：（menu）信息
	*@throws Exception
	**/
	public int update(MenuBean menuBean);
	/**
	*删除：（menu）信息
	*@throws Exception
	**/
	public void delete(MenuBean menuBean);
	/**获取顶级标题*/
	public List<MenuBean> selectTopMenu();
	/**根据父标题获取父标题下的子标题*/
	public List<MenuBean> selectByFatherMenuId(String fatherMenuId);
	
	public String getMenuJson(List<MenuBean> list);
}
