package dev.Tridip.HomeService.middleware;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.Tridip.HomeService.utils.JwtUtils;
import dev.Tridip.HomeService.dto.response.ApiRespDto;

@Component
public class IsLoggedin implements HandlerInterceptor{

    private final JwtUtils jwt;

    public IsLoggedin(JwtUtils jwt){
        this.jwt = jwt;
    }

    @Override
    public boolean preHandle( @NonNull HttpServletRequest request, @NonNull HttpServletResponse response,  @NonNull Object handler)
            throws Exception {
                System.out.println("Before request come "+ request.getRequestURI());
         String token = jwt.getJwtTokenFromCookie(request.getCookies());
         if(token != null && jwt.validateToken(token)){
            return true;
         }
         // If not logged in, return custom JSON response
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
         response.setContentType("application/json");
         
         ApiRespDto<String> errorResponse = new ApiRespDto<>(false, "Unauthorized! Please log in first.", null);
         
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(errorResponse);
         System.out.println("jsonString: "+ jsonString);
         response.getWriter().write(jsonString);
         return false;
    }

    @Override
    public void postHandle( @NonNull HttpServletRequest request,  @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception {
                System.out.println("Response is being sent to client " + request.getRequestURI());
        // HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
             System.err.println("Req, res completion ");
                // HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
