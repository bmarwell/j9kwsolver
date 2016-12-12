package de.bmarwell.j9kwsolver;

import de.bmarwell.j9kwsolver.response.ServerStatus;

public interface J9kwServerApi {

  /**
   * Gives the state of the 9kw-Server as an java object.
   *
   * @return ServerState Object
   */
  ServerStatus getServerStatus();
}
