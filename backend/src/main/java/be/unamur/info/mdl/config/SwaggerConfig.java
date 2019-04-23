package be.unamur.info.mdl.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final Set<String> PRODUCES_AND_CONSUMES_JSON = new HashSet<>(
    Arrays.asList("application/json"));

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("be.unamur.info.mdl.ctrler"))
      .paths(PathSelectors.any())
      .build().apiInfo(apiEndPointsInfo())
      .produces(PRODUCES_AND_CONSUMES_JSON)
      .consumes(PRODUCES_AND_CONSUMES_JSON);
  }

  private ApiInfo apiEndPointsInfo() {

    return new ApiInfoBuilder().title(" REST API")
/*      .description("Employee Management REST API")
      .contact(new Contact("Ramesh Fadatare", "www.javaguides.net", "ramesh24fadatare@gmail.com"))
      .license("Apache 2.0")
      .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
*/
      .version("v0.3.0")
      .build();

  }

}
