package de.bmarwell.j9kwsolver.lib;

import de.bmarwell.j9kwsolver.J9kwCaptchaAPI;
import de.bmarwell.j9kwsolver.lib.service.PropertyService;
import de.bmarwell.j9kwsolver.lib.service.ResponseSanitizer;
import de.bmarwell.j9kwsolver.request.CaptchaSolution;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;
import de.bmarwell.j9kwsolver.response.RequestCaptchaResponse;

import org.immutables.gson.stream.GsonMessageBodyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public final class DefaultJ9kwCaptcha implements J9kwCaptchaAPI {

  public static final Logger LOG = LoggerFactory.getLogger(DefaultJ9kwCaptcha.class);

  private static final String J9KW_SERVER_HOST = "https://www.9kw.eu";

  private static final String J9KW_CAPTCHA_PATH = "index.cgi";

  private PropertyService propertyService;

  public DefaultJ9kwCaptcha(PropertyService propertyService) {
    this.propertyService = propertyService;
  }

  @Override
  public CompletableFuture<RequestCaptchaResponse> getNewCaptcha(boolean tryLoop) {
    return CompletableFuture
        .supplyAsync(() -> getNewCaptcha());
  }

  private RequestCaptchaResponse getNewCaptcha() {
    Client client = ClientBuilder.newBuilder()
        .register(new GsonMessageBodyProvider())
        .build();

    WebTarget target = client
        .target(J9KW_SERVER_HOST)
        .path(J9KW_CAPTCHA_PATH)
        .queryParam("action", "usercaptchanew")
        .queryParam("apikey", propertyService.getApiKey())
        .queryParam("source", propertyService.getToolName())
        .queryParam("extended", "1")
        // file data integrated in json.
        .queryParam("filedata", "1")
        .queryParam("base64", "1")
        .queryParam("debug", "1")
        .queryParam("json", "1");

    LOG.debug("Target: [{}].", target);

    Response response = target
        .request(MediaType.APPLICATION_JSON_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .get();

    ResponseSanitizer.sanitizeResponse(response);

    LOG.debug("Response: [{}].", response);
    LOG.debug("MT: [{}].", response.getMediaType());
    RequestCaptchaResponse captcha = response.readEntity(RequestCaptchaResponse.class);

    LOG.debug("Captcha: [{}].", captcha);

    return captcha;
  }

  @Override
  public CompletableFuture<CaptchaSolutionResponse> solveCaptcha(CaptchaSolution solution) {
    // TODO Auto-generated method stub
    return null;
  }

}
