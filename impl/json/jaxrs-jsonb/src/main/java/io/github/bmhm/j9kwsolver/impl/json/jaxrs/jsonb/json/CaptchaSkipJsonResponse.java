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

public class CaptchaSkipJsonResponse implements J9kwResponse {

  @JsonbProperty("message")
  private String message;

  public CaptchaSkipJsonResponse() {
    // default constructor for jsonb
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", CaptchaSkipJsonResponse.class.getSimpleName() + "[", "]")
        .add("super=" + super.toString())
        .add("message='" + this.message + "'")
        .toString();
  }
}
