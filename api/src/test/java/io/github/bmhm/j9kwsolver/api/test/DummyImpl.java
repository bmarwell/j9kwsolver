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

package io.github.bmhm.j9kwsolver.api.test;

import io.github.bmhm.j9kwsolver.api.J9kwSolverApi;
import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;
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

public class DummyImpl implements J9kwSolverApi {
  public DummyImpl() {
    // nothing to see here!
  }

  @Override
  public J9kwApiResponse<CaptchaRequest> requestNewCaptcha(final CaptchaSelector captchaSelector) {
    throw new UnsupportedOperationException("not yet implemented: [io.github.bmhm.j9kwsolver.api.test.DummyImpl::requestNewCaptcha].");

  }

  @Override
  public CaptchaReceiptResponse confirmReception(final CaptchaId captchaId) {
    throw new UnsupportedOperationException("not yet implemented: [io.github.bmhm.j9kwsolver.api.test.DummyImpl::confirmReception].");

  }

  @Override
  public CaptchaSkipResponse skipCaptcha(final CaptchaId captchaId) {
    throw new UnsupportedOperationException("not yet implemented: [io.github.bmhm.j9kwsolver.api.test.DummyImpl::skipCaptcha].");

  }

  @Override
  public CaptchaSolutionResponse solveCaptcha(final CaptchaSolution captchaSolution) {
    throw new UnsupportedOperationException("not yet implemented: [io.github.bmhm.j9kwsolver.api.test.DummyImpl::solveCaptcha].");

  }

  @Override
  public J9kwServerStatus getServerStatus() {
    throw new UnsupportedOperationException("not yet implemented: [io.github.bmhm.j9kwsolver.api.test.DummyImpl::getServerStatus].");

  }

  @Override
  public UserBalance getBalance() {
    throw new UnsupportedOperationException("not yet implemented: [io.github.bmhm.j9kwsolver.api.test.DummyImpl::getBalance].");

  }

  @Override
  public void setConfig(final J9kwSolverConfig config) {
    // nothing to see here.
  }
}
