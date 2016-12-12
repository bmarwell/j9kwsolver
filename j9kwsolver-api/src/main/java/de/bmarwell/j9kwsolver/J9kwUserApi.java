package de.bmarwell.j9kwsolver;

import de.bmarwell.j9kwsolver.response.UserBalance;

public interface J9kwUserApi {

  /**
   * Gets the apikey users's balance.
   *
   * @return - the balance in credits.
   */
  UserBalance getBalance();
}
