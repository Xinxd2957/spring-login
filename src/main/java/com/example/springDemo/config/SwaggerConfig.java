package com.example.springDemo.config;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ModelPropertyBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.schema.ApiModelProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author
 * swagger接口文档基本配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements ModelPropertyBuilderPlugin {
    //    @Value("${swagger.enable}")
//    private boolean enableSwagger;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 生产环境的时候关闭 swagger 比较安全
//                .enable(enableSwagger)
                //可以使用 directModelSubstitute 做一些期望的类型转换,修正Byte转string的Bug
//                .directModelSubstitute(Byte.class, Integer.class)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
//            .apis(RequestHandlerSelectors.basePackage(""))
                .paths(PathSelectors.any())
                .build()
                .forCodeGeneration(true)
                ;
//            .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试")
                .description("测试功能")
//            .termsOfServiceUrl("测试环境url")
                .version("1.0.0")
                .build();
    }


    // 有token验证再搞这个
//    private List<ApiKey> security() {
//        return newArrayList(
//            new ApiKey("token", "token", "header")
//        );
//    }


    @Override
    public void apply(ModelPropertyContext context) {
        //如果不支持swagger的话，直接返回
//        if (!enableSwagger) {
//            return;
//        }

        //获取当前字段的类型
        final Class fieldType = context.getBeanPropertyDefinition().get().getField().getRawType();


        //为枚举字段设置注释
        descForEnumFields(context, fieldType);
    }

    /**
     * 为枚举字段设置注释
     */
    private void descForEnumFields(ModelPropertyContext context, Class fieldType) {
        Optional<ApiModelProperty> annotation = Optional.absent();

        if (context.getAnnotatedElement().isPresent()) {
            annotation = annotation
                    .or(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = annotation.or(Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class));
        }

        //没有@ApiModelProperty 或者 notes 属性没有值，直接返回
        if (!annotation.isPresent() || StringUtils.isBlank((annotation.get()).notes())) {
            return;
        }

        //@ApiModelProperties中的notes指定的class类型
        Class rawPrimaryType;
        try {
            rawPrimaryType = Class.forName((annotation.get()).notes());
        } catch (ClassNotFoundException e) {
            //如果指定的类型无法转化，直接忽略
            return;
        }

        //如果对应的class是一个@SwaggerDisplayEnum修饰的枚举类，获取其中的枚举值
        Object[] subItemRecords = null;
        SwaggerDisplayEnum swaggerDisplayEnum = AnnotationUtils
                .findAnnotation(rawPrimaryType, SwaggerDisplayEnum.class);
        if (null != swaggerDisplayEnum && Enum.class.isAssignableFrom(rawPrimaryType)) {
            subItemRecords = rawPrimaryType.getEnumConstants();
        }
        if (null == subItemRecords) {
            return;
        }


        final List<String> displayValues = Arrays.stream(subItemRecords).filter(Objects::nonNull).map(item -> {
            return item.toString();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        String joinText = " (" + String.join("; ", displayValues) + ")";
        try {
            Field mField = ModelPropertyBuilder.class.getDeclaredField("description");
            mField.setAccessible(true);
            joinText = mField.get(context.getBuilder()) + joinText;
        } catch (Exception e) {
//            log.error(e.getMessage());
        }

        final ResolvedType resolvedType = context.getResolver().resolve(fieldType);
        context.getBuilder().description(joinText).type(resolvedType);
    }


    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

}