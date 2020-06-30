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

import java.util.StringJoiner;

public class JaxRsProcessorFactory extends AbstractProcessorFactory {

  public JaxRsProcessorFactory(final J9kwSolverConfig config) {
    super(config);
  }

  @Override
  public J9kwResponseProcessor<CaptchaRequest> getRequestProcessor(final CaptchaSelector captchaSelector) {
    return new CaptchaRequestProcessor(getConfig(), captchaSelector);
  }

  @Override
  public J9kwResponseProcessor<CaptchaReceiptResponse> getConfirmationProcessor(final CaptchaId captchaId) {
    return new ConfirmationProcessor(getConfig(), captchaId);
  }

  @Override
  public J9kwResponseProcessor<CaptchaSkipResponse> getSkipProcessor(final CaptchaId captchaId) {
    return new SkipProcessor(getConfig(), captchaId);
  }

  @Override
  public J9kwResponseProcessor<CaptchaSolutionResponse> getSolveProcessor(final CaptchaSolution captchaSolution) {
    return new SolveProcessor(getConfig(), captchaSolution);
  }

  @Override
  public J9kwResponseProcessor<J9kwServerStatus> getServerStatusProcessor() {
    return new ServerStatusProcessor(getConfig());
  }

  @Override
  public J9kwResponseProcessor<UserBalance> getBalanceProcessor() {
    return new UserBalanceProcessor(getConfig());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", JaxRsProcessorFactory.class.getSimpleName() + "[", "]")
        .add("super=" + super.toString())
        .toString();
  }
}
