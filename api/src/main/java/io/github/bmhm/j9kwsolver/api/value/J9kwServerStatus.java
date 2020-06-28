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

package io.github.bmhm.j9kwsolver.api.value;

/**
 * Some server related information from the 9kw
 * <a href="https://www.9kw.eu/api.html#apigeneral-tab">General API</a>.
 */
public interface J9kwServerStatus {

  /**
   * @return the number of online users.
   */
  long getUserOnline();

  /**
   * Get number of workers.
   *
   * @return the number of workers.
   */
  long getWorkerCount();

  /**
   * The number of queued captchas.
   *
   * @return rhe number of queued captchas.
   */
  long getQueueLength();

  /**
   * The number of captchas in work.
   *
   * @return the number of captchas in work.
   */
  long getInWorkCount();
}
