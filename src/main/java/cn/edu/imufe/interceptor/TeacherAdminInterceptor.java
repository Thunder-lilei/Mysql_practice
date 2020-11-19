package cn.edu.imufe.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.imufe.util.IfLoginUtil;


/**
 * @author lilei
 *
 * 2020年11月19日
 */
public class TeacherAdminInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//执行完毕，返回前拦截
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		//处理过程中执行拦截
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//在拦截点执行前拦截，如果返回true则不执行拦截点后的操作（拦截成功）
		//返回false则不执行拦截
		//管理员和老师可以全部访问
		if(!IfLoginUtil.TheRole("teacher", request.getSession()) && !IfLoginUtil.TheRole("admin", request.getSession())) {
			//角色认证失败，且未登录
			if(!IfLoginUtil.IfLogin(request.getSession())) {
				System.out.println("拦截执行"+request.getServletPath());
				response.sendRedirect(request.getContextPath()+"/admin/login.html");
				return false;
			}
			else {
				System.out.println("拦截执行"+request.getServletPath());
				response.sendRedirect(request.getContextPath()+"/index.html");
				return false;
			}
		}else{
			//角色认证成功，不拦截
			System.out.println(IfLoginUtil.GetRole(request.getSession())+"访问");
			return true;
		}
	}

}