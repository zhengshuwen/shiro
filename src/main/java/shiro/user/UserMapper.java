package shiro.user;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserMapper {
	public void delete(UserBean userBean);
	public int update(UserBean userBean);
	public int insert(UserBean userBean);
	public List<UserBean> select(UserBean userBean);
	public UserBean findByName(String userName);
	public UserBean findById(String userid);
	public List<String> findRoles(String userid);
	public List<String> findPermissions(String userid);
}
