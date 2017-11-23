package sys.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
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


public class RequestNumberFilter implements Filter{
	private Logger log = Logger.getLogger(RequestNumberFilter.class);
	/** 时间格式化*/
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** 时间间隔*/
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000; 
	/** 用于保存当前正在处理的请求数量*/
	private static AtomicInteger nowCount = new AtomicInteger(0);  
	
	/** 每天的请求数量*/
	private static AtomicInteger dayCount=new AtomicInteger(0);
	
	/** 每天合法的请求数量*/
	private static AtomicInteger dayUnRefuseCount=new AtomicInteger(0);
	
	
	/** 最大并发允许数量, 负数表示不限制 */  
	private int maxConcurrent = -1; 
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		/**有请求进入，当天请求数增加一个，当前并发量增加一个*/
		log.info(request);
		dayCountIncrease();
		int num = nowCountIncrease();
		/**查看请求并发量是否达到最大限制值*/
		if(this.maxConcurrent>0&&num>this.maxConcurrent){//当前处理的请求数量达到最大值，可以选择排队执行和直接结束请求
			log.debug("请求并发量达到最大限制值!");
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "请求并发量达到最大并发数限制:" + this.maxConcurrent);
		}else{
			try{
				/** 继续请求*/
				filterChain.doFilter(request, response);
			}finally{
				/** 请求退出，减少当前并发量，增加合法请求数量。 304:表示服务器的页面与浏览器上次缓存的相同*/
				System.out.println("请求退出，减少当前并发量,"+response.getStatus());
				if(response.getStatus()!=304){
					nowCountDecrement();
					dayUnRefuseCountIncrease();
				}
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
		/**设置一个定时器*/
		timerManager();
	}
	
	private void timerManager(){
		log.info("初始化每日统计定时器！");
    	Calendar calendar = Calendar.getInstance();          
    	/*** 定制每日上午6:00执行方法 ***/  
		calendar.set(Calendar.HOUR_OF_DAY,18);  
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);     
		Date date=calendar.getTime(); //第一次执行定时任务的时间    
		//如果第一次执行定时任务的时间 小于 当前的时间  
		//此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。循环执行的周期则以当前时间为准  
		if (date.before(new Date())) {  
		    Calendar startDT = Calendar.getInstance();  
	    	startDT.setTime(date);  
	    	startDT.add(Calendar.DAY_OF_MONTH, 1);
	    	date=startDT.getTime();
		}
		Timer timer = new Timer();  
		//安排指定的任务在指定的时间开始进行重复的固定延迟执行。  
		TimerTask task=new TimerTask(){
			@Override
			public void run() {
				String datestr=formatter.format(Calendar.getInstance().getTime());
				//初始化每日数据
				log.info("每日访问数据("+datestr+"):访问请求数量："+dayCount.get()+"，合法的请求数量："+dayUnRefuseCount.get()+"，不合法的请求数量："+(dayCount.get()-dayUnRefuseCount.get()));
				RequestNumberFilter.cleanDay();
			}
		};
		timer.schedule(task,date,PERIOD_DAY);
		log.info("定时器初始化成功！");
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
	private static int nowCountIncrease(){  
		return nowCount.incrementAndGet();  
	}  
      
	/** 
	 * 减少并发数量 
	 */  
	private static int nowCountDecrement(){  
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
	private static int dayCountIncrease(){  
		return dayCount.incrementAndGet();  
	}  
	/** 
	 * 增加当天的合法请求数量  
	 */  
	private static int dayUnRefuseCountIncrease(){  
		return dayUnRefuseCount.incrementAndGet();  
	} 
      

	
	/**
	 * 初始化每日数据
	 * */
	public static void cleanDay(){
		dayCount=new AtomicInteger(0);
		dayUnRefuseCount=new AtomicInteger(0);
	}
}
