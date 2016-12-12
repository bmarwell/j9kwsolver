/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver;

import de.bmarwell.j9kwsolver.response.UserBalance;
import de.bmarwell.j9kwsolver.service.PropertyService;

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
public final class J9kwUserAPI {
  private static final Logger LOG = LoggerFactory.getLogger(J9kwUserAPI.class);

  private static final String J9KW_SERVER_HOST = "https://www.9kw.eu";

  private static final String J9KW_BALANCE_PATH = "index.cgi";

  /**
   * Gets the apikey users's balance.
   *
   * @return - the balance in credits.
   */
  public UserBalance getBalance() {
    Client client = ClientBuilder.newBuilder()
        .register(new GsonMessageBodyProvider())
        .build();

    WebTarget target = client
        .target(J9KW_SERVER_HOST)
        .path(J9KW_BALANCE_PATH)
        .queryParam("action", "usercaptchaguthaben")
        .queryParam("apikey", PropertyService.getProperty("apikey"))
        .queryParam("json", "1");

    LOG.debug("Target: [{}].", target);

    Response response = target
        .request(MediaType.APPLICATION_JSON_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .get();

    LOG.debug("Response: [{}].", response);
    LOG.debug("MT: [{}].", response.getMediaType());
    UserBalance userBalance = response.readEntity(UserBalance.class);

    LOG.debug("UserBalance: [{}].", userBalance);

    return userBalance;
  }

}
