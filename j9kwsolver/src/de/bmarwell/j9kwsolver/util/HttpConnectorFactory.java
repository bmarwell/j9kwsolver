/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Benjamin Marwell
 * This class will return various connectors, which might prove useful.
 */
public class HttpConnectorFactory {
	public static CloseableHttpClient getHttpClient() {
		RequestConfig rc = RequestConfig.copy(RequestConfig.DEFAULT)
				.setSocketTimeout(30000)
				.setConnectTimeout(30000)
				.build();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(rc)
				.build();
		
		return httpClient;
	}
}
