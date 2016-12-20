/*
 * J9KW Solver Library
 * Copyright (C) 2016, j9kwsolver contributors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package de.bmarwell.j9kwsolver.lib;

import de.bmarwell.j9kwsolver.J9kwCaptchaAPI;
import de.bmarwell.j9kwsolver.lib.service.PropertyService;
import de.bmarwell.j9kwsolver.lib.service.ResponseSanitizer;
import de.bmarwell.j9kwsolver.request.CaptchaSolution;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;
import de.bmarwell.j9kwsolver.response.RequestCaptchaResponse;

import com.google.common.base.Preconditions;

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
    Preconditions.checkNotNull(solution);
    Preconditions.checkNotNull(solution.captcha());

    return CompletableFuture
        .supplyAsync(() -> submitCaptcha(solution));
  }

  private CaptchaSolutionResponse submitCaptcha(CaptchaSolution solution) {
    Client client = ClientBuilder.newBuilder()
        .register(new GsonMessageBodyProvider())
        .build();

    WebTarget target = client
        .target(J9KW_SERVER_HOST)
        .path(J9KW_CAPTCHA_PATH)
        .queryParam("action", "usercaptchacorrect")
        .queryParam("apikey", propertyService.getApiKey())
        .queryParam("source", propertyService.getToolName())
        .queryParam("extended", "1")
        .queryParam("id", solution.id())
        // this is the answer
        .queryParam("captcha", solution.captcha())
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

    CaptchaSolutionResponse solutionResponse = response.readEntity(CaptchaSolutionResponse.class);

    LOG.debug("Solution response: [{}].", solutionResponse);

    return solutionResponse;
  }

}
