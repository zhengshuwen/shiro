package sys.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import shiro.user.UserBean;
import shiro.user.UserService;
import shiro.user.impl.UserServiceImpl;


public class MyRealm extends AuthorizingRealm{
	@Autowired
	UserService userServiceImpl;
	private Logger log = Logger.getLogger(MyRealm.class);
	@Override
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	/**
	 * 授权
	 * @param PrincipalCollection principals
	 * @return AuthorizationInfo
	 * 会进入授权方法一共有三种情况！
		1、subject.hasRole(“admin”) 或 subject.isPermitted(“admin”)：自己去调用这个是否有什么角色或者是否有什么权限的时候；
		2、@RequiresRoles("admin") ：在方法上加注解的时候；
		3、[@shiro.hasPermission name = "admin"][/@shiro.hasPermission]：在页面上加shiro标签的时候，即进这个页面的时候扫描到有这个标签的时候。
	 * */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String userid = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userServiceImpl.findRoles(userid));
		authorizationInfo.setStringPermissions(userServiceImpl.findPermissions(userid));
		authorizationInfo.getRoles().forEach((value)->{
			log.info("用户权限角色:"+value);
		});
		authorizationInfo.getStringPermissions().forEach((value)->{
			log.info("用户拥有的权限:"+value);
		});
		return authorizationInfo;
	}
	
	/**
	 * 认证
	 * 当调用Subject currentUser = SecurityUtils.getSubject();
	 * 			currentUser.login(token);
	 * @param AuthenticationToken token
	 * @return AuthenticationInfo
	 * */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("进入认证的方法：doGetAuthenticationInfo");
        String username = (String)token.getPrincipal();
        UserBean user = userServiceImpl.findByName(username);
        if(user == null) {
        	log.info("没有找到账号，账号不存在",new UnknownAccountException());
            throw new UnknownAccountException();//没找到帐号
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
        	log.info("帐号已锁定，不能登录！",new LockedAccountException());
            throw new LockedAccountException(); //帐号锁定
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserid(), //用户名
                user.getPassword(), //密码
                //ByteSource.Util.bytes(user.getUsername()),//salt=username
                getName()  //realm name
        );
        return authenticationInfo;
	}

}
