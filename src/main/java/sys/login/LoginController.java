package sys.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.user.UserBean;
import sys.shiro.MyRealm;

@Controller
public class LoginController {
	private Logger log = Logger.getLogger(MyRealm.class);
	
	@ResponseBody
	@RequestMapping("/loginCheck")
	public String login(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
        Subject user = SecurityUtils.getSubject();
        boolean isAuthenticated = user.isAuthenticated();
        if(isAuthenticated){
        	return "0000";
        }else{
        	return "1111";
        }
        
	}
	
	@RequestMapping("/index.do")
	public String index(){
		Subject user = SecurityUtils.getSubject();
        boolean isAuthenticated = user.isAuthenticated();
        if(isAuthenticated){
        	return "main.html";
        }else{
        	return "login.html";
        }
		
	}

	/**
     * 生成验证码
     */
    @RequestMapping("/captcha.do")
    public void Captcha(HttpServletResponse response, HttpSession session)throws IOException{
        CreateImageCode vCode = new CreateImageCode(116,36,5,10);
        session.setAttribute("captcha", vCode.getCode());
        vCode.write(response.getOutputStream());
    }
}
