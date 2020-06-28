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

public enum CaptchaType {
  /**
   * Enter a text displayed as an image). Also commonly referred to as image captchas.
   */
  IMAGE("text"),

  /**
   * Text questions like &quot;please solve 1+2&quot;
   */
  TEXTONLY("textonly"),

  /**
   * E.g. recaptcha.
   */
  INTERACTIVE("interactive"),

  MATH("math"),

  AUDIO("audio"),

  /**
   * Captchas which need to be rotated.
   */
  ROTATE("rotate"),

  /**
   * Request captchas which can be solved with a single click.
   */
  MOUSE("mouse"),

  /**
   * Also requests mouse captchas to be solved with one or multiple clicks. Also commonly referred to as mouse or click captchas.
   */
  MULTIMOUSE("multimouse"),

  /**
   * Requests puzzle captchas from the system.
   *
   * <p>A larger image to be completed by dragging smaller images using the mouse.<br>
   * The appropriate coordinates will be transmitted as the result.<br></p>
   */
  PUZZLE("puzzle"),

  /**
   * Also requests captchas where the solution is to be reviewed by a human.
   */
  CONFIRM("confirm");

  private final String parameterName;

  CaptchaType(final String parameterName) {
    this.parameterName = parameterName;
  }

  public String getFieldName() {
    return this.parameterName;
  }

  /**
   * Default capture types are {@code TEXT, TEXTONLY, CONFIRM}.
   *
   * @return the default capture types.
   */
  public static CaptchaType[] defaultTypes() {
    return new CaptchaType[] {
        IMAGE, TEXTONLY, CONFIRM
    };
  }
}
