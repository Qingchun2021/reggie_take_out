package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**登录过滤器
 * @author xukai
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = {"/*"})
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取本次请求的URI
        String requestUri = request.getRequestURI();
        log.info("拦截到请求:{}", requestUri);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"};

        //如果不需要处理，全部放行
        if (checkMatchedUri(urls, requestUri)) {
            log.info("本次请求{}不需要处理", requestUri);
            filterChain.doFilter(request, response);
            return;
        }

        //如果该用户已经登录，放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录,用户id为{}", request.getSession().getAttribute("employee"));
            Long empId = (Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            long id = Thread.currentThread().getId();
            log.info("当前线程id:{}", id);
            filterChain.doFilter(request, response);
            return;
        }

        //用户未登录，返回未登录结果，通过输出流方式向客户端页面响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    public boolean checkMatchedUri(String[] urls, String requestUri) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUri);
            if (match) {
                return true;
            }
        }
        return false;
    }



}
