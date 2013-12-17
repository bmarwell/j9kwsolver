/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
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
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Benjamin Marwell
 * This class will return various connectors, which might prove useful.
 */
public class HttpConnectorFactory {
	private static final Logger log = LoggerFactory.getLogger(HttpConnectorFactory.class);
	private static CloseableHttpClient httpClient = getHttpClient();
	
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
	
	public static String getBodyFromRequest(URI uri) {
		CloseableHttpResponse response = null;
		String responseBody = null;
		HttpGet httpGet = new HttpGet(uri);
		
		log.debug("Requesting URI: {}.", httpGet.getURI());
		
		try {
			response = httpClient.execute(httpGet);
			StringWriter writer = new StringWriter();
			IOUtils.copy(response.getEntity().getContent(), writer);
			responseBody = writer.toString();
		} catch (IOException e) {
			log.error("Fehler beim HTTP Request!", e);
		} finally {
//			IOUtils.closeQuietly(httpclient);
		}
		
		return responseBody;
	}
}
