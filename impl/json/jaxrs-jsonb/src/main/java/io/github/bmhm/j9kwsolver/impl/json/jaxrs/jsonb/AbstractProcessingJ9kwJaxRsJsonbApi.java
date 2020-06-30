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

import io.github.bmhm.j9kwsolver.api.J9kwSolverApi;
import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;
import io.github.bmhm.j9kwsolver.api.value.CaptchaId;
import io.github.bmhm.j9kwsolver.api.value.CaptchaReceiptResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaRequest;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSelector;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSkipResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolution;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolutionResponse;
import io.github.bmhm.j9kwsolver.api.value.J9kwServerStatus;
import io.github.bmhm.j9kwsolver.api.value.UserBalance;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.AbstractProcessorFactory;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.J9kwResponseProcessor;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.impl.JaxRsProcessorFactory;

abstract class AbstractProcessingJ9kwJaxRsJsonbApi implements J9kwSolverApi {

  private J9kwSolverConfig config;

  protected AbstractProcessorFactory getProcessorFactory(final J9kwSolverConfig config) {
    // todo: replace with serviceloader
    return new JaxRsProcessorFactory(config);
  }

  protected J9kwResponseProcessor<CaptchaRequest> getRequestProcessor(final CaptchaSelector captchaSelector) {
    return getProcessorFactory(getConfig()).getRequestProcessor(captchaSelector);
  }

  protected J9kwResponseProcessor<CaptchaReceiptResponse> getConfirmationProcessor(final CaptchaId captchaId) {
    return getProcessorFactory(getConfig()).getConfirmationProcessor(captchaId);
  }

  protected J9kwResponseProcessor<CaptchaSkipResponse> getSkipProcessor(final CaptchaId captchaId) {
    return getProcessorFactory(getConfig()).getSkipProcessor(captchaId);
  }

  protected J9kwResponseProcessor<CaptchaSolutionResponse> getSolveProcessor(final CaptchaSolution captchaSolution) {
    return getProcessorFactory(getConfig()).getSolveProcessor(captchaSolution);
  }

  protected J9kwResponseProcessor<J9kwServerStatus> getServerStatusProcessor() {
    return getProcessorFactory(getConfig()).getServerStatusProcessor();
  }

  protected J9kwResponseProcessor<UserBalance> getBalanceProcessor() {
    return getProcessorFactory(getConfig()).getBalanceProcessor();
  }

  protected J9kwSolverConfig getConfig() {
    return this.config;
  }

  @Override
  public void setConfig(final J9kwSolverConfig config) {
    this.config = requireNonNull(config, "config in J9kwSolverJaxRsJsonb.setConfig(J9kwSolverConfig).");
  }

}
