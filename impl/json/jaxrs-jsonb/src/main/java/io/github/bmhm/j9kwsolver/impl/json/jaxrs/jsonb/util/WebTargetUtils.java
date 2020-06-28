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

import io.github.bmhm.j9kwsolver.api.J9kwSolver;
import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

public class WebTargetUtils {

  private final J9kwSolverConfig config;

  public WebTargetUtils(final J9kwSolverConfig config) {
    this.config = config;
  }

  /**
   * Creates a client and a {@link WebTarget} for usage with an 9kw endpoint.
   *
   * <p>This method sets the following query parameters:</p>
   * <ul>
   *   <li>{@code apikey} - to the value provided in the config.</li>
   *   <li>{@code source} - to the value of {@link J9kwSolver#TOOL_NAME}.</li>
   *   <li>{@code extended} - to {@code 1}.</li>
   *   <li>{@code json} - to {@code 1}.</li>
   * </ul>
   *
   * @param path the path inside the 9kw API base URI. Most often this is just {@code index.cgi},
   * @return a WebTarget which can be used to build queries upon.
   */
  public WebTarget createWebTarget(final String path) {
    final Client client = LazyClientBuilderHolder.CLIENT_BUILDER
        .connectTimeout(this.config.getConnectionTimeout().toMillis(), TimeUnit.MILLISECONDS)
        .readTimeout(this.config.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS)
        .build();

    final URI targetUri = UriBuilder.fromUri(this.config.getApiURI())
        .path(path)
        .build();

    return client.target(targetUri)
        .queryParam("apikey", this.config.getApiKey())
        .queryParam("source", J9kwSolver.TOOL_NAME)
        .queryParam("extended", "1")
        .queryParam("debug", this.config.isDebug() ? "1" : "0")
        .queryParam("json", "1");
  }

  static class LazyClientBuilderHolder {

    public static final ClientBuilder CLIENT_BUILDER = createClient();

    private static ClientBuilder createClient() {
      return ClientBuilder.newBuilder()
          // fixes the response to contain the correct content-type header.
          .register(new ResponseContentTypeChanger())
          // can log the body on-the-fly, e.g. before exceptions destroy the input stream.
          .register(new InputStreamLogger())
          // register jsonb implementation.
          .register(new JsonbMessageBodyReader())
          // end
          ;
    }
  }
}
