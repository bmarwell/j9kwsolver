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

import org.immutables.value.Value;

import java.awt.image.BufferedImage;
import java.time.Duration;

@Value.Immutable
public abstract class AbstractCaptchaRequestImage implements CaptchaRequestImage {

  @Override
  public abstract CaptchaId getCaptchaId();

  @Override
  public abstract CaptchaType getCaptchaType();

  @Override
  public abstract long getTimeoutSeconds();

  @Override
  @Value.Default
  public Duration getTimeout() {
    return CaptchaRequestImage.super.getTimeout();
  }

  @Override
  public abstract BufferedImage getImage();
}
