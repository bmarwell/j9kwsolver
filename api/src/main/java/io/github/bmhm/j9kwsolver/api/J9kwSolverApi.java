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

package io.github.bmhm.j9kwsolver.api;

import io.github.bmhm.j9kwsolver.api.request.J9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaId;
import io.github.bmhm.j9kwsolver.api.value.CaptchaReceiptResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaRequest;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSelector;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSkipResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolution;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolutionResponse;
import io.github.bmhm.j9kwsolver.api.value.J9kwServerStatus;
import io.github.bmhm.j9kwsolver.api.value.UserBalance;

public interface J9kwSolverApi {

  /**
   * Tries to receive a captcha request for solving.
   *
   * <p><strong>Blocking operation.</strong><br>
   * It is recommended to wrap it into a {@link java.util.concurrent.CompletableFuture}.<br></p>
   *
   * @return a captcha response which contains the image, and other solving data.
   */
  default J9kwApiResponse<CaptchaRequest> requestNewCaptcha() {
    return requestNewCaptcha(CaptchaSelectorFactory.defaultSelector());
  }

  /**
   * Tries to receive a captcha request for solving.
   *
   * <p><strong>Blocking operation.</strong><br></p>
   *
   * <p>This api call can be configured for specific captcha types.<br></p>
   *
   * @param captchaSelector the captcha selector.
   * @return a captcha request
   */
  J9kwApiResponse<CaptchaRequest> requestNewCaptcha(CaptchaSelector captchaSelector);

  J9kwApiResponse<CaptchaReceiptResponse> confirmReception(CaptchaId captchaId);

  J9kwApiResponse<CaptchaSkipResponse> skipCaptcha(CaptchaId captchaId);

  /**
   * Submits a solution request which will grant you points if the solution was accepted.
   *
   * @param captchaSolution
   *     the solution created by a user.
   * @return a reponse from the server whether solution was okay and the new credit balance of the user.
   */
  J9kwApiResponse<CaptchaSolutionResponse> solveCaptcha(CaptchaSolution captchaSolution);

  J9kwApiResponse<J9kwServerStatus> getServerStatus();

  J9kwApiResponse<UserBalance> getBalance();

  /**
   * Sets a config to be used.
   *
   * @param config
   */
  void setConfig(J9kwSolverConfig config);

}
