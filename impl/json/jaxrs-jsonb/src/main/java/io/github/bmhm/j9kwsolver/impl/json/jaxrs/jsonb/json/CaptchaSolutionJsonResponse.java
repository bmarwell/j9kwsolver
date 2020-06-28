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

import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;

public class CaptchaSolutionJsonResponse {

  @JsonbProperty("newcredits")
  private long newCredits;

  @JsonbProperty("captchakey")
  private String captchaSolution;

  @JsonbProperty("message")
  private String message;

  public long getNewCredits() {
    return this.newCredits;
  }

  public void setNewCredits(final long newCredits) {
    this.newCredits = newCredits;
  }

  public String getCaptchaSolution() {
    return this.captchaSolution;
  }

  public void setCaptchaSolution(final String captchaSolution) {
    this.captchaSolution = captchaSolution;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", CaptchaSolutionJsonResponse.class.getSimpleName() + "[", "]")
        .add("newCredits=" + this.newCredits)
        .add("captchaId=" + this.captchaSolution)
        .add("message='" + this.message + "'")
        .toString();
  }
}
