package sys.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shiro.user.UserBean;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login(UserBean user,HttpServletRequest request,HttpServletResponse response){
		System.out.println("userLoginController!");
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        //TODO 这里以后可以把角色更换成资源控制后动态生成页面,（-->这里有疑问-->是不是可以使用自定义角色？shiro张开涛的16章有个自定义标签扫描出的角色）
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        boolean isAuthenticated = subject.isAuthenticated();

//        if (isAuthenticated) {
//            String principal = (String) subject.getPrincipal();
//            System.out.println("principal:"+principal);
//            switch (principal) {
//                case "admin":
//                    return "/admin/main";
//                case "teacher":
//                    return "/teacher/main";
//                case "student":
//                    return "/student/main";
//                case "supplier":
//                    return "redirect:supplier.do/supplier.view";
//            }
//        }
        if(error==null||error.isEmpty()){
        	return "myweb.html";
        }else
        	return "redirect:index.html";
	}
}
