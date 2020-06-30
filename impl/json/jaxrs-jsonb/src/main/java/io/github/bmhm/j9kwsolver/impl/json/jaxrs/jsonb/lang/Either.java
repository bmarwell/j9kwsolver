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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.lang;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class Either<L, R> {

  private final /* nullable */ L left;

  private final /* nullable */ R right;

  private Either(final L left, final R right) {
    this.left = left;
    this.right = right;
  }

  public void consume(final Consumer<? super L> leftConsumer, final Consumer<? super R> rightConsumer) {
    Optional.ofNullable(this.left).ifPresent(leftConsumer);
    Optional.ofNullable(this.right).ifPresent(rightConsumer);
  }

  public Optional<L> left() {
    return Optional.ofNullable(this.left);
  }

  public Optional<R> right() {
    return Optional.ofNullable(this.right);
  }

  public <T> T handle(final BiFunction<L, R, T> handler) {
    return handler.apply(this.left, this.right);
  }

  public static <L, R> Either<L, R> mapLeft(final L left) {
    return new Either<>(requireNonNull(left), null);
  }

  public static <L, R> Either<L, R> mapRight(final R right) {
    return new Either<>(null, requireNonNull(right));
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Either.class.getSimpleName() + "[", "]")
        .add("left=" + this.left)
        .add("right=" + this.right)
        .toString();
  }
}
