package kramphub.example;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Collections;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public AsyncRestTemplate asyncRestTemplate(){

        MappingJackson2HttpMessageConverter mappingConverter = new MappingJackson2HttpMessageConverter();
        mappingConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));

        AsyncRestTemplate asycTemp = new AsyncRestTemplate();
        asycTemp.getMessageConverters().add(mappingConverter);

        return asycTemp;

    }

    /**
     *
     * @todo handle scaling dynamicly.... Just set it to two because it we have only two api endpoint.
     */
    @Bean
    public ScheduledThreadPoolExecutor poolExecutor() {
      return new ScheduledThreadPoolExecutor(2);
    }

}
