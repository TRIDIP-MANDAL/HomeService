package dev.Tridip.HomeService.middleware;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dev.Tridip.HomeService.utils.JwtUtils;
import dev.Tridip.HomeService.dto.response.ApiRespDto;

@Component
public class StopReAuth implements HandlerInterceptor{
    private final JwtUtils jwt;
    public StopReAuth(JwtUtils jwt){
        this.jwt = jwt;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Before request come "+ request.getRequestURI());

        String token = jwt.getJwtTokenFromCookie(request.getCookies());
        if(token == null || !jwt.validateToken(token)){
            return true;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ObjectMapper object = new ObjectMapper();
        ApiRespDto<String> resp = new ApiRespDto<>(false, "You already loggedin, can,t login again", null);
        response.setContentType("application/json");
        String respJson = object.writeValueAsString(resp);
        response.getWriter().write(respJson);
        return false;
    }
}
