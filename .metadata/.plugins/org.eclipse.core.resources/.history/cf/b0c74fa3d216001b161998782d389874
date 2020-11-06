package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

@WebFilter(filterName="F2",urlPatterns="/*")
public class SSOFilter implements Filter {
	private FilterConfig filterConfig = null;
	private String cookieName = "DesktopSSOID";
	private String SSOServiceURL = "http://localhost:8080/SSOAuth/SSOAuth";
	private String SSOLoginPage = "http://localhost:8080/SSOAuth/login.jsp";
	public void init(FilterConfig filterConfig) {
		System.out.println("------demo1初始化-----");
		this.filterConfig = filterConfig;
//		this.cookieName = filterConfig.getInitParameter("cookieName");
//		this.SSOServiceURL = filterConfig.getInitParameter("SSOServiceURL");
//		this.SSOLoginPage = filterConfig.getInitParameter("SSOLoginPage");
	}
	public void destroy() {}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// 控制台输出 拦截成功
		System.out.println("------demo1拦截器-------");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String result = "failed";
		String path = request.getContextPath(); 
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		System.out.println("demo1-----loginout请求-----"+url);
		// 获取路径参数？后部分内容
		String qstring = request.getQueryString();
		if (qstring == null) qstring = "";
		// 检查http请求的head是否有需要的cookie
		String cookieValue = "";
		Cookie[] diskCookies = request.getCookies();
		// 如果登录找到对应的cookie
		System.out.println("diskCookies:"+diskCookies);
		if (diskCookies != null) {
			for (int i = 0; i < diskCookies.length; i++) {
				if (diskCookies[i].getName().equals(this.cookieName)) {
					cookieValue = diskCookies[i].getValue();
					// 如果找到了相应的cookie则效验其有效性
					result = SSOService(cookieValue);
					System.out.println("-----demo1找到了 cookies!----");
				}
			}
		}
		// 效验失败或没有找到cookie，则需要登录
		if (result.equals("failed")) {
			response.sendRedirect(this.SSOLoginPage + "?goto=" + url);
		} else if (qstring.indexOf("logout") > 1) {
			// logout服务
			System.out.println("-----从demo1退出登陸!----");
			logoutService(cookieValue);
			response.sendRedirect(this.SSOLoginPage + "?goto=" + url);
		} else {
			// 效验成功
			request.setAttribute("SSOUser", result);  
			Throwable problem = null;
			try {
				chain.doFilter(req, res);
			} catch (Throwable t) {
				problem = t;
				t.printStackTrace();
			}
			if (problem != null) {
				if ((problem instanceof ServletException))
					throw ((ServletException) problem);
				if ((problem instanceof IOException))
					throw ((IOException) problem);
				sendProcessingError(problem, res);
			}
		}
	}
	// 校验登录cookie
	private String SSOService(String cookievalue) throws IOException {
		String authAction = "?action=authcookie&cookiename=";
		HttpClient httpclient = new HttpClient();
		GetMethod httpget = new GetMethod(this.SSOServiceURL + authAction+ cookievalue);
		System.out.println("GetMethod,"+this.SSOServiceURL + authAction+ cookievalue);
		try {
			httpclient.executeMethod(httpget);
			String result = httpget.getResponseBodyAsString();
			System.out.println("------------demo1------------"+result);
			return result;
		} finally {
			httpget.releaseConnection();
		}
	}
	// 校验登出cookie
	private void logoutService(String cookievalue) throws IOException {
		String authAction = "?action=logout&cookiename=";
		HttpClient httpclient = new HttpClient();
		GetMethod httpget = new GetMethod(this.SSOServiceURL + authAction+ cookievalue);
		try {
			httpclient.executeMethod(httpget);
			httpget.getResponseBodyAsString();
		} finally {
			httpget.releaseConnection();
		}
	}
	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);
		if ((stackTrace != null) && (!stackTrace.equals(""))) {
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);
				pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");
				pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
				pw.print(stackTrace);
				pw.print("</pre></body>\n</html>");
				pw.close();
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		} else{
				try {
					PrintStream ps = new PrintStream(response.getOutputStream());
					t.printStackTrace(ps);
					ps.close();
					response.getOutputStream().close();
				} catch (Exception ex) {}
		}
			
	}

	public static String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception ex) {
		}
		return stackTrace;
	}
	
	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}