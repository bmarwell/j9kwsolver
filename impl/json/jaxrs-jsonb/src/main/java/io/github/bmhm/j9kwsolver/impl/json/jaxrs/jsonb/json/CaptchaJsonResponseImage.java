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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json;

import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.Base64ImageAdapter;

import java.awt.image.BufferedImage;
import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;

public class CaptchaJsonResponseImage extends AbstractCaptchaJsonResponse {

  @JsonbProperty("file1")
  @JsonbTypeAdapter(Base64ImageAdapter.class)
  private BufferedImage image;

  public CaptchaJsonResponseImage() {
    super();
  }

  public BufferedImage getImage() {
    return this.image;
  }

  public void setImage(final BufferedImage image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", CaptchaJsonResponseImage.class.getSimpleName() + "[", "]")
        .add("super=" + super.toString())
        .add("image=" + this.image)
        .toString();
  }
}
