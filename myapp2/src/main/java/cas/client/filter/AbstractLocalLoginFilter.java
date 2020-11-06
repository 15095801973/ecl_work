package cas.client.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cas.client.Constants;
import domains.User;

/**
 * Servlet Filter implementation class LocalLoginFilter
 */
abstract public class AbstractLocalLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AbstractLocalLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	final public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session=((HttpServletRequest)request).getSession();
		Boolean CAS_LOGIN=(Boolean) session.getAttribute(Constants.LOCAL_LOGINED);
		User u1=(User) session.getAttribute("user");
		System.out.println("AbstractLoca:CAS_LOGIN: "+CAS_LOGIN);
		if(CAS_LOGIN!=null&&CAS_LOGIN && u1!=null)
			chain.doFilter(request, response);
		else
		{
			if(doLogin((HttpServletRequest)request,(HttpServletResponse)response))
			{
				session.setAttribute(Constants.LOCAL_LOGINED, true);
				chain.doFilter(request, response);
			}else{
				loginFail((HttpServletRequest)request,(HttpServletResponse)response);
			}
		}
	}

	abstract protected void loginFail(HttpServletRequest request,
			HttpServletResponse response);

	abstract protected boolean doLogin(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
