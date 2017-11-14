package sys.filter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import sys.shiro.MyRealm;


public class RequestNumberFilter implements Filter{
	private Logger log = Logger.getLogger(MyRealm.class);
	
	/** 用于保存当前正在处理的请求数量*/
	private static AtomicInteger nowCount = new AtomicInteger(0);  
	
	/** 每天的请求数量*/
	private static AtomicInteger dayCount=new AtomicInteger(0);
	
	/** 每天合法的请求数量*/
	private static AtomicInteger dayUnrefuseCount=new AtomicInteger(0);
	
	/** 每天不合法的请求数量*/
	private static AtomicInteger dayRefuseCount=new AtomicInteger(0);
	
	/** 最大并发允许数量, 负数表示不限制 */  
	private int maxConcurrent = -1; 
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		/**有请求进入，当天请求数增加一个，当前并发量增加一个*/
		dayCountIncrease();
		int num = nowCountIncrease();
		/**查看请求并发量是否达到最大限制值*/
		if(this.maxConcurrent>0&&num>this.maxConcurrent){
			threadWait();
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "请求并发量达到最大并发数限制:" + this.maxConcurrent);
		}else{
			try{
				filterChain.doFilter(request, response);
			}catch(Exception e){
				/** 请求出现异常，不和法*/
				dayRefuseCountIncrease();
			}finally{
				/** 请求退出，减少当前并发量*/
				nowCountDecrement();
				dayUnrefuseCountIncrease();
				System.out.println("请求："+request.getRequestURI()+"，退出！");
			}
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		/**
		 * 配置最大并发量
		 * */
		String maxStr = filterConfig.getInitParameter("maxConcurrent");
		int num=-1;
		if(maxStr!=null&&maxStr.equals("")){
			try{
				num=Integer.parseInt(maxStr); 
			}catch(NumberFormatException e){
				log.error("初始化最大在线人数出错，配置类型错误！",e);
			}
		}
		this.maxConcurrent=num;
	}
	
	/**
	 * 请求等待
	 * */
	public void threadWait(){
//		while(true){
//			if(requestEnable){
//				break;
//			}
//		}
	}
	/**  
	 * 获取当前并发数   
	 */  
	public static int getNowCount(){  
		return nowCount.get();  
	}  
      
	/** 
	 * 增加并发数量  
	 */  
	public static int nowCountIncrease(){  
		return nowCount.incrementAndGet();  
	}  
      
	/** 
	 * 减少并发数量 
	 */  
	public static int nowCountDecrement(){  
		return nowCount.decrementAndGet();  
	} 
	
	/**  
	 * 获取当天的请求数量   
	 */  
	public static int getDayCount(){  
		return dayCount.get();  
	}  
      
	/** 
	 * 增加当天的请求数量  
	 */  
	public static int dayCountIncrease(){  
		return dayCount.incrementAndGet();  
	}  
      
	
	/**  
	 * 获取当天不合法的请求   
	 */  
	public static int getDayRefuseCount(){  
		return dayRefuseCount.get();  
	}  
      
	/** 
	 * 增加当天不合法的请求 
	 */  
	public static int dayRefuseCountIncrease(){  
		return dayRefuseCount.incrementAndGet();  
	}  
      
	/** 
	 * 减少当天不合法的请求
	 */  
	public static int dayRefuseCountDecrement(){  
		return dayRefuseCount.decrementAndGet();  
	}
	/**  
	 * 获取当天合法的请求
	 */  
	public static int getDayUnrefuseCount(){  
		return dayUnrefuseCount.get();  
	}  
      
	/** 
	 * 增加当天合法的请求  
	 */  
	public static int dayUnrefuseCountIncrease(){  
		return dayUnrefuseCount.incrementAndGet();  
	}  
      
	/** 
	 * 减少当天合法的请求
	 */  
	public static int dayUnrefuseCountDecrement(){  
		return dayUnrefuseCount.decrementAndGet();  
	}
}
