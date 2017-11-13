package shiro.user;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import shiro.user.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.ui.ModelMap;
import java.util.List;


@Controller
public class UserController{
	@Autowired
	UserServiceImpl userServiceImpl;

	/**
	*查询：用户表（ssmuser）信息
	*@throws Exception
	**/
	//@RequiresPermissions(value = { "/userGet" })
	@RequestMapping("/userGet")
	public void userGet(UserBean userBean,ModelMap model,HttpServletRequest request) throws Exception{ 
		userBean=new UserBean();
		System.out.println("权限测试！/userGet");
		//List<UserBean> listMsg = userServiceImpl.select(userBean);
	}

	/**
	*新增：用户表（ssmuser）信息
	*@throws Exception
	**/
	//@RequiresPermissions(value={"/userPut"})
	@RequestMapping("/userPut")
	public void userPut(UserBean userBean,ModelMap model,HttpServletResponse response, HttpServletRequest request) throws Exception{
		System.out.println("权限测试！/userPut");
		//userServiceImpl.insert(userBean);
	}

	/**
	*更新：用户表（ssmuser）信息
	*@throws Exception
	**/
	@RequiresPermissions(value = {"/userPost"})
	@RequestMapping(value="/userpost/{username}",method=RequestMethod.POST)
	public String userPost(@PathVariable("username")String username,UserBean user,ModelMap model,HttpServletResponse response, HttpServletRequest request) throws Exception{
		System.out.println("权限测试！/userPost");
		userServiceImpl.update(user);
		return "success.html";
	}

	/**
	*删除：用户表（ssmuser）信息
	*@throws Exception
	**/
	//@RequiresPermissions(value = {"/userDelete"})
	@RequestMapping("/userDelete")
	public void userDelete(UserBean userBean,ModelMap model,HttpServletResponse response, HttpServletRequest request) throws Exception{
		System.out.println("权限测试！/userDelete");
		//userServiceImpl.delete(userBean);
	}

}