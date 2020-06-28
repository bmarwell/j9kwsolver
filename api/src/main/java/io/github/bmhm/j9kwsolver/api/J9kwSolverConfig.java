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

package io.github.bmhm.j9kwsolver.api;

import java.io.Serializable;
import java.net.URI;
import java.time.Duration;

public interface J9kwSolverConfig extends Serializable {

  /**
   * Returns an API key.
   *
   * @return the API key.
   */
  String getApiKey();

  /**
   * Returns the API uri.
   *
   * <p>Implementations should have a sane default.</p>
   *
   * @return the API URL.
   */
  default URI getApiURI() {
    return URI.create("https://www.9kw.eu");
  }

  /**
   * Debug flag.
   *
   * @return is debug.
   */
  default boolean isDebug() {
    return false;
  }

  Duration getConnectionTimeout();

  Duration getReadTimeout();
}
