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

package io.github.bmhm.j9kwsolver.api.value;

public interface CaptchaSolution {

  /**
   * The ID of the solved captcha.
   *
   * @return the ID of the solved captcha.
   */
  CaptchaId getCaptchaId();

  /**
   * Returns {@code true} if this captcha was only to be confirmed.
   *
   * @return {@code true} if this captcha was only to be confirmed.
   */
  boolean isConfirmCaptcha();

  /**
   * The answer for the captcha with the ID {@link #getCaptchaId()}.
   *
   * @return the answer for the captcha with the ID {@link #getCaptchaId()}.
   */
  String getAnswer();
}
