package by.nikolajuk.spring.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;


@Configuration
@ComponentScan("by.nikolajuk.spring")
@EnableWebMvc
@PropertySource("classpath:database.properties")
public class SpringConfig implements WebMvcConfigurer {
	
	  private final ApplicationContext applicationContext;
	  private final Environment environment; 

	    @Autowired
	    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
	        this.applicationContext = applicationContext;
	        this.environment = environment;
	    }

	    @Bean
	    public SpringResourceTemplateResolver templateResolver() {
	        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	        templateResolver.setApplicationContext(applicationContext);
	        templateResolver.setPrefix("/WEB-INF/views/");
	        templateResolver.setSuffix(".html");
	        return templateResolver;
	    }

	    @Bean
	    public SpringTemplateEngine templateEngine() {
	        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver());
	        templateEngine.setEnableSpringELCompiler(true);
	        return templateEngine;
	    }

	    @Override
	    public void configureViewResolvers(ViewResolverRegistry registry) {
	        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	        resolver.setTemplateEngine(templateEngine());
	        registry.viewResolver(resolver);
	    }
	    
	    @Bean
	    public DataSource dataSource() {
	    	  DriverManagerDataSource dataSource = new DriverManagerDataSource();

	          dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
	          dataSource.setUrl(environment.getProperty("url"));
	          dataSource.setUsername(environment.getProperty("username_value"));
	          dataSource.setPassword(environment.getProperty("password"));

	          return dataSource;
	    	
	    }
	    @Bean
	    public JdbcTemplate jdbcTemplate() {
	        return new JdbcTemplate(dataSource());
	    }

}
