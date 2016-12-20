/*
 * J9KW Solver Library
 * Copyright (C) 2016, j9kwsolver contributors.
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
 *
 */

package de.bmarwell.j9kwsolver.lib.service;

import de.bmarwell.j9kwsolver.lib.DefaultJ9kwUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public final class ResponseSanitizer {

  private static final Logger LOG = LoggerFactory.getLogger(ResponseSanitizer.class);

  private ResponseSanitizer() {
    // utility class
  }

  /**
   * Fixes the content type.
   *
   * @param response
   *          the response to sanitize.
   * @return the modified response for method chaining.
   */
  public static Response sanitizeResponse(Response response) {
    String contentType = (String) response.getHeaders().getFirst("Content-Type");

    LOG.debug("Headers: [{}].", response.getHeaders());

    if (!contentType.startsWith("application/json")) {
      DefaultJ9kwUser.LOG.warn("9kw delivering wrong contenttype [{}]. Fixing.", contentType);
      response.getHeaders().putSingle("Content-Type", "application/json");
    }

    return response;
  }

}
