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
