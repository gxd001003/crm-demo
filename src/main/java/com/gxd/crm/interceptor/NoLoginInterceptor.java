package com.gxd.crm.interceptor;



import com.gxd.crm.dao.UserMapper;
import com.gxd.crm.exceptions.NoLoginException;
import com.gxd.crm.pojo.User;
import com.gxd.crm.utils.LoginUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;



public class NoLoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;
    /**
     * 拦截用户是否是登陆状态
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);

        if(userId == null || userMapper.selectByPrimaryKey(userId) == null){
            throw new NoLoginException();
        }
        return true;
    }
}
