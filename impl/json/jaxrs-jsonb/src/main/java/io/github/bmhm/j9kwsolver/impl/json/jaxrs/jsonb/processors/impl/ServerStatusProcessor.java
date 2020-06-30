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

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.processors.impl;

import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;
import io.github.bmhm.j9kwsolver.api.value.J9kwServerStatus;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.JsonResponseMapper;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.json.JsonServerStatus;
import io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb.util.WebTargetUtils;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class ServerStatusProcessor extends AbstractRequestProcessor<J9kwServerStatus> {

  public ServerStatusProcessor(final J9kwSolverConfig config) {
    super(config);
  }

  @Override
  public void process() {
    final WebTargetUtils webTargetUtils = new WebTargetUtils(getConfig());
    final WebTarget webTarget = webTargetUtils.createWebTarget("/grafik/servercheck.json");

    try {
      final Response response = webTarget.request().get();

      final JsonServerStatus jsonServerStatus = response.readEntity(JsonServerStatus.class);

      final J9kwServerStatus serverStatus = JsonResponseMapper.fromResonse(jsonServerStatus);

      setResult(serverStatus);
      setResponseCode(response.getStatus());
    } catch (final WebApplicationException webApplicationException) {
      setException(webApplicationException);
      setResponseCode(webApplicationException.getResponse().getStatus());
    } catch (final ProcessingException processingException) {
      setException(processingException);
    }
  }
}
