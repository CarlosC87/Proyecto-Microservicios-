package com.fleet.composite.config;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient vehicleWebClient(WebClient.Builder builder, DownstreamProperties props) {
        return builder.baseUrl(props.vehicleUrl())
                .clientConnector(connector(props))
                .build();
    }

    @Bean
    public WebClient driverWebClient(WebClient.Builder builder, DownstreamProperties props) {
        return builder.baseUrl(props.driverUrl())
                .clientConnector(connector(props))
                .build();
    }

    private ReactorClientHttpConnector connector(DownstreamProperties props) {
        HttpClient http = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) props.timeout().toMillis())
                .responseTimeout(props.timeout());
        return new ReactorClientHttpConnector(http);
    }
}
