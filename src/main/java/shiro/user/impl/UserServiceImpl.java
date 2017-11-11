package shiro.user.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shiro.user.UserBean;
import shiro.user.UserMapper;
import shiro.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper userMapper;
	
	public Set<String> findRoles(String userid) {
		return new HashSet<String>(userMapper.findRoles(userid));
	}
	public Set<String> findPermissions(String userid) {
		return new HashSet<String>(userMapper.findPermissions(userid));
	}
	public UserBean findByName(String userName) {
		return userMapper.findByName(userName);
	}
	public void delete(UserBean userBean) {
		// TODO Auto-generated method stub
		userMapper.delete(userBean);
	}
	public int update(UserBean userBean) {
		// TODO Auto-generated method stub
		return userMapper.update(userBean);
	}
	public int insert(UserBean userBean) {
		// TODO Auto-generated method stub
		return userMapper.insert(userBean);
	}
	public List<UserBean> select(UserBean userBean) {
		// TODO Auto-generated method stub
		return userMapper.select(userBean);
	}
	@Override
	public UserBean findById(String userid) {
		return userMapper.findById(userid);
	}
}