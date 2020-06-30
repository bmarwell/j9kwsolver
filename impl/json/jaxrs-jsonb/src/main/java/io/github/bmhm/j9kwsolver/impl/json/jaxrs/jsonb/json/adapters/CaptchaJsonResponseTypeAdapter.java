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

import io.github.bmhm.j9kwsolver.api.value.CaptchaType;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.AbstractCaptchaJsonResponse;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.CaptchaJsonResponseImage;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.JsonbProvider;

import java.lang.reflect.Type;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbException;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;

public class CaptchaJsonResponseTypeAdapter implements JsonbDeserializer<AbstractCaptchaJsonResponse> {

  @Override
  public AbstractCaptchaJsonResponse deserialize(final JsonParser parser, final DeserializationContext ctx, final Type rtType) {
    final JsonObject jsonObject = parser.getObject();
    if (!jsonObject.containsKey("captchaid")) {
      throw new JsonbException("No field \"captchaid\" found.");
    }

    for (final CaptchaType type : CaptchaType.values()) {
      if (jsonObject.containsKey(type.getFieldName()) && jsonObject.getInt(type.getFieldName()) == 1) {
        // yes
        return deserialize(jsonObject, type);
      }

      throw new IllegalArgumentException("Cannot deserialize: [" + jsonObject + "].");
    }

    // TODO: implement
    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.CaptchaJsonResponseAdapter::adaptFromJson].");

  }

  private AbstractCaptchaJsonResponse deserialize(final JsonObject jsonObject, final CaptchaType type) {
    final Jsonb instance = JsonbProvider.getInstance();
    switch (type) {
      case IMAGE:
        // the parser is forward-only, hence we cannot use context here.
        return instance.fromJson(jsonObject.toString(), CaptchaJsonResponseImage.class);
      default:
        throw new UnsupportedOperationException(
            "not yet implemented: "
                + "[io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.CaptchaJsonResponseAdapter::deserialize], "
                + "type [" + type + "].");
    }
  }

}
