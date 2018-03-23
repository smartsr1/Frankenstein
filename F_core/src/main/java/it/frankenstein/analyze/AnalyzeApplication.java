package it.frankenstein.analyze;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

@SpringBootApplication(scanBasePackages = "it.frankenstein")
@EnableAutoConfiguration
@PropertySources({
		@PropertySource("classpath:common.properties"),
		@PropertySource("classpath:application.properties")
})
public class AnalyzeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyzeApplication.class, args);
	}

	@Bean
	public com.sun.jersey.api.client.Client getClient() throws NoSuchAlgorithmException, KeyManagementException {

		TrustManager[] trustManager = new X509TrustManager[] { new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {

					}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {

					}
		} };

		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustManager, null);

		ClientConfig config = new DefaultClientConfig();
		HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
		HTTPSProperties prop = new HTTPSProperties(hostnameVerifier, sslContext);
		config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, prop);
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
		return client;

	}

}
