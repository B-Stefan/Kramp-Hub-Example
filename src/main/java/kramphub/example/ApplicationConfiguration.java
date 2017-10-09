package kramphub.example;

import com.codahale.metrics.annotation.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StopWatch;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.stagemonitor.core.Stagemonitor;
import org.stagemonitor.core.metrics.MonitorGauges;
import org.stagemonitor.core.metrics.metrics2.Metric2Registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.stagemonitor.core.metrics.metrics2.MetricName.name;

@Configuration
public class ApplicationConfiguration {

    /**
     * Class to track the response time of the external API's
     * @todo Research about the exact callstack here. Maybe the stopwatch instance get called to early.
     */
    public class PerfRequestSyncInterceptor implements AsyncClientHttpRequestInterceptor {

        private final Logger LOG = LoggerFactory.getLogger(PerfRequestSyncInterceptor.class);

        @Override
        public ListenableFuture<ClientHttpResponse> intercept(HttpRequest request, byte[] body, AsyncClientHttpRequestExecution execution) throws IOException {
            final StopWatch stopwatch = new StopWatch();
            stopwatch.start(); // Maybe triggered to early ...
            ListenableFuture<ClientHttpResponse> response = execution.executeAsync(request, body);
            final Metric2Registry registry = Stagemonitor.getMetric2Registry();
            response.addCallback(new ListenableFutureCallback<ClientHttpResponse>() {
                @Override
                public void onFailure(Throwable ex) {
                    stopwatch.stop();
                    registry.timer(name("itunes_api_request_duration").tag("stage", "itunes").build())
                            .update(stopwatch.getTotalTimeMillis(), TimeUnit.MILLISECONDS);
                }

                @Override
                public void onSuccess(ClientHttpResponse result) {
                    stopwatch.stop();
                    registry.timer(name("itunes_api_request_duration").tag("stage", "itunes").build())
                            .update(stopwatch.getTotalTimeMillis(), TimeUnit.MILLISECONDS);

                }
            });

            return response;
        }
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate(){

        MappingJackson2HttpMessageConverter mappingConverter = new MappingJackson2HttpMessageConverter();
        mappingConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));

        AsyncRestTemplate asycTemp = new AsyncRestTemplate();
        asycTemp.getMessageConverters().add(mappingConverter);

        final List<AsyncClientHttpRequestInterceptor> requestInterceptors = new ArrayList<>();
        requestInterceptors.add(new PerfRequestSyncInterceptor());
        asycTemp.setInterceptors(requestInterceptors);

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
