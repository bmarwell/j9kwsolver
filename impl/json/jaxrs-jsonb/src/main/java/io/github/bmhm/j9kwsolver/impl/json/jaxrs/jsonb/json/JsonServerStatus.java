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

public class JsonServerStatus implements J9kwResponse {

  @JsonbProperty("message")
  private String message;

  /**
   * Number of users currently solving captchas.
   */
  @JsonbProperty("worker")
  private long worker;

  /**
   * Number of captchas assigned to workers and thus currently being solved.
   */
  @JsonbProperty("inwork")
  private long inWork;

  /**
   * Number of users online. Possibly the same as {@link #worker}.
   */
  @JsonbProperty("useronline")
  private long userOnline;

  /**
   * Number of captchas waiting to be solved.
   */
  @JsonbProperty("queue")
  private long queue;

  public JsonServerStatus() {
    // jsonb constructor
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public long getWorker() {
    return this.worker;
  }

  public void setWorker(final long worker) {
    this.worker = worker;
  }

  public long getInWork() {
    return this.inWork;
  }

  public void setInWork(final long inWork) {
    this.inWork = inWork;
  }

  public long getUserOnline() {
    return this.userOnline;
  }

  public void setUserOnline(final long userOnline) {
    this.userOnline = userOnline;
  }

  public long getQueue() {
    return this.queue;
  }

  public void setQueue(final long queue) {
    this.queue = queue;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", JsonServerStatus.class.getSimpleName() + "[", "]")
        .add("super=" + super.toString())
        .add("message='" + this.message + "'")
        .add("worker=" + this.worker)
        .add("inWork=" + this.inWork)
        .add("userOnline=" + this.userOnline)
        .add("queue=" + this.queue)
        .toString();
  }
}
