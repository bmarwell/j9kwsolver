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

public class JsonUserBalance implements J9kwResponse {

  @JsonbProperty("message")
  private String message;

  @JsonbProperty("credits")
  private long credits;

  public JsonUserBalance() {
    // jsonb
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public long getCredits() {
    return this.credits;
  }

  public void setCredits(final long credits) {
    this.credits = credits;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", JsonUserBalance.class.getSimpleName() + "[", "]")
        .add("super=" + super.toString())
        .add("message='" + this.message + "'")
        .add("credits=" + this.credits)
        .toString();
  }
}