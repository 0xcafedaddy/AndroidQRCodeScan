package com.qlu.android.product.interceptor;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qlu.android.product.exception.AuthorizationException;
import com.qlu.android.product.util.Constant;

/**
 * Created by yang
 *
 */
public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);
    private static final String[] excludeUrls = {"logout","login","index","welcome","findProduct"};

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        if(logger.isDebugEnabled()){
            logger.debug("验证请求:[" + requestUrl + "]!" );
        }

        HttpSession session  = request.getSession();
        if(session.getAttribute(Constant.SESSION_USER) == null) {
            if(logger.isDebugEnabled()){
                logger.debug("拦截非法请求:[" + requestUrl + "]!" );
            }
            throw new AuthorizationException();
        } else {
            //不做拦截的请求
            for (String  excludeUrl: excludeUrls) {
                if(requestUrl.indexOf(excludeUrl)!=-1){
                    return true;
                }
            }
            
            if(logger.isDebugEnabled()){
                logger.debug("拦截非法请求:[" + requestUrl + "]!" );
            }
            //无权限访问，返回首页
            Writer w = response.getWriter();
            JSONObject obj = new JSONObject();
            obj.put(Constant.KEY_STATUS, Constant.STATUS_ERROR);
            obj.put(Constant.KEY_MSG, "have no permission to request this URL");
            w.write(obj.toString());
            w.close();
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
           HttpServletResponse response, Object handler,
           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }



}
