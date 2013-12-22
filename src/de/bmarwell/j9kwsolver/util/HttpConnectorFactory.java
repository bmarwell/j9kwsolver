/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.util;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Benjamin Marwell
 * This class will return various connectors, which might prove useful.
 */
public final class HttpConnectorFactory {
	/**
	 * Default connect timeout.
	 */
	private static final int CONNECT_TIMEOUT_MS = 30000;
	/**
	 * Default socket timeout.
	 */
	private static final int SOCKET_TIMEOUT_MS = 30000;
	/**
	 * Logger instance for this class.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(HttpConnectorFactory.class);
	/**
	 * The httpClient object for this application / API.
	 */
	private static CloseableHttpClient httpClient;
	
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		
		RequestConfig rc = RequestConfig.copy(RequestConfig.DEFAULT)
				.setSocketTimeout(SOCKET_TIMEOUT_MS)
				.setConnectTimeout(CONNECT_TIMEOUT_MS)
				.build();
		
		httpClient = HttpClients.custom()
				.setDefaultRequestConfig(rc)
				.setConnectionManager(cm)
				.build();
	}
	
	/**
	 * Empty private default constructor for utility class HttpConnectorFactory.
	 */
	private HttpConnectorFactory() { }
	
	/**
	 * Returns the single httpClient for this application to use.
	 * @return the httpClient to be used for requests.
	 */
	public static CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
	/**
	 * Shuts down the connector instance, if open.
	 * @return true if connection could be closed.
	 */
	public static boolean shutdownConnector() {
		IOUtils.closeQuietly(httpClient);
		
		return true;
	}
	
	/**
	 * Returns the Body from the URI via http request.
	 * @param uri the URI to get the body from.
	 * @return the Body as String.
	 */
	public static String getBodyFromRequest(final URI uri) {
		CloseableHttpResponse response = null;
		String responseBody = null;
		HttpGet httpGet = new HttpGet(uri);
		
		LOG.debug("Requesting URI: {}.", httpGet.getURI());
		
		try {
			response = httpClient.execute(httpGet);
			StringWriter writer = new StringWriter();
			IOUtils.copy(response.getEntity().getContent(), writer);
			responseBody = writer.toString();
		} catch (IOException e) {
			LOG.error("Fehler beim HTTP Request!", e);
		}
		
		return responseBody;
	}
}
