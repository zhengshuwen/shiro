package sys.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import shiro.user.UserBean;
import shiro.user.UserService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
/**
 * 登录过滤器
 * */
public class FormLoginFilter extends PathMatchingFilter {

    private String loginUrl = "/login";
    private String successUrl = "/";
    private Logger log = Logger.getLogger(MyRealm.class);
    @Autowired
	UserService userServiceImpl;
    
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	HttpServletRequest req = (HttpServletRequest) request;
        /**查看url是不是登录的url*/
        if (isLoginRequest(req)) {
            if ("post".equalsIgnoreCase(req.getMethod())) {//form表单提交
                boolean loginSuccess = login(req); //登录
                if (loginSuccess) {
                    return true;
                }
            }
        }
        return true;
    }

//    private boolean redirectToSuccessUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        WebUtils.redirectToSavedRequest(req, resp, successUrl);
//        return false;
//    }
//
//    private void saveRequestAndRedirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//    	System.out.println("saveRequestAndRedirectToLogin");
//        WebUtils.saveRequest(req);
//        WebUtils.issueRedirect(req, resp, loginUrl);
//    }
    /**
     * 登录
     * @param HttpServletRequest req
     * @return boolean
     * */
    private boolean login(HttpServletRequest req) throws UnsupportedEncodingException {
    	req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        log.info("用户:"+username+"，密码："+password);
        String currentUserid=SecurityUtils.getSubject().getPrincipal().toString();
    	UserBean user = userServiceImpl.findById(currentUserid);
    	/**判断是否已经登录*/
    	if (SecurityUtils.getSubject().isAuthenticated()&&user.getUsername()==username) {
    		log.info("用户已经登录！");
            return true;//已经登录过
        }
        
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (Exception e) {
            req.setAttribute("shiroLoginFailure", e.getClass().getName());
            return false;
        }
        return true;
    }

    private boolean isLoginRequest(HttpServletRequest req) {
        return pathsMatch(loginUrl, WebUtils.getPathWithinApplication(req));
    }
}
