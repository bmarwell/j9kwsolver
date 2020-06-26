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

package de.bmarwell.j9kwsolver.test;

import de.bmarwell.j9kwsolver.lib.DefaultJ9kwUser;
import de.bmarwell.j9kwsolver.lib.service.ImmutablePropertyService;
import de.bmarwell.j9kwsolver.response.UserBalance;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UserAccountIT {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UserAccountIT.class);

  @Test
  public void getBalanceTest() {
    final DefaultJ9kwUser j9kwUserAPI = new DefaultJ9kwUser(ImmutablePropertyService.builder()
        .apiKey(UUID.randomUUID().toString())
        .build());

    final UserBalance balance = j9kwUserAPI.getBalance();

    LOG.debug("Balance: [{}].", balance);

    Assert.assertNotNull("Should not have been null.", balance);
  }
}
