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

import io.github.bmhm.j9kwsolver.api.CaptchaSelectorFactory;
import io.github.bmhm.j9kwsolver.api.J9kwSolverApi;
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
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.J9kwResponseProcessor;

public class JaxRsJsonbJ9kwSolverApi extends AbstractProcessingJ9kwJaxRsJsonbApi implements J9kwSolverApi {

  public JaxRsJsonbJ9kwSolverApi() {
    // default constructor for services.
  }

  @Override
  public J9kwApiResponse<CaptchaRequest> requestNewCaptcha() {
    return requestNewCaptcha(CaptchaSelectorFactory.defaultSelector());
  }

  @Override
  public J9kwApiResponse<CaptchaRequest> requestNewCaptcha(final CaptchaSelector captchaSelector) {
    final J9kwResponseProcessor<CaptchaRequest> requestProcessor = getRequestProcessor(captchaSelector);
    requestProcessor.process();

    return requestProcessor.getApiResponse();
  }

  @Override
  public J9kwApiResponse<CaptchaReceiptResponse> confirmReception(final CaptchaId captchaId) {
    final J9kwResponseProcessor<CaptchaReceiptResponse> confirmationProcessor = getConfirmationProcessor(captchaId);
    confirmationProcessor.process();

    return confirmationProcessor.getApiResponse();
  }

  @Override
  public J9kwApiResponse<CaptchaSkipResponse> skipCaptcha(final CaptchaId captchaId) {
    final J9kwResponseProcessor<CaptchaSkipResponse> skipProcessor = getSkipProcessor(captchaId);
    skipProcessor.process();

    return skipProcessor.getApiResponse();
  }

  @Override
  public J9kwApiResponse<CaptchaSolutionResponse> solveCaptcha(final CaptchaSolution captchaSolution) {
    final J9kwResponseProcessor<CaptchaSolutionResponse> solveProcessor = getSolveProcessor(captchaSolution);
    solveProcessor.process();

    return solveProcessor.getApiResponse();
  }

  @Override
  public J9kwApiResponse<J9kwServerStatus> getServerStatus() {
    final J9kwResponseProcessor<J9kwServerStatus> statusProcessor = getServerStatusProcessor();
    statusProcessor.process();

    return statusProcessor.getApiResponse();
  }

  @Override
  public J9kwApiResponse<UserBalance> getBalance() {
    final J9kwResponseProcessor<UserBalance> balanceProcessor = getBalanceProcessor();
    balanceProcessor.process();

    return balanceProcessor.getApiResponse();
  }

}
