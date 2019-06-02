package io.github.lukamaletin.ftnscholar.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class RestTemplateConfig {

    @Value("${COIN10_MERCHANT_KEY_PASS}")
    private String keyPass;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
        final char[] keyPassArray = keyPass.toCharArray();

        final SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore(keyPassArray), keyPassArray)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

        final HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient)).build();
    }

    private KeyStore keyStore(char[] password) throws Exception {
        final KeyStore keyStore = KeyStore.getInstance("PKCS12");
        final File p12file = ResourceUtils.getFile("classpath:merchantKeyStore.p12");

        try (InputStream inputStream = new FileInputStream(p12file)) {
            keyStore.load(inputStream, password);
        }

        return keyStore;
    }
}
