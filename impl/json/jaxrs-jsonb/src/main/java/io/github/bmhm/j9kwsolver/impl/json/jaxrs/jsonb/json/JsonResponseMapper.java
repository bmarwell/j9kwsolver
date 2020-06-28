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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json;

import io.github.bmhm.j9kwsolver.api.value.CaptchaRequest;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolutionResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaType;
import io.github.bmhm.j9kwsolver.api.value.ImmutableCaptchaRequestImage;
import io.github.bmhm.j9kwsolver.api.value.ImmutableCaptchaSolutionResponse;

public final class JsonResponseMapper {

  private JsonResponseMapper() {
    // util
  }

  public static CaptchaRequest fromResponse(final AbstractCaptchaJsonResponse jsonResponse) {
    final CaptchaType captchaType = getCaptchaType(jsonResponse);
    switch (captchaType) {
      case IMAGE:
        final CaptchaJsonResponseImage imgResponse = (CaptchaJsonResponseImage) jsonResponse;
        return ImmutableCaptchaRequestImage.builder()
            .captchaId(imgResponse.getCaptchaId())
            .captchaType(captchaType)
            .timeoutSeconds(imgResponse.getTimeoutSeconds())
            .image(imgResponse.getImage())
            .build();
      default:
        throw new UnsupportedOperationException("Not implemented: [" + captchaType + "].");
    }
  }

  public static CaptchaType getCaptchaType(final AbstractCaptchaJsonResponse jsonResponse) {
    if (jsonResponse.isImage()) {
      return CaptchaType.IMAGE;
    }

    if (jsonResponse.isTextOnly()) {
      return CaptchaType.TEXTONLY;
    }

    throw new IllegalArgumentException("Unable to find correct captcha type");
  }

  public static CaptchaSolutionResponse fromResonse(final CaptchaSolutionJsonResponse captchaSolution) {
    return ImmutableCaptchaSolutionResponse.builder()
        .message(captchaSolution.getMessage())
        .captchaKey(captchaSolution.getCaptchaSolution())
        .newCredits(captchaSolution.getNewCredits())
        .build();
  }
}
