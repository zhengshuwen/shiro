package shiro.user;

import java.util.Date;
import java.util.List;
import java.util.Set;

import shiro.user.impl.UserServiceImpl;

/**
 * 表：用户表（ssmuser）实体类信息
 * 作者：郑书文
 * 生成日期：2017-11-08
 * */
public class UserBean{
	/**
	 * 用户id
	 * */
	private String userid;
	/**
	 * 用户名称
	 * */
	private String username;
	/**
	 * 密码
	 * */
	private String password;
	/**
	 * 创建时间
	 * */
	private Date createtime;
	/**
	 * 最后一次更新时间
	 * */
	private Date updatetime;
    
    private Set<String> roleIds; //拥有的角色列表
    
    private Boolean locked = Boolean.FALSE;
    
    
    public String getCredentialsSalt() {
        return this.username;
    }

	public Set<String> getRoleIds() {
		return roleIds;
	}


	public void setRoleIds() {
		UserService userService = new UserServiceImpl();
		this.roleIds = userService.findRoles(this.username);
	}


	public Boolean getLocked() {
		return locked;
	}


	public void setLocked(Boolean locked) {
		this.locked = locked;
	}


	/**
	 * 设置：用户id
	 * */
	public void setUserid(String userid){
		this.userid=userid;
	};


	/**
	 * 获取：用户id
	 * */
	public String getUserid(){
		return userid;
	};


	/**
	 * 设置：用户名称
	 * */
	public void setUsername(String username){
		this.username=username;
	};


	/**
	 * 获取：用户名称
	 * */
	public String getUsername(){
		return username;
	};


	/**
	 * 设置：密码
	 * */
	public void setPassword(String password){
		this.password=password;
	};


	/**
	 * 获取：密码
	 * */
	public String getPassword(){
		return password;
	};


	/**
	 * 设置：创建时间
	 * */
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	};


	/**
	 * 获取：创建时间
	 * */
	public Date getCreatetime(){
		return createtime;
	};


	/**
	 * 设置：最后一次更新时间
	 * */
	public void setUpdatetime(Date updatetime){
		this.updatetime=updatetime;
	};


	/**
	 * 获取：最后一次更新时间
	 * */
	public Date getUpdatetime(){
		return updatetime;
	};
}