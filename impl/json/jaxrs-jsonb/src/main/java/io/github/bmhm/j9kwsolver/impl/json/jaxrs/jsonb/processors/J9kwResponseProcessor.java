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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors;

import io.github.bmhm.j9kwsolver.api.request.J9kwApiResponse;

import java.util.Optional;
import java.util.OptionalInt;

public interface J9kwResponseProcessor<T> {

  void process();

  OptionalInt getResponseCode();

  Optional<Throwable> getException();

  Optional<T> getResult();

  J9kwApiResponse<T> getApiResponse();
}
