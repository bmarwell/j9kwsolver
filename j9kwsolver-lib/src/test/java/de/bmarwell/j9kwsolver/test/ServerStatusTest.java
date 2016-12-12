package de.bmarwell.j9kwsolver.test;

import de.bmarwell.j9kwsolver.DefaultJ9kwServer;
import de.bmarwell.j9kwsolver.response.ServerStatus;

import org.junit.Assert;
import org.junit.Test;

public class ServerStatusTest {

  @Test
  public void testServerStatus() {
    DefaultJ9kwServer j9kwServerAPI = new DefaultJ9kwServer();

    ServerStatus serverStatus = j9kwServerAPI.getServerStatus();

    Assert.assertNotNull("ServerStatus should not be null.", serverStatus);
  }
}
