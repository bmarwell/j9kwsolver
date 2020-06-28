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
   * <p>Examples for different types of captchas:</p>
   * <ul>
   *    <li>Text: 9xj3o1</li>
   *  <li>Mouse: 324x184 (coordinates)</li>
   *  <li>Multimouse: 68x149;81x192</li>
   *  <li>Puzzle: 301.05.165.11</li>
   *  <li>Rotate: 40 für Plus oder -40 für Minus</li>
   *  <li>Interaktiv: the field contents of the field {@code g-recaptcha-response}.<br>
   *      Example for reCaptcha v2:<br>
   *      {@code 03AHJ_VutkQJRDWMowUV1T1JAKCUD8IJhktzcRRm4HbOQ50KDGdvBLitIh9yDO-
   *             mTN3OyKadUTk1DG8GxsuCywdJfC8Za3lfvjTCedAmh2-yvr4oFc6sIOIEsNWbPi
   *             yyPKvQiUT4c_FH3oIlqfBOAbCVnlpDHmUVZkndwk2QKhy8lhndh6V7chyACz2iR
   *             UmgKOB-TY-JPuBMh6VuS35Snbcv8lhx5Qhue82Y8GwWMGJ5HHYN8WZuoZbrT_Fr
   *             ZBg9VsvaTXJ82GyHln7TJ688DBjpR0XVWaDb7Grj5R4Crz1savh4Oe8TCq1QiCu
   *             XQn7O0KyMQa9jGWBvkH46edLavWp6RzRbqH50R3OIrZjdVWuPhS8KvnXwZ6B7jw
   *             SdsPhLlDr9mmF5bX7Z4GAP-KjZBE-ng_sPSgxQ8NUDZBN6zIdq1AInoa_CEgC7L
   *             q-iIfJ4kJuFW7AKpbuADcbHOZ0dpv8MCF6IolPKM3CAX5z-pX_u9gsF3gWjxC-f
   *             7fF5jPutWf0cAe4YqEaO45oii80mnc5OV8e7ejHh2SdEZpxX03gElNs9fgNXCK0
   *             Yh672bnaWNxrlBSoeD_UZOFHKuvKP8vTGDMp6e3Q5IuUo7GDEXN6vbBfg1zj1Th
   *             CxTJkTQzM9pVaBUbBfs7Uijiuo-9-0PPNOnjipVOTFAldbOSM8U05j-s1aK6vAS
   *             spQDgMonPmwfumY5hbt_GT3FMQN3K2hkRY5tu2T1pwLfWZjAOEbsRq90EWAM7PR
   *             Rx2T_M-jWiKSSptOUM5q-eUns87QHY6I90Ix27_3m_nDbYV36BuIlV5aVosdawt
   *             GAtTQ5UAUklBs-fwTC4FX7HCxnXoNjkdCSYNkTdynLbkTP7uPmGHaD94lKnEfZc
   *             SIC0tbumDW9_9qYfPHibGLT6VfFKa9b8edSjHHGer1drCWfsoDEPKT86BfMMFkR
   *             4tNTzUhdt9VXpUoDJZNNaqB53rDeWEKIB7VYIp0w_k5zqdCj07WKna4dAmi1EEB
   *             NdyfxCD43Xy_TDLHIVLtwpK0VxIw3Qrm46TA8Td2twUZwj0tAtvA}</li>
   *  <li>Confirm: "yes" für Richtig, "no" für Falsch.</li>
   * </ul>
   *
   * @return the answer for the captcha with the ID {@link #getCaptchaId()}.
   */
  String getSolution();
}
