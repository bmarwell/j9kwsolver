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

import io.github.bmhm.j9kwsolver.api.value.CaptchaSelector;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSource;
import io.github.bmhm.j9kwsolver.api.value.CaptchaType;
import io.github.bmhm.j9kwsolver.api.value.ImmutableCaptchaSelector;

import java.util.Arrays;

public final class CaptchaSelectorFactory {

  private CaptchaSelectorFactory() {
    // private util constructor.
  }

  public static CaptchaSelector defaultSelector() {
    return ImmutableCaptchaSelector.builder()
        .captchaSource(CaptchaSource.ALL)
        .addAllCaptchaTypes(Arrays.asList(CaptchaType.defaultTypes()))
        .build();
  }

  public static CaptchaSelector imagesOnly() {
    return ImmutableCaptchaSelector.builder()
        .captchaSource(CaptchaSource.ALL)
        .addCaptchaTypes(CaptchaType.IMAGE)
        .build();
  }
}
