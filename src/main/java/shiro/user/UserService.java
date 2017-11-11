package shiro.user;

import java.util.List;
import java.util.Set;


public interface UserService {
	/**查找用户的权限角色*/
	public Set<String> findRoles(String userid);
	/**查找用户的访问权限*/
	public Set<String> findPermissions(String userid);
	/**根据id查找用户*/
	public UserBean findById(String userid);
	
	public UserBean findByName(String userName);
	
	public void delete(UserBean userBean);
	public int update(UserBean userBean);
	public int insert(UserBean userBean);
	public List<UserBean> select(UserBean userBean);
	
	
}
