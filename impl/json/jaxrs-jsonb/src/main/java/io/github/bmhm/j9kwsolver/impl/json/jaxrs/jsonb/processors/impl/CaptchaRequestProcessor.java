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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.impl;

import static java.util.Objects.requireNonNull;

import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;
import io.github.bmhm.j9kwsolver.api.request.ImmutableJ9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.request.J9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaRequest;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSelector;
import io.github.bmhm.j9kwsolver.api.value.CaptchaType;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.AbstractCaptchaJsonResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.JsonResponseMapper;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.lang.Either;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.WebTargetUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonValue;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class CaptchaRequestProcessor extends AbstractRequestProcessor<CaptchaRequest> {

  private static final Logger LOG = Logger.getLogger(CaptchaRequestProcessor.class.getCanonicalName());

  private static final String PARAMVALUE_ONE_TRUE = "1";
  private static final String PARAMVALUE_NOUGHT_FALSE = "0";

  private final CaptchaSelector selector;

  public CaptchaRequestProcessor(final J9kwSolverConfig config, final CaptchaSelector captchaSelector) {
    super(config);
    this.selector = requireNonNull(captchaSelector);
  }

  @Override
  public void process() {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(getConfig());
    final WebTarget webTarget = webTargetUtils.createWebTarget(getDefaultPath())
        .queryParam("withok", PARAMVALUE_ONE_TRUE)
        // returns the image / text / etc.
        .queryParam("filedata", PARAMVALUE_ONE_TRUE)
        .queryParam("action", "usercaptchanew");

    final Set<CaptchaType> wantedTypes = this.selector.getCaptchaTypes();
    // set "1" for each given type
    wantedTypes.iterator()
        .forEachRemaining(type -> webTarget.queryParam(type.getFieldName(), PARAMVALUE_ONE_TRUE));
    // set all other explicitely to 0.
    Arrays.stream(CaptchaType.values())
        .filter(type -> !wantedTypes.contains(type))
        .forEach(type -> webTarget.queryParam(type.getFieldName(), PARAMVALUE_NOUGHT_FALSE));

    switch (this.selector.getCaptchaSource()) {
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
        this.setException(illegalStateException);
        return;
    }

    LOG.log(Level.FINER, "URI: [" + webTarget.getUri() + "].");

    try {
      final Response response = webTarget.request().get();

      final Either<JsonValue, J9kwApiResponse<CaptchaRequest>> eitherJsonOrError = eitherJsonOrError(response);

      eitherJsonOrError.consume(
          left -> {
            final J9kwApiResponse<CaptchaRequest> captchaRequestJ9kwApiResponse = tryReadCaptchaResponse(response, left);
            captchaRequestJ9kwApiResponse.getResponseCode().ifPresent(this::setResponseCode);
            captchaRequestJ9kwApiResponse.getResult().ifPresent(this::setResult);
          },
          right -> {
            setException(right.getException().orElse(null));
            right.getResponseCode().ifPresent(this::setResponseCode);
          }
      );
    } catch (final RuntimeException rtEx) {
      setException(rtEx);
    }
  }

  private J9kwApiResponse<CaptchaRequest> tryReadCaptchaResponse(final Response response, final JsonValue jsonValue) {
    if (jsonValue.asJsonObject().containsKey("message")) {
      return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
          .responseCode(Response.Status.NOT_FOUND.getStatusCode())
          .build();
    }

    try {
      final AbstractCaptchaJsonResponse captchaJsonResponse = getJsonB().fromJson(jsonValue.toString(), AbstractCaptchaJsonResponse.class);
      final CaptchaRequest captchaRequest = JsonResponseMapper.fromResponse(captchaJsonResponse);

      return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
          .result(captchaRequest)
          .responseCode(response.getStatus())
          .build();
    } catch (final WebApplicationException webApplicationException) {
      return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
          .responseCode(webApplicationException.getResponse().getStatus())
          .exception(webApplicationException)
          .build();
    } catch (final ProcessingException processingException) {
      return ImmutableJ9kwApiResponse.<CaptchaRequest>builder()
          .exception(processingException)
          .build();
    }
  }
}
