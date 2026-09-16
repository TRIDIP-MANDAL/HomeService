package dev.Tridip.HomeService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.Tridip.HomeService.middleware.IsLoggedin;
import dev.Tridip.HomeService.middleware.StopReAuth;

@Configuration 
public class WebConfig implements WebMvcConfigurer{
    
    private final @NonNull IsLoggedin is_loggedin;
    private final @NonNull StopReAuth stop_reAuth;
    
    public WebConfig( @NonNull IsLoggedin is_loggedin, @NonNull StopReAuth stop_reAuth){
        this.is_loggedin = is_loggedin;
        this.stop_reAuth = stop_reAuth;
    }
    
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        // WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(is_loggedin)
        .addPathPatterns("/api/**","/v1/auth/logout");

        registry.addInterceptor(stop_reAuth)
        .addPathPatterns("/v1/auth/login","/v1/auth/signup");

    }
}
