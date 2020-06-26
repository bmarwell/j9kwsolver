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

package de.bmarwell.j9kwsolver.lib;

import de.bmarwell.j9kwsolver.J9kwUserApi;
import de.bmarwell.j9kwsolver.lib.service.BodyInterceptor;
import de.bmarwell.j9kwsolver.lib.service.PropertyService;
import de.bmarwell.j9kwsolver.lib.service.ResponseSanitizer;
import de.bmarwell.j9kwsolver.response.UserBalance;

import org.immutables.gson.stream.GsonMessageBodyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * An API for retrieving user information.
 * @author Benjamin Marwell
 *
 */
public final class DefaultJ9kwUser implements J9kwUserApi {
  public static final Logger LOG = LoggerFactory.getLogger(DefaultJ9kwUser.class);

  private static final String J9KW_SERVER_HOST = "https://www.9kw.eu";

  private static final String J9KW_BALANCE_PATH = "index.cgi";

  private final PropertyService propertyService;

  public DefaultJ9kwUser(final PropertyService propertyService) {
    this.propertyService = propertyService;
  }

  @Override
  public UserBalance getBalance() {
    final Client client = ClientBuilder.newBuilder()
        .register(new BodyInterceptor())
        .register(new GsonMessageBodyProvider())
        .build();

    final WebTarget target = client
        .target(J9KW_SERVER_HOST)
        .path(J9KW_BALANCE_PATH)
        .queryParam("action", "usercaptchaguthaben")
        .queryParam("apikey", this.propertyService.getApiKey())
        .queryParam("json", "1");

    LOG.debug("Target: [{}].", target);

    final Response response = target
        .request(MediaType.APPLICATION_JSON_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .get();

    ResponseSanitizer.sanitizeResponse(response);

    LOG.debug("Response: [{}].", response);
    LOG.debug("MT: [{}].", response.getMediaType());
    final UserBalance userBalance = response.readEntity(UserBalance.class);

    LOG.debug("UserBalance: [{}].", userBalance);

    return userBalance;
  }

}
