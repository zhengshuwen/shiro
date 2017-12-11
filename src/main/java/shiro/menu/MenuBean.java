package shiro.menu;

/**
 * 表：（menu）实体类信息
 * 作者：郑书文
 * 生成日期：2017-12-06
 * */
public class MenuBean{
	/**
	 * menuId
	 * */
	private String menuid;
	/**
	 * menuName
	 * */
	private String menuname;
	/**
	 * fatherMenuId
	 * */
	private String fathermenuid;
	/**
	 * menuUrl
	 * */
	private String menuurl;



	/**
	 * 设置：menuId
	 * */
	public void setMenuid(String menuid){
		this.menuid=menuid;
	};


	/**
	 * 获取：menuId
	 * */
	public String getMenuid(){
		return menuid;
	};


	/**
	 * 设置：menuName
	 * */
	public void setMenuname(String menuname){
		this.menuname=menuname;
	};


	/**
	 * 获取：menuName
	 * */
	public String getMenuname(){
		return menuname;
	};


	/**
	 * 设置：fatherMenuId
	 * */
	public void setFathermenuid(String fathermenuid){
		this.fathermenuid=fathermenuid;
	};


	/**
	 * 获取：fatherMenuId
	 * */
	public String getFathermenuid(){
		return fathermenuid;
	};


	/**
	 * 设置：menuUrl
	 * */
	public void setMenuurl(String menuurl){
		this.menuurl=menuurl;
	};


	/**
	 * 获取：menuUrl
	 * */
	public String getMenuurl(){
		return menuurl;
	};
}