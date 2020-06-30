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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util;

import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.adapters.CaptchaIdAdapter;

import java.util.concurrent.atomic.AtomicReference;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public final class JsonbProvider {

  private static final AtomicReference<JsonbProvider> INSTANCE = new AtomicReference<>();

  private JsonbProvider() {
    // util.
  }

  public static Jsonb getInstance() {
    if (INSTANCE.get() == null) {
      ensureInstance();
    }

    return INSTANCE.get().getInstanceInternal();
  }

  private static void ensureInstance() {
    INSTANCE.getAndUpdate(JsonbProvider::getOrCreateJsonbMapper);
  }

  private static JsonbProvider getOrCreateJsonbMapper(final JsonbProvider old) {
    if (old != null) {
      return old;
    }

    return new JsonbProvider();
  }

  public Jsonb getInstanceInternal() {
    return LazyJsonbHolder.JSONB_HOLDER;
  }

  static class LazyJsonbHolder {
    public static final Jsonb JSONB_HOLDER = createJsonb();

    private static Jsonb createJsonb() {
      final JsonbConfig config = new JsonbConfig()
          .withAdapters(new CaptchaIdAdapter());

      return JsonbBuilder.create(config);
    }
  }
}
