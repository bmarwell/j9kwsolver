/**
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

package de.bmarwell.j9kwsolver.lib;

import de.bmarwell.j9kwsolver.J9kwServerApi;
import de.bmarwell.j9kwsolver.response.ServerStatus;

import org.immutables.gson.stream.GsonMessageBodyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * An API for receiving server information.
 *
 * @author Benjamin Marwell
 */
public final class DefaultJ9kwServer implements J9kwServerApi {
  /**
   * The Logger of this class.
   */
  private static final Logger LOG = LoggerFactory.getLogger(DefaultJ9kwServer.class);

  private static final String J9KW_SERVER_HOST = "https://www.9kw.eu";
  private static final String J9KW_STATUS_PATH = "grafik/servercheck.json";


  @Override
  public ServerStatus getServerStatus() {
    Client client = ClientBuilder.newBuilder()
        .register(new GsonMessageBodyProvider())
        .build();
    WebTarget target = client.target(J9KW_SERVER_HOST).path(J9KW_STATUS_PATH);

    Response response = target
        .request(MediaType.APPLICATION_JSON_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .get();

    LOG.debug("Response: [{}].", response);
    LOG.debug("MT: [{}].", response.getMediaType());
    ServerStatus serverStatus = response.readEntity(ServerStatus.class);

    LOG.debug("ServerStatus: [{}].", serverStatus);

    return serverStatus;
  }

}
