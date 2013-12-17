package de.bmarwell.j9kwsolver.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpConnectorFactory {
	public static CloseableHttpClient getHttpClient() {
		RequestConfig rc = RequestConfig.copy(RequestConfig.DEFAULT)
				.setSocketTimeout(30000)
				.setConnectTimeout(60000)
				.build();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(rc)
				.build();
		
		return httpClient;
	}
}
