/**
 * 
 */
package de.bmarwell.j9kwsolver.test;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.request.CaptchaGet;
import de.bmarwell.j9kwsolver.request.CaptchaNewOk;
import de.bmarwell.j9kwsolver.request.CaptchaReturn;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;
import de.bmarwell.j9kwsolver.util.RequestToURI;

/**
 * @author bmarwell
 *
 */
public class J9kwSolver {
	private static final Logger log = LoggerFactory.getLogger(J9kwSolver.class);
	
	private static CaptchaReturn getRequest(String apikey) {
		CloseableHttpResponse response = null;
		CaptchaReturn cr = null;
		
		CaptchaGet cg = new CaptchaGet();
		cg.setDebug(true);
		cg.setApikey(apikey);
		cg.setExtended(true);
		cg.setSource(PropertyService.getProperty("toolname"));
		
		URI uri = RequestToURI.captchaGetToURI(cg);
		
		CloseableHttpClient httpclient = HttpConnectorFactory.getHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		
		log.debug("Requesting URI: {}.", httpGet.getURI());
		
		try {
			response = httpclient.execute(httpGet);
			cr = RequestToURI.captchaGetResponseToCaptchaReturn(response);
		} catch (IOException e) {
			log.error("Fehler beim HTTP Request!", e);
		} finally {
			IOUtils.closeQuietly(httpclient);
		}
		
		return cr;
	}
	
	private static boolean returnReceived(String apikey) {
		CloseableHttpResponse response = null;
		boolean success = false;
		Scanner s = null;
		
		CaptchaNewOk cno = new CaptchaNewOk();
		cno.setApikey(apikey);
		cno.setSource(PropertyService.getProperty("toolname"));
		
		URI uri = RequestToURI.captchaNewOkToURI(cno);
		
		CloseableHttpClient httpclient = HttpConnectorFactory.getHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		
		log.debug("Requesting URI: {}.", httpGet.getURI());
		
		try {
			response = httpclient.execute(httpGet);
			StringWriter writer = new StringWriter();
			IOUtils.copy(response.getEntity().getContent(), writer);
			
//			s = new Scanner(response.getEntity().getContent());
//			StringBuilder answ = new StringBuilder();
//			while (s.hasNext()) {
//				answ.append(s.nextLine());
//			}
			
			log.debug("Input: ", writer.toString());
		} catch (IOException e) {
			log.error("Fehler beim HTTP Request!", e);
		} finally {
			IOUtils.closeQuietly(httpclient);
			IOUtils.closeQuietly(s);
		}
		
		return success;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CaptchaReturn cr = null;
		String apiKey = null;
		apiKey = PropertyService.getProperty("apikey");

		assert apiKey != null : "apiKey konnte nicht gelesen werden.";
		assert apiKey != "" : "apiKey konnte nicht gelesen werden.";
		assert apiKey.length() > 8 : "apiKey ist zu kurz.";
		
		while (cr == null) {
			cr = getRequest(apiKey);
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (cr != null) {
			log.debug("CR ungleich null!");
			returnReceived(apiKey);
			
		}
	}
	

}
