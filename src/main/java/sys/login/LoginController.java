package sys.login;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shiro.user.UserBean;
import sys.shiro.MyRealm;

@Controller
public class LoginController {
	private Logger log = Logger.getLogger(MyRealm.class);
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		System.out.println("userLoginController!");
        Subject user = SecurityUtils.getSubject();
		
        boolean isAuthenticated = user.isAuthenticated();

        if(isAuthenticated){
        	return "redirect:myweb";
        }else{
        	return "redirect:index.html";
        }
	}
	
	@RequestMapping("/myweb")
	public String myweb(){
		return "myweb.html";
	}

	
}
