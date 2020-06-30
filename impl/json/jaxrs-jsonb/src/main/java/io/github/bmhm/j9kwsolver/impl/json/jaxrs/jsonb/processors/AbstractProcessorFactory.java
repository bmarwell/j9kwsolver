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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors;

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

import java.util.StringJoiner;

public abstract class AbstractProcessorFactory {

  private final J9kwSolverConfig config;

  public AbstractProcessorFactory(final J9kwSolverConfig config) {
    this.config = config;
  }

  public abstract J9kwResponseProcessor<CaptchaRequest> getRequestProcessor(CaptchaSelector captchaSelector);

  public abstract J9kwResponseProcessor<CaptchaReceiptResponse> getConfirmationProcessor(CaptchaId captchaId);

  public abstract J9kwResponseProcessor<CaptchaSkipResponse> getSkipProcessor(CaptchaId captchaId);

  public abstract J9kwResponseProcessor<CaptchaSolutionResponse> getSolveProcessor(CaptchaSolution captchaSolution);

  public abstract J9kwResponseProcessor<J9kwServerStatus> getServerStatusProcessor();

  public abstract J9kwResponseProcessor<UserBalance> getBalanceProcessor();

  protected J9kwSolverConfig getConfig() {
    return this.config;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AbstractProcessorFactory.class.getSimpleName() + "[", "]")
        .add("config=" + this.config)
        .toString();
  }
}
