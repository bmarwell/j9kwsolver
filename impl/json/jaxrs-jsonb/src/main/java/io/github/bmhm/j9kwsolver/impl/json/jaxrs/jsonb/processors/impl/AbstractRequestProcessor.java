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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.impl;

import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;
import io.github.bmhm.j9kwsolver.api.request.ImmutableJ9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.request.J9kwApiResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.lang.Either;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.J9kwResponseProcessor;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.JsonbProvider;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.StringJoiner;
import java.util.function.Predicate;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

abstract class AbstractRequestProcessor<T> implements J9kwResponseProcessor<T> {

  private static final String DEFAULT_PATH = "index.cgi";

  private static final Jsonb JSONB = JsonbProvider.getInstance();

  private final J9kwSolverConfig config;
  private Integer responseCode;
  private Throwable throwable;

  private T result;

  public AbstractRequestProcessor(final J9kwSolverConfig config) {
    this.config = config;
  }

  public J9kwSolverConfig getConfig() {
    return this.config;
  }

  @Override
  public OptionalInt getResponseCode() {
    if (this.responseCode == null) {
      return OptionalInt.empty();
    }

    return OptionalInt.of(this.responseCode);
  }

  @Override
  public Optional<Throwable> getException() {
    return Optional.ofNullable(this.throwable);
  }

  protected void setResponseCode(final int responseCode) {
    this.responseCode = responseCode;
  }

  protected void setException(final Throwable throwable) {
    this.throwable = throwable;
  }

  protected String getDefaultPath() {
    return DEFAULT_PATH;
  }

  @Override
  public Optional<T> getResult() {
    return Optional.ofNullable(this.result);
  }

  protected void setResult(final T result) {
    this.result = result;
  }

  protected <T> Either<JsonValue, J9kwApiResponse<T>> eitherJsonOrError(final Response response) {
    final Predicate<JsonValue>[] predicates = new Predicate[0];

    return eitherJsonOrError(response, predicates);
  }

  protected <T> Either<JsonValue, J9kwApiResponse<T>> eitherJsonOrError(final Response response, final Predicate<JsonValue>... predicates) {
    if (response.getStatus() != Response.Status.OK.getStatusCode()) {
      if (response.hasEntity()) {
        final String responseContent = response.readEntity(String.class);
        return Either.mapRight(ImmutableJ9kwApiResponse.<T>builder()
            .responseCode(response.getStatus())
            .exception(new WebApplicationException(responseContent, response))
            .build());
      }
      return Either.mapRight(ImmutableJ9kwApiResponse.<T>builder()
          .responseCode(response.getStatus())
          .build());
    }

    final JsonValue jsonValue = response.readEntity(JsonValue.class);

    final boolean allMatch = Arrays.stream(predicates)
        .allMatch(pred -> pred.test(jsonValue));

    if (!allMatch) {
      return Either.mapRight(ImmutableJ9kwApiResponse.<T>builder()
          .responseCode(response.getStatus())
          .exception(new IllegalStateException("message is not ok. Response was: [" + jsonValue.toString() + "]."))
          .build());
    }

    return Either.mapLeft(jsonValue);
  }

  protected Jsonb getJsonB() {
    return JSONB;
  }

  @Override
  public J9kwApiResponse<T> getApiResponse() {
    if (getException().isPresent()
        || getResponseCode().orElse(-1) != Response.Status.OK.getStatusCode()) {
      return ImmutableJ9kwApiResponse.<T>builder()
          .exception(getException())
          .responseCode(getResponseCode())
          .build();
    }

    return ImmutableJ9kwApiResponse.<T>builder()
        .result(getResult().orElseThrow())
        .responseCode(this.responseCode)
        .build();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AbstractRequestProcessor.class.getSimpleName() + "[", "]")
        .add("config=" + this.config)
        .add("responseCode=" + this.responseCode)
        .add("throwable=" + this.throwable)
        .toString();
  }
}
