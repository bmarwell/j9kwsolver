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

import org.immutables.value.Value;

import java.net.URI;

@Value.Immutable
@Value.Style(stagedBuilder = true, jdkOnly = true, visibility = Value.Style.ImplementationVisibility.PUBLIC)
abstract class AbstractJ9kwSolverConfig implements J9kwSolverConfig {
  private static final long serialVersionUID = 5322316035760787247L;

  @Override
  public abstract String getApiKey();

  @Override
  @Value.Default
  public URI getApiURI() {
    return J9kwSolverConfig.super.getApiURI();
  }

  @Override
  @Value.Default
  public boolean isDebug() {
    return J9kwSolverConfig.super.isDebug();
  }
}
