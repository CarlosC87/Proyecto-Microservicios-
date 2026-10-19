package com.fleet.composite.client;

import com.fleet.composite.exception.DownstreamUnavailableException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * Política común hacia los servicios remotos: reintento con backoff solo ante fallos de
 * infraestructura (conexión, timeout, 5xx) y traducción a DownstreamUnavailableException (-> 503).
 * Los 4xx (p. ej. 404) NO se reintentan.
 */
final class DownstreamResilience {

    private DownstreamResilience() {
    }

    static <T> Mono<T> apply(Mono<T> call, String serviceName) {
        return call
                .retryWhen(Retry.backoff(2, Duration.ofMillis(200))
                        .filter(DownstreamResilience::isUnavailable)
                        .onRetryExhaustedThrow((spec, signal) -> signal.failure()))
                .onErrorMap(DownstreamResilience::isUnavailable,
                        e -> new DownstreamUnavailableException(serviceName, e));
    }

    static boolean isUnavailable(Throwable t) {
        return t instanceof WebClientRequestException
                || t instanceof TimeoutException
                || (t instanceof WebClientResponseException e && e.getStatusCode().is5xxServerError());
    }
}
