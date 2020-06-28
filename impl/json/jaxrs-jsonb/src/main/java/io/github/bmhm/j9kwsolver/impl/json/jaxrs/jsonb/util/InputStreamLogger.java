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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

public class InputStreamLogger implements ReaderInterceptor {

  private static final Logger LOG = Logger.getLogger(InputStreamLogger.class.getName());

  public InputStreamLogger() {
    super();
  }

  @Override
  public Object aroundReadFrom(final ReaderInterceptorContext context) throws IOException, WebApplicationException {
    try (final InputStream inputStream = context.getInputStream()) {
      final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      buffer.write(inputStream.readAllBytes());
      final String content = buffer.toString(StandardCharsets.UTF_8);

      LOG.log(Level.FINEST, String.format(Locale.ENGLISH, "Read content: [%s].", content));

      // push back input
      final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
      context.setInputStream(byteArrayInputStream);
    }

    return context.proceed();
  }
}
