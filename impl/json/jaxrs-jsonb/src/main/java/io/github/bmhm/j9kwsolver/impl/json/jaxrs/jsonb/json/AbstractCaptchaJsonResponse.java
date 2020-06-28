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

import io.github.bmhm.j9kwsolver.api.value.CaptchaId;
import io.github.bmhm.j9kwsolver.api.value.CaptchaType;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.CaptchaIdAdapter;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.CaptchaJsonResponseTypeAdapter;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.IntBooleanAdapter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.json.bind.annotation.JsonbTypeDeserializer;

@JsonbTypeDeserializer(CaptchaJsonResponseTypeAdapter.class)
public abstract class AbstractCaptchaJsonResponse implements J9kwResponse {

  @JsonbProperty("message")
  protected String message;

  @JsonbProperty("timeout")
  private long timeoutSeconds;

  @JsonbProperty("captchaid")
  @JsonbTypeAdapter(CaptchaIdAdapter.class)
  private CaptchaId captchaId;

  @JsonbProperty("text")
  @JsonbTypeAdapter(IntBooleanAdapter.class)
  private Boolean isImage;

  @JsonbProperty("textOnly")
  @JsonbTypeAdapter(IntBooleanAdapter.class)
  private Boolean isTextOnly;

  public AbstractCaptchaJsonResponse() {
    // jsonb
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public long getTimeoutSeconds() {
    return this.timeoutSeconds;
  }

  public void setTimeoutSeconds(final long timeoutSeconds) {
    this.timeoutSeconds = timeoutSeconds;
  }

  public CaptchaId getCaptchaId() {
    return this.captchaId;
  }

  public void setCaptchaId(final CaptchaId captchaId) {
    this.captchaId = captchaId;
  }

  public Boolean isImage() {
    return this.isImage;
  }

  public void setIsImage(final Boolean isImage) {
    this.isImage = isImage;
  }

  public Boolean isTextOnly() {
    return this.isTextOnly;
  }

  public void setIsTextOnly(final Boolean isTextOnly) {
    this.isTextOnly = isTextOnly;
  }

  public CaptchaType getCaptchaType() {
    if (isImage()) {
      return CaptchaType.IMAGE;
    }

    if (isTextOnly()) {
      return CaptchaType.TEXTONLY;
    }

    throw new IllegalStateException("Unknown captcha type.");
  }

}
