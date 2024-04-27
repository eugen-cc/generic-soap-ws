package cc.eugen.backend.soap.xdataservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;

import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
@Slf4j
public class WebserviceConfig {

    @Value("${webservice.context.root}")
    private String contextRoot;

    @Value("${webservice.name}")
    private String webserviceName;

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        log.debug(contextRoot);
        final var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, String.format("/%s/*", contextRoot));
    }

    @Bean(name = "XDataWebservice")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        final var wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("XDataPort");
        wsdl11Definition.setLocationUri(String.format("/%s", contextRoot));
        wsdl11Definition.setTargetNamespace("http://eugen.cc/spring-boot-soap-xdata");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schema.xsd"));
    }

}
