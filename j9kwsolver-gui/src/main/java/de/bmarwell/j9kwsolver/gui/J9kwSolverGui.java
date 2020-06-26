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

package de.bmarwell.j9kwsolver.gui;

import de.bmarwell.j9kwsolver.J9kwCaptchaAPI;
import de.bmarwell.j9kwsolver.J9kwServerApi;
import de.bmarwell.j9kwsolver.J9kwUserApi;
import de.bmarwell.j9kwsolver.lib.DefaultJ9kwCaptcha;
import de.bmarwell.j9kwsolver.lib.DefaultJ9kwServer;
import de.bmarwell.j9kwsolver.lib.DefaultJ9kwUser;
import de.bmarwell.j9kwsolver.lib.service.ImmutablePropertyService;
import de.bmarwell.j9kwsolver.lib.service.PropertyService;
import de.bmarwell.j9kwsolver.request.CaptchaSolution;
import de.bmarwell.j9kwsolver.request.ImmutableCaptchaSolution;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;
import de.bmarwell.j9kwsolver.response.RequestCaptchaResponse;
import de.bmarwell.j9kwsolver.response.ServerStatus;
import de.bmarwell.j9kwsolver.response.UserBalance;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class J9kwSolverGui {

  private static final Logger LOG = LoggerFactory.getLogger(J9kwSolverGui.class);

  /**
   * Minimal length for API key.
   */
  private static final int APIKEY_MIN_LENGTH = 8;

  private final PropertyService propertyService;

  public J9kwSolverGui() {
    final Config config = ConfigFactory.load();
    this.propertyService = ImmutablePropertyService.builder()
        .apiKey(config.getString("apikey"))
        .debug(config.getBoolean("debug"))
        .build();
  }

  /**
   * @param args command line arguments.
   */
  public static void main(final String[] args) {
    final J9kwSolverGui j9kwSolverGui = new J9kwSolverGui();

    j9kwSolverGui.run();
  }

  private void run() {
    testApiKey();
    receiveCaptcha();

    getServerStatus();
    getBalance();

    final Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
    LOG.debug("Threads running: {}.", threadSet);
  }

  /**
   * API call to J9kwCaptchaAPI.getNewCaptcha().
   */
  private void receiveCaptcha() {
    LOG.debug("Hole neues Captcha.");
    final J9kwCaptchaAPI jca = new DefaultJ9kwCaptcha(this.propertyService);

    try {
      LOG.debug("Getting next captcha.");
      final CompletableFuture<RequestCaptchaResponse> newCaptcha = jca.getNewCaptcha(true);
      newCaptcha.whenComplete((RequestCaptchaResponse result, Throwable error) -> {
        if (error != null) {
          LOG.error("Problem receiving captcha", error);
          return;
        }

        if (LOG.isTraceEnabled()) {
          LOG.trace("Gotten captcha: [{}].", result);
        }
        final String solution = getCaptchaSolution(result);

        solveCaptcha(result, solution);
      });

      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (final InterruptedException interruptedException) {
      LOG.error("Interrupted?!", interruptedException);
    }
  }

  /**
   * Solves a Captcha, i.e. sending a solution to the server.
   *
   * @param captcha
   *          the Captcha which has been solved.
   * @param solutionText
   *          the Text solution of this Captcha. Should be text
   *          for text captchas, Yes/No for Confirm captchas or coordinates for
   *          mouse/click captchas.
   * @return true if server accepted the solution, otherwise false.
   */
  private boolean solveCaptcha(
      final RequestCaptchaResponse captcha,
      final String solutionText) {
    final CaptchaSolution solution = ImmutableCaptchaSolution.builder()
        .id(captcha.captchaid().orElseThrow())
        .apikey(this.propertyService.getApiKey())
        .captcha(solutionText)
        .build();

    final J9kwCaptchaAPI jca = new DefaultJ9kwCaptcha(this.propertyService);
    final CompletableFuture<CaptchaSolutionResponse> solveCaptcha = jca.solveCaptcha(solution);

    boolean accepted = false;

    try {
      final CaptchaSolutionResponse acceptedSolution = solveCaptcha.get();
      LOG.debug(": {}.", acceptedSolution);
      accepted = acceptedSolution.accepted();
    } catch (final InterruptedException interruptedException) {
      LOG.error("Interrupted?!", interruptedException);
    } catch (final ExecutionException executionException) {
      LOG.error("Could not execute?!", executionException);
    }

    return accepted;
  }

  /**
   * @param captcha
   *          the Captcha object to be solved.
   * @return the user entered solution as String object.
   */
  private String getCaptchaSolution(final RequestCaptchaResponse captcha) {
    /* Show image */
    final J9kwShowImage display = new J9kwShowImage();
    return display.show(captcha);
  }

  /**
   * Call to J9kwServerAPI.getServerStatus().
   *
   * @return the Server status object or null, if unretrievable.
   */
  public static ServerStatus getServerStatus() {
    final J9kwServerApi sa = new DefaultJ9kwServer();
    final ServerStatus ss = sa.getServerStatus();

    LOG.debug("ServerStatus: {}.", ss);

    return ss;
  }

  /**
   * Call to J9kwUserAPI.getBalance().
   *
   * @return balance as int or -1 if unretrievable.
   */
  public UserBalance getBalance() {
    final J9kwUserApi ua = new DefaultJ9kwUser(this.propertyService);

    final UserBalance balance = ua.getBalance();
    LOG.debug("Balance: {} credits.", balance);

    return balance;
  }

  /**
   * Retrieves the API Key.
   *
   * @throws IllegalStateException
   *           probleme mit dem ApiKey.
   */
  private void testApiKey() {
    final String apiKey = this.propertyService.getApiKey();
    Objects.requireNonNull(apiKey, "apiKey is null.");
    if (apiKey.isEmpty()) {
      throw new IllegalStateException("apiKey is empty");
    }
    if (apiKey.length() < APIKEY_MIN_LENGTH) {
      throw new IllegalArgumentException("apiKey ist zu kurz");
    }
  }
}
