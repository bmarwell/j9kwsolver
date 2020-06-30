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
import io.github.bmhm.j9kwsolver.api.request.J9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaId;
import io.github.bmhm.j9kwsolver.api.value.CaptchaReceiptResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.lang.Either;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.WebTargetUtils;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class ConfirmationProcessor extends AbstractRequestProcessor<CaptchaReceiptResponse> {

  private final CaptchaId captchaId;

  public ConfirmationProcessor(final J9kwSolverConfig config, final CaptchaId captchaId) {
    super(config);
    this.captchaId = requireNonNull(captchaId);
  }

  @Override
  public void process() {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(getConfig());
    final WebTarget webTarget = webTargetUtils.createWebTarget(getDefaultPath())
        // no captcha ID required. Dont ask why.
        .queryParam("action", "usercaptchanewok");

    final Response response = webTarget.request().get();

    final Either<JsonValue, J9kwApiResponse<CaptchaReceiptResponse>> eitherBodyOrFailResponse = eitherJsonOrError(response);

    eitherBodyOrFailResponse.consume(
        left -> handleCaptchaReceiptResponse(response, left),
        right -> {
          right.getException().ifPresent(this::setException);
          right.getResponseCode().ifPresent(this::setResponseCode);
        }
    );
  }

  private void handleCaptchaReceiptResponse(final Response response,
                                            final JsonValue left) {
    final JsonObject responseObject = left.asJsonObject();
    if (!"OK".equals(responseObject.getString("message", ""))) {
      this.setResponseCode(response.getStatus());
      return;
    }

    this.setResponseCode(response.getStatus());
    this.setResult(() -> "OK");
  }
}
