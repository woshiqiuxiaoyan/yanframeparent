package configcode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>Title:WebAppConfig </p>
 * <p>Description:丘小燕</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/12/5
 * Time: 10:37
 */
@Configuration
@EnableSwagger2
public class WebAppConfig   {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(this.apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("芊朵恋")
                .termsOfServiceUrl("https://github.com/woshiqiuxiaoyan")
                .version("v1.0")
                .build();
    }
}
