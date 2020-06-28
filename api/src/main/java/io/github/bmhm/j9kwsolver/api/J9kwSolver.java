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

import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ServiceLoader;

public final class J9kwSolver {

  public static final String TOOL_NAME = "j9kwSolver";

  private static final Class<J9kwSolverApi> API_CLASS = J9kwSolverApi.class;

  private J9kwSolver() {
    // util class
  }

  public static J9kwSolverApi getInstance(final J9kwSolverConfig config) {
    Objects.requireNonNull(config, "config must not be null");
    final Iterator<J9kwSolverApi> iterator = LazyHolder.INSTANCE.iterator();

    if (!iterator.hasNext()) {
      final String message = String.format(Locale.ENGLISH,
          "No implementation found for API class [%s]. "
              + "Please make sure your application ships a file [META-INF/services/%1$s].",
          API_CLASS);
      throw new NoSuchElementException(message);
    }

    final J9kwSolverApi j9kwSolverApi = iterator.next();
    j9kwSolverApi.setConfig(config);
    // set config?
    return j9kwSolverApi;
  }

  /**
   * Lazy initialization pattern.
   */
  private static class LazyHolder {
    private static final ServiceLoader<J9kwSolverApi> INSTANCE = ServiceLoader.load(API_CLASS);
  }
}
