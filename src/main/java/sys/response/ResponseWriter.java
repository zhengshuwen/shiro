package sys.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseWriter {
	
	/**
	 * @author zhsw
	 * @param data:数据源,type:响应方式
	 * */
	public static void writer(HttpServletResponse response,String data,String type) throws IOException{
		if("json".equals(type))
			response.setContentType("text/javascript; charset=utf-8");
		PrintWriter writer=response.getWriter();
		writer.write(data);
		writer.flush();
		writer.close();
	}
	
}
