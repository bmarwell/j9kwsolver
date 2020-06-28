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

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

@Consumes(MediaType.WILDCARD)
@Produces(MediaType.WILDCARD)
public class JsonbMessageBodyReader implements MessageBodyReader<Object> {

  private final Set<MediaType> mediaTypes = Set.of(MediaType.APPLICATION_JSON_TYPE);

  private final Jsonb jsonb;

  public JsonbMessageBodyReader() {
    this.jsonb = JsonbMapper.getInstance();
  }

  @Override
  public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return this.mediaTypes.contains(mediaType);
  }

  @Override
  public Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType,
                         final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream)
      throws IOException {
    try {
      return this.jsonb.fromJson(entityStream, type);
    } catch (final JsonbException jsonbException) {
      throw new IOException("Unable to deserialize JSON stream.", jsonbException);
    }
  }

}
