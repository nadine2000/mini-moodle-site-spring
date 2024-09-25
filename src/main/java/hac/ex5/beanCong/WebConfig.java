package hac.ex5.beanCong;

import hac.ex5.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * Web Configuration for Interceptor to authentication
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Interceptor I used
     */
    @Autowired
    private Interceptor myInterceptor;

    /**
     * adding my Interceptor to the Interceptor Registry
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor);
    }

    /**
     * allow access to all static folders
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}


