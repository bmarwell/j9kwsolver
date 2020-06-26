/*
 * J9KW Solver Library
 * Copyright (C) 2020, j9kwsolver contributors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

package de.bmarwell.j9kwsolver.lib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

public class BodyInterceptor implements ReaderInterceptor {

  private static final Logger LOG = LoggerFactory.getLogger(BodyInterceptor.class);

  @Override
  public Object aroundReadFrom(final ReaderInterceptorContext context) throws IOException, WebApplicationException {
    if (!LOG.isTraceEnabled()) {
      return context.proceed();
    }

    try (final InputStream inputStream = context.getInputStream()) {
      final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

      final byte[] data = new byte[16384];

      int numberBytesRead;

      while ((numberBytesRead = inputStream.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, numberBytesRead);
      }

      final String content = new String(data, StandardCharsets.UTF_8);
      LOG.trace("Read content: [{}].", content);
      final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
      context.setInputStream(byteArrayInputStream);
    }

    return context.proceed();
  }
}
