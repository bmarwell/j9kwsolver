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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

public class ResponseContentTypeChanger implements ReaderInterceptor {

  private static final String EXPECTED_MEDIA_TYPE = MediaType.APPLICATION_JSON;

  private static final String HEADER_NAME = "Content-Type";

  public ResponseContentTypeChanger() {
    // no options.
  }


  /**
   * Fixes the content type.<br>
   * {@inheritDoc}
   */
  @Override
  public Object aroundReadFrom(final ReaderInterceptorContext context) throws IOException, WebApplicationException {
    final MultivaluedMap<String, String> headers = context.getHeaders();
    final String contentTypeHeader = headers.getFirst(HEADER_NAME);

    if (!contentTypeHeader.startsWith(EXPECTED_MEDIA_TYPE)) {
      headers.putSingle("Content-Type", EXPECTED_MEDIA_TYPE);
    }

    return context.proceed();
  }
}
