package sys.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import shiro.user.UserBean;
import shiro.user.UserService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
/**
 * 登录请求处理
 * */
public class FormLoginFilter extends AccessControlFilter {

    private String loginUrl = "/loginCheck.do";
    private Logger log = Logger.getLogger(MyRealm.class);
    @Autowired
	UserService userServiceImpl;
    	
    /**
     * 登录
     * @param HttpServletRequest req
     * @return boolean
     * */
    private boolean login(HttpServletRequest req) throws UnsupportedEncodingException {
    	req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (Exception e) {
        	log.info("shiroLoginFailure", e);
            req.setAttribute("shiroLoginFailure", e.getClass().getName());
            return false;
        }
        return true;
    }
    
    private boolean captcha(String captcha,HttpSession session){
    	String sCaptcha=(String)session.getAttribute("captcha");
    	boolean ischeck=false;
    	if(captcha!=null&&captcha.equals(sCaptcha)){
    		ischeck=true;
    	}
    	log.info("验证码是否正确？"+ischeck);
		return ischeck;
    }

    private boolean isLoginRequest(HttpServletRequest req) {
    	log.info("uri:"+WebUtils.getPathWithinApplication(req));
        return pathsMatch(loginUrl, WebUtils.getPathWithinApplication(req));
    }

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
    	String username = req.getParameter("username");
    	String captcha=req.getParameter("captcha");
    	HttpSession session=req.getSession();
    	/**查看验证码是否正确*/
        if(!captcha(captcha,session))
        	return false;
        
    	String currentUserid=(String) SecurityUtils.getSubject().getPrincipal();
    	UserBean user = userServiceImpl.findById(currentUserid);
    	/**判断是否已经登录*/
    	if (SecurityUtils.getSubject().isAuthenticated()&&user.getUsername()==username) {
    		log.info("用户已经登录！");
            return true;//已经登录过
        }
    	
    	log.info("login!");
    	
        /**查看url是不是登录的url*/
        if (isLoginRequest(req)) {
            if ("post".equalsIgnoreCase(req.getMethod())) {//form表单提交
                boolean loginSuccess = login(req); //登录
                if (loginSuccess) {
                	log.info("用户成功登录！");
                    return true;
                }
            }
        }  
        log.info("用户登录失败！");
        return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}
}
