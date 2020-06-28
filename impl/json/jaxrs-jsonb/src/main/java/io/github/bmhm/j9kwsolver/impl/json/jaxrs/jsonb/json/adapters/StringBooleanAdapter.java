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

import javax.json.Json;
import javax.json.JsonString;
import javax.json.bind.adapter.JsonbAdapter;

public class StringBooleanAdapter implements JsonbAdapter<Boolean, JsonString> {

  @Override
  public JsonString adaptToJson(final Boolean booleanValue) throws Exception {
    return Json.createValue(booleanValue ? "1" : "0");
  }

  @Override
  public Boolean adaptFromJson(final JsonString obj) throws Exception {
    final String string = obj.getString();

    switch (string) {
      case "0":
        return false;
      case "1":
        return true;
      default:
        throw new IllegalArgumentException("Expected '0' or '1', but got: [" + string + "].");
    }
  }
}
