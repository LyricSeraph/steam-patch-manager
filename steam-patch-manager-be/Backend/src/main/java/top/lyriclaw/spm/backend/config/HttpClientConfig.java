package top.lyriclaw.spm.backend.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import top.lyriclaw.spm.backend.api.SteamApis;

import javax.net.ssl.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class HttpClientConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Proxy createProxy() {
        String httpProxy = extractProxy();
        if (!StringUtils.hasLength(httpProxy)) {
            return Proxy.NO_PROXY;
        }

        try {
            URI uri = URI.create(httpProxy);
            SocketAddress sa = new InetSocketAddress(uri.getHost() + uri.getPath(), uri.getPort());
            if (uri.getScheme().startsWith("http") || uri.getScheme().startsWith("https")) {
                return new Proxy(Proxy.Type.HTTP, sa);
            } else if (uri.getScheme().startsWith("socks")) {
                return new Proxy(Proxy.Type.SOCKS, sa);
            } else {
                log.warn("Unsupported proxy protocol: {}", uri.getScheme());
            }
        } catch (Exception ex) {
            log.warn("Cannot recognize proxy url: {}", httpProxy);
        }
        return Proxy.NO_PROXY;
    }

    private String extractProxy() {
        String httpProxy = extractEnv("HTTP_PROXY");
        if (StringUtils.hasLength(httpProxy)) {
            return httpProxy;
        }
        httpProxy = extractEnv("HTTPS_PROXY");
        if (StringUtils.hasLength(httpProxy)) {
            return httpProxy;
        }
        return null;
    }

    private String extractEnv(String key) {
        String httpProxy = System.getenv(key.toLowerCase(Locale.ROOT));
        if (StringUtils.hasLength(httpProxy)) {
            return httpProxy;
        }
        httpProxy = System.getenv(key.toUpperCase(Locale.ROOT));
        if (StringUtils.hasLength(httpProxy)) {
            return httpProxy;
        }
        return null;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Retrofit createRetrofit(@Autowired OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(SteamApis.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @SneakyThrows
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public OkHttpClient createHttpClient(@Autowired Proxy proxy) {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                .build();
        return new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .proxy(proxy)
                .connectionSpecs(Collections.singletonList(spec))
                .sslSocketFactory(getSSLSocketFactory(), getTrustManager())
                .hostnameVerifier((s, sslSession) -> true)
                .build();
    }

    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[] {getTrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static X509TrustManager getTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
    }

}
