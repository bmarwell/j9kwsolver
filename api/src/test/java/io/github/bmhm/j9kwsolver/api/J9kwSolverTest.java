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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.bmhm.j9kwsolver.api.test.DummyImpl;
import org.junit.jupiter.api.Test;

public class J9kwSolverTest {

  @Test
  public void testDummyImplementation_throws_null() {
    assertThrows(
        NullPointerException.class,
        () -> J9kwSolver.getInstance(null),
        "must throw NPE on null config."
    );
  }

  /**
   * Check if ServiceLoader works.
   */
  @Test
  public void testDummyImplementation_returnsImplementation() {
    // given config
    final J9kwSolverConfig config = ImmutableJ9kwSolverConfig.builder()
        .apiKey("abc")
        .build();

    // when
    final J9kwSolverApi instance = J9kwSolver.getInstance(config);

    // then
    assertEquals(DummyImpl.class, instance.getClass());
  }
}
