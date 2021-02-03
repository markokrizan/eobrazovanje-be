package rs.ac.uns.ftn.education.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

  @Bean
  public SpringResourceTemplateResolver htmlTemplateResolver(){
      SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
      templateResolver.setPrefix("classpath:/templates/mail-templates/");
      templateResolver.setSuffix(".html");
      templateResolver.setTemplateMode("HTML");
      templateResolver.setCharacterEncoding("UTF-8");

      return templateResolver;
  }

  @Bean
  public TemplateEngine thymeleafTemplateEngine() {
      TemplateEngine templateEngine = new TemplateEngine();
      templateEngine.addTemplateResolver(htmlTemplateResolver());

      return templateEngine;
  }
}
