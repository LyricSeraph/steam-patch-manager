package top.lyriclaw.spm.backend.config;

import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.AllArgsConstructor;
import top.lyriclaw.spm.backend.constant.RequestHeaders;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class OpenApiConfig {

    static {
        io.swagger.v3.core.jackson.ModelResolver.enumsAsRef = true;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/**")
                .pathsToExclude("/error")
                .addOperationCustomizer((GlobalOperationCustomizer) (operation, handlerMethod) ->
                        operation.addParametersItem(
                                new Parameter()
                                        .in("header")
                                        .required(false)
                                        .description("Auth token")
                                        .name(RequestHeaders.AUTH_TOKEN)
                                        .example("0000000000000000")
                        ))
                .build();
    }

}
