package shiro.menu;

import static sys.response.ResponseWriter.writer;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MenuController {
	@Autowired
	MenuMapper menuServiceImpl;
	
	@ResponseBody
	@RequestMapping("getMenu.do")
	public String getMenu(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<MenuBean> list=menuServiceImpl.selectTopMenu();
		String json = menuServiceImpl.getMenuJson(list);
		//writer(response,json,"json");
		System.out.println();
		return json;
	}
	
	@RequestMapping("menuManage.do")
	public String menuManage(){
		return null;
	}
	
}
