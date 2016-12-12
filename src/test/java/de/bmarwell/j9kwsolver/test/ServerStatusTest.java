package de.bmarwell.j9kwsolver.test;

import de.bmarwell.j9kwsolver.J9kwServerAPI;
import de.bmarwell.j9kwsolver.response.ServerStatus;

import org.junit.Assert;
import org.junit.Test;

public class ServerStatusTest {

  @Test
  public void testServerStatus() {
    J9kwServerAPI j9kwServerAPI = new J9kwServerAPI();

    ServerStatus serverStatus = j9kwServerAPI.getServerStatus();

    Assert.assertNotNull("ServerStatus should not be null.", serverStatus);
  }
}
