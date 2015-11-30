package com.tilf.troke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/*
@Configuration
@EnableAutoConfiguration
@ComponentScan
*/
@SpringBootApplication
public class TrokeApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(TrokeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // Customize the application or call application.sources(...) to add sources
        // Since our example is itself a @Configuration class we actually don't
        // need to override this method.
        return application;
    }

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer(
    ) throws Exception {


        return (ConfigurableEmbeddedServletContainer container) -> {

            if (container instanceof TomcatEmbeddedServletContainerFactory) {

                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addConnectorCustomizers(
                        (connector) -> {
                            connector.setMaxPostSize(20000000);//20MB
                        }
                );
            }
        };
    }
    /*
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return container -> {

            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page);
        };
    }
    */
}
