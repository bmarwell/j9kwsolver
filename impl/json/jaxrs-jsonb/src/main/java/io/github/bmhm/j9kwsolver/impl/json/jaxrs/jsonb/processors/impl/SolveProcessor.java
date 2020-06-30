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
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolution;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolutionResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.CaptchaSolutionJsonResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.JsonResponseMapper;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.lang.Either;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.WebTargetUtils;

import javax.json.JsonValue;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class SolveProcessor extends AbstractRequestProcessor<CaptchaSolutionResponse> {

  private final CaptchaSolution captchaSolution;

  public SolveProcessor(final J9kwSolverConfig config, final CaptchaSolution captchaSolution) {
    super(config);
    this.captchaSolution = requireNonNull(captchaSolution);
  }

  @Override
  public void process() {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(getConfig());
    final WebTarget webTarget = webTargetUtils.createWebTarget(getDefaultPath())
        // returns the image / text / etc.
        .queryParam("action", "usercaptchacorrect")
        .queryParam("id", this.captchaSolution.getCaptchaId().getValue())
        .queryParam("extended", "1")
        // captcha solution
        .queryParam("captcha", this.captchaSolution.getSolution());

    try {
      final Response response = webTarget.request().get();

      final Either<JsonValue, J9kwApiResponse<CaptchaSolutionResponse>> eitherJsonOrError = this.eitherJsonOrError(response);

      eitherJsonOrError.consume(
          left -> {
            final CaptchaSolutionJsonResponse jsonSolutionResponse =
                getJsonB().fromJson(left.toString(), CaptchaSolutionJsonResponse.class);

            if (!jsonSolutionResponse.getMessage().equals("OK")) {
              throw new WebApplicationException(left.toString(), Response.Status.BAD_REQUEST);
            }

            setResponseCode(response.getStatus());
            setResult(JsonResponseMapper.fromResonse(jsonSolutionResponse));
          },
          right -> {
            right.getException().ifPresent(this::setException);
            right.getResponseCode().ifPresent(this::setResponseCode);
          }
      );
    } catch (final WebApplicationException webApplicationException) {
      ImmutableJ9kwApiResponse.<CaptchaSolutionResponse>builder()
          .responseCode(webApplicationException.getResponse().getStatus())
          .exception(webApplicationException)
          .build();
    } catch (final ProcessingException processingException) {
      ImmutableJ9kwApiResponse.<CaptchaSolutionResponse>builder()
          .exception(processingException)
          .build();
    }
  }
}
