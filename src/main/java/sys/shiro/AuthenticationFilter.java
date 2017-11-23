package sys.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * 拦截所有未登录的非登录请求
 * */
public class AuthenticationFilter extends AccessControlFilter{
	private Logger log=Logger.getLogger(AuthenticationFilter.class);
	@Override
	protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue)
			throws Exception {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		log.info("用户没有登录，请求被拒绝！");
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}
}
