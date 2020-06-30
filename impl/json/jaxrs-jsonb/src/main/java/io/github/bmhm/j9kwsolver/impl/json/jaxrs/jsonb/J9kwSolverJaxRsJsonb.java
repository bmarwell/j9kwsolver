/*
 * J9kw solver library
 * Copyright 2013-2020 the j9kwsolver team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb;

import static java.util.Objects.requireNonNull;

import io.github.bmhm.j9kwsolver.api.CaptchaSelectorFactory;
import io.github.bmhm.j9kwsolver.api.J9kwSolverApi;
import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;
import io.github.bmhm.j9kwsolver.api.request.ImmutableJ9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.request.J9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaId;
import io.github.bmhm.j9kwsolver.api.value.CaptchaReceiptResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaRequest;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSelector;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSkipResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolution;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolutionResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaType;
import io.github.bmhm.j9kwsolver.api.value.J9kwServerStatus;
import io.github.bmhm.j9kwsolver.api.value.UserBalance;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.AbstractCaptchaJsonResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.CaptchaSkipJsonResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.CaptchaSolutionJsonResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.JsonResponseMapper;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.lang.Either;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.JsonbMapper;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.WebTargetUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class J9kwSolverJaxRsJsonb implements J9kwSolverApi {

  private static final Logger LOG = Logger.getLogger(J9kwSolverJaxRsJsonb.class.getCanonicalName());

  private static final Jsonb JSONB = JsonbMapper.getInstance();

  private static final String DEFAULT_PATH = "index.cgi";
  private static final String PARAMVALUE_ONE_TRUE = "1";
  private static final String PARAMVALUE_NOUGHT_FALSE = "0";

  private J9kwSolverConfig config;

  public J9kwSolverJaxRsJsonb() {
    // default constructor for services.
  }

  @Override
  public J9kwApiResponse<CaptchaRequest> requestNewCaptcha() {
    return requestNewCaptcha(CaptchaSelectorFactory.defaultSelector());
  }

  @Override
  public J9kwApiResponse<CaptchaRequest> requestNewCaptcha(final CaptchaSelector captchaSelector) {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(this.config);
    final WebTarget webTarget = webTargetUtils.createWebTarget(DEFAULT_PATH)
        .queryParam("withok", PARAMVALUE_ONE_TRUE)
        // returns the image / text / etc.
        .queryParam("filedata", PARAMVALUE_ONE_TRUE)
        .queryParam("action", "usercaptchanew");

    final Set<CaptchaType> wantedTypes = captchaSelector.getCaptchaTypes();
    // set "1" for each given type
    wantedTypes.iterator()
        .forEachRemaining(type -> webTarget.queryParam(type.getFieldName(), PARAMVALUE_ONE_TRUE));
    // set all other explicitely to 0.
    Arrays.stream(CaptchaType.values())
        .filter(type -> !wantedTypes.contains(type))
        .forEach(type -> webTarget.queryParam(type.getFieldName(), PARAMVALUE_NOUGHT_FALSE));

    switch (captchaSelector.getCaptchaSource()) {
      case ALL:
        webTarget.queryParam("selfsolve", PARAMVALUE_ONE_TRUE);
        break;
      case OTHERS:
        webTarget.queryParam("selfsolve", PARAMVALUE_NOUGHT_FALSE);
        break;
      case SELF_ONLY:
        webTarget.queryParam("selfonly", PARAMVALUE_ONE_TRUE);
        break;
      default:
        final IllegalStateException illegalStateException = new IllegalStateException("Cannot determine captcha source.");
        return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
            .exception(illegalStateException)
            .build();
    }

    LOG.log(Level.FINER, "URI: [" + webTarget.getUri() + "].");

    final Response response = webTarget.request().get();

    if (response.getStatus() != Response.Status.OK.getStatusCode()) {
      if (response.hasEntity()) {
        final String responseContent = response.readEntity(String.class);
        return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
            .responseCode(response.getStatus())
            .exception(new WebApplicationException(responseContent, response))
            .build();
      }
      return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
          .responseCode(response.getStatus())
          .build();
    }

    final JsonValue responseJson = response.readEntity(JsonValue.class);
    if (responseJson.getValueType() != JsonValue.ValueType.OBJECT) {
      return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
          .responseCode(response.getStatus())
          .build();
    }

    final JsonObject responseObject = responseJson.asJsonObject();
    if ("NO CAPTCHA".equals(responseObject.getString("message", ""))) {
      LOG.log(Level.FINE, "No captcha gotten: [" + responseObject + "].");
      return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
          .responseCode(response.getStatus())
          .build();
    }

    final AbstractCaptchaJsonResponse captchaJsonResponse =
        JSONB.fromJson(responseObject.toString(), AbstractCaptchaJsonResponse.class);
    final CaptchaRequest captchaRequest = JsonResponseMapper.fromResponse(captchaJsonResponse);

    return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
        .result(captchaRequest)
        .responseCode(response.getStatus())
        .build();
  }

  @Override
  public J9kwApiResponse<CaptchaReceiptResponse> confirmReception(final CaptchaId captchaId) {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(this.config);
    final WebTarget webTarget = webTargetUtils.createWebTarget(DEFAULT_PATH)
        // no captcha ID required. Dont ask why.
        .queryParam("action", "usercaptchanewok");

    final Response response = webTarget.request().get();

    if (response.getStatus() != Response.Status.OK.getStatusCode()) {
      if (response.hasEntity()) {
        final String responseContent = response.readEntity(String.class);
        return ImmutableJ9kwApiResponse.<CaptchaReceiptResponse>builder()
            .responseCode(response.getStatus())
            .exception(new WebApplicationException(responseContent, response))
            .build();
      }
      return ImmutableJ9kwApiResponse.<CaptchaReceiptResponse>builder()
          .responseCode(response.getStatus())
          .build();
    }

    final JsonValue responseJson = response.readEntity(JsonValue.class);

    final JsonObject responseObject = responseJson.asJsonObject();
    if (!"OK".equals(responseObject.getString("message", ""))) {
      return ImmutableJ9kwApiResponse.<CaptchaReceiptResponse>builder()
          .responseCode(response.getStatus())
          .build();
    }

    return ImmutableJ9kwApiResponse.<CaptchaReceiptResponse>builder()
        .responseCode(response.getStatus())
        .result(() -> "OK")
        .build();
  }

  @Override
  public J9kwApiResponse<CaptchaSkipResponse> skipCaptcha(final CaptchaId captchaId) {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(this.config);
    final WebTarget webTarget = webTargetUtils.createWebTarget(DEFAULT_PATH)
        // returns the image / text / etc.
        .queryParam("action", "usercaptchaskip")
        .queryParam("id", captchaId.getValue())
        .queryParam("extended", "1");

    final Response response = webTarget.request().get();

    return this.<CaptchaSkipResponse>responseIsInvalid(response).handle(
        (left, right) -> {
          if (right != null) {
            return right;
          }

          final CaptchaSkipJsonResponse skipResponse = JSONB.fromJson(left.toString(), CaptchaSkipJsonResponse.class);
          return ImmutableJ9kwApiResponse.<CaptchaSkipResponse>builder()
              .responseCode(response.getStatus())
              .result(skipResponse::getMessage)
              .build();
        }
    );
  }

  @Override
  public J9kwApiResponse<CaptchaSolutionResponse> solveCaptcha(final CaptchaSolution captchaSolution) {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(this.config);
    final WebTarget webTarget = webTargetUtils.createWebTarget(DEFAULT_PATH)
        // returns the image / text / etc.
        .queryParam("action", "usercaptchacorrect")
        .queryParam("id", captchaSolution.getCaptchaId().getValue())
        .queryParam("extended", "1")
        // captcha solution
        .queryParam("captcha", captchaSolution.getSolution());

    final Response response = webTarget.request().get();

    return this.<CaptchaSolutionResponse>responseIsInvalid(response).handle(
        (left, right) -> {
          if (right != null) {
            return right;
          }

          final CaptchaSolutionJsonResponse leftStr = JSONB.fromJson(left.toString(), CaptchaSolutionJsonResponse.class);
          return ImmutableJ9kwApiResponse.<CaptchaSolutionResponse>builder()
              .responseCode(response.getStatus())
              .result(JsonResponseMapper.fromResonse(leftStr))
              .build();
        }
    );
  }


  private <T> Either<JsonValue, J9kwApiResponse<T>> responseIsInvalid(final Response response) {
    if (response.getStatus() != Response.Status.OK.getStatusCode()) {
      if (response.hasEntity()) {
        final String responseContent = response.readEntity(String.class);
        return Either.mapRight(ImmutableJ9kwApiResponse.<T>builder()
            .responseCode(response.getStatus())
            .exception(new WebApplicationException(responseContent, response))
            .build());
      }
      return Either.mapRight(ImmutableJ9kwApiResponse.<T>builder()
          .responseCode(response.getStatus())
          .build());
    }

    final JsonValue jsonValue = response.readEntity(JsonValue.class);
    if (jsonValue.getValueType() != JsonValue.ValueType.OBJECT
        || !((JsonObject) jsonValue).getString("message", "").equals("OK")) {
      return Either.mapRight(ImmutableJ9kwApiResponse.<T>builder()
          .responseCode(response.getStatus())
          .exception(new IllegalStateException("message is not ok. Response was: [" + jsonValue.toString() + "]."))
          .build());
    }

    return Either.mapLeft(jsonValue);
  }

  @Override
  public J9kwServerStatus getServerStatus() {
    // TODO: implement
    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.J9kwSolverJaxRsJsonb::getServerStatus].");
  }

  @Override
  public UserBalance getBalance() {
    // TODO: implement
    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.J9kwSolverJaxRsJsonb::getBalance].");
  }


  @Override
  public void setConfig(final J9kwSolverConfig config) {
    this.config = requireNonNull(config, "config in J9kwSolverJaxRsJsonb.setConfig(J9kwSolverConfig).");
  }

  protected static <T> J9kwApiResponse<T> createWrappedResponse(final Response response, final T content) {
    return ImmutableJ9kwApiResponse.<T>builder()
        .responseCode(response.getStatus())
        .result(content)
        .build();
  }
}
