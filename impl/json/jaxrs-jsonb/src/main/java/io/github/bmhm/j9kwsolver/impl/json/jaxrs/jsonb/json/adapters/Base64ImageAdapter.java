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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.json.JsonString;
import javax.json.bind.adapter.JsonbAdapter;
import javax.ws.rs.core.MediaType;

public class Base64ImageAdapter implements JsonbAdapter<BufferedImage, JsonString> {

  private static final String DATAURL_PREFIX = "data:";

  @Override
  public JsonString adaptToJson(final BufferedImage obj) throws Exception {
    // TODO: implement
    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.Base64ImageAdapter::adaptToJson].");
  }

  /**
   * Decodes a data URL to a buffered image.
   *
   * <p>Spec: {@code data:[<mediatype>][;base64],<data>}.<br></p>
   *
   * @param jsonString
   *     the json input string to parse as data url.
   * @return a buffered image.
   * @throws IOException
   *     if there was an error reading the data using {@link ImageIO}.
   * @throws IllegalArgumentException
   *     if the data is not a known {@code image/*} media type.
   */
  @Override
  public BufferedImage adaptFromJson(final JsonString jsonString) throws IOException {
    final String jsonStringValue = jsonString.getString();
    if (!jsonStringValue.startsWith(DATAURL_PREFIX)) {
      throw new IllegalArgumentException("Expected data: prefix.");
    }

    final String dataUrl = jsonStringValue.substring(DATAURL_PREFIX.length());
    final String[] metaAndData = dataUrl.split(",", 2);
    final String meta = metaAndData[0];

    final String mediaType = getMediaType(meta);

    final byte[] data;
    if (isBase64(meta)) {
      data = Base64.getDecoder().decode(metaAndData[1]);
    } else {
      data = metaAndData[1].getBytes(StandardCharsets.UTF_8);
    }

    switch (mediaType.toLowerCase(Locale.ENGLISH)) {
      case "image/png":
      case "image/jpeg":
      case "image/jpg":
        return ImageIO.read(new ByteArrayInputStream(data));
      default:
        throw new IllegalArgumentException("media type not implemented: [" + mediaType + "].");
    }
  }

  private boolean isBase64(final String meta) {
    return meta.contains(";base64");
  }

  private String getMediaType(final String meta) {
    // media type is optional and defaults to text/plain.
    // see: https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Data_URIs
    final String mediaType = meta.split(";base64", 2)[0];
    if (mediaType.isEmpty()) {
      return MediaType.TEXT_PLAIN;
    }

    return mediaType;
  }
}
