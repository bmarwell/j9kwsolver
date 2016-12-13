package de.bmarwell.j9kwsolver.lib.service;

import de.bmarwell.j9kwsolver.lib.DefaultJ9kwUser;

import javax.ws.rs.core.Response;

public final class ResponseSanitizer {
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

    if (!contentType.startsWith("application/json")) {
      DefaultJ9kwUser.LOG.warn("9kw delivering wrong contenttype [{}]. Fixing.", contentType);
      response.getHeaders().putSingle("Content-Type", "application/json");
    }

    return response;
  }

}
