package shiro.menu;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuMapper{
	@Autowired
	MenuMapper menuMapper;
	/**
	*查询：（menu）信息
	**/
	public List<MenuBean> select(MenuBean menuBean){
		return menuMapper.select(menuBean);
	}
	/**
	*新增：（menu）信息
	*@throws Exception
	**/
	public int insert(MenuBean menuBean){
		return menuMapper.insert(menuBean);
	}
	/**
	*更新：（menu）信息
	*@throws Exception
	**/
	public int update(MenuBean menuBean){
		return menuMapper.update(menuBean);
	}
	/**
	*删除：（menu）信息
	*@throws Exception
	**/
	public void delete(MenuBean menuBean){
		menuMapper.delete(menuBean);
	}
	
	private StringBuffer sb=new StringBuffer();
	
	public String getMenuJson(List<MenuBean> list){
		sb.setLength(0);
		return getMenu(list);
	}
	
	public String getMenu(List<MenuBean> list){
		if(list.isEmpty()){
			sb.append("{}");
		}
		if(list.size()>1){
			sb.append("[");
		}
		for(int i=0;i<list.size();i++){
			sb.append("{");
			sb.append(toJson(list.get(i)));
			List<MenuBean> sonlist=selectByFatherMenuId(list.get(i).getMenuid());
			getMenu(sonlist);
			sb.append("}");
			if(i<list.size()-1){
				sb.append(",");
			}
		}
		
		if(sb.substring(sb.length()).toString().equals(",")){
			sb.deleteCharAt(sb.length() - 1);
		}
		if(list.size()>1){
			sb.append("]");
		}
		return sb.toString();
	}
	/**
	 * @return menuId:0101,menuName:测试,fatherMenuId:01,menuUrl:test.do
	 * */
	public String toJson(MenuBean bean){
		bean=removeNull(bean);
		StringBuffer sb=new StringBuffer();
		sb.append("\"menuId\":\""+bean.getMenuid()+"\",");
		sb.append("\"menuName\":\""+bean.getMenuname()+"\",");
		sb.append("\"fatherMenuId\":\""+bean.getFathermenuid()+"\",");
		sb.append("\"menuUrl\":\""+bean.getMenuurl()+"\",");
		sb.append("\"sonMenu\":");
		return sb.toString();
	}
	
	public MenuBean removeNull(MenuBean bean){
		if(bean.getFathermenuid()==null){
			bean.setFathermenuid("");
		}
		return bean;
	}
	@Override
	public List<MenuBean> selectTopMenu() {
		return menuMapper.selectTopMenu();
	}
	@Override
	public List<MenuBean> selectByFatherMenuId(String fatherMenuId) {
		return menuMapper.selectByFatherMenuId(fatherMenuId);
	}
}