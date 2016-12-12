package de.bmarwell.j9kwsolver.test;

import de.bmarwell.j9kwsolver.DefaultJ9kwUser;
import de.bmarwell.j9kwsolver.response.UserBalance;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccountTest {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UserAccountTest.class);

  @Test
  public void getBalanceTest() {
    DefaultJ9kwUser j9kwUserAPI = new DefaultJ9kwUser();

    UserBalance balance = j9kwUserAPI.getBalance();

    LOG.debug("Balance: [{}].", balance);

    Assert.assertNotNull("Should not have been null.", balance);
  }
}
