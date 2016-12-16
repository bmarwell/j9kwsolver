package de.bmarwell.j9kwsolver.gui;

import de.bmarwell.j9kwsolver.J9kwCaptchaAPI;
import de.bmarwell.j9kwsolver.J9kwServerApi;
import de.bmarwell.j9kwsolver.J9kwUserApi;
import de.bmarwell.j9kwsolver.lib.DefaultJ9kwCaptcha;
import de.bmarwell.j9kwsolver.lib.DefaultJ9kwServer;
import de.bmarwell.j9kwsolver.lib.DefaultJ9kwUser;
import de.bmarwell.j9kwsolver.lib.service.PropertyService;
import de.bmarwell.j9kwsolver.request.CaptchaSolution;
import de.bmarwell.j9kwsolver.request.ImmutableCaptchaSolution;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;
import de.bmarwell.j9kwsolver.response.RequestCaptchaResponse;
import de.bmarwell.j9kwsolver.response.ServerStatus;
import de.bmarwell.j9kwsolver.response.UserBalance;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class J9kwSolverGui {
  /**
   * Minimal length for API key.
   */
  private static final int APIKEY_MIN_LENGTH = 8;
  /**
   * Thread sleep time for testing purposes.
   */
  private static final int THEAD_SLEEP_TIME_MS = 2500;
  private static final Logger LOG = LoggerFactory.getLogger(J9kwSolverGui.class);

  private PropertyService propertyService;

  /**
   * @param args
   *          command line arguments.
   */
  public static void main(final String[] args) {
    J9kwSolverGui j9kwSolverGui = new J9kwSolverGui();

    j9kwSolverGui.run();
  }

  public J9kwSolverGui() {
    this.propertyService = new PropertyService();
  }

  private void run() {
    RequestCaptchaResponse captcha = null;

    testApiKey();

    captcha = getCaptcha();

    LOG.debug("Captcha received! [{}].", captcha);
    String solution = getCaptchaSolution(captcha);

    if (captcha != null) {
      solveCaptcha(captcha, solution);
    }

    getServerStatus();
    getBalance();

    Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
    LOG.debug("Threads running: {}.", threadSet);
  }

  /**
   * API call to J9kwCaptchaAPI.getNewCaptcha().
   * @return the Captcha provided by 9kw.
   */
  private RequestCaptchaResponse getCaptcha() {
    LOG.debug("Hole neues Captcha.");
    Optional<RequestCaptchaResponse> captcha = Optional.empty();
    J9kwCaptchaAPI jca = new DefaultJ9kwCaptcha(propertyService);

    try {
      while (!captcha.isPresent() || captcha.get().message().isPresent()) {
        LOG.debug("Getting next captcha.");
        captcha = Optional.ofNullable(jca.getNewCaptcha(true).get());
        LOG.debug("Gotten captcha: [{}].", captcha.orElseGet(() -> null));

        Thread.sleep(1000);
      }
      LOG.debug("Captcha: {}.", captcha);
    } catch (InterruptedException e) {
      LOG.error("Interrupted?!", e);
    } catch (ExecutionException e) {
      LOG.error("Could not execute?!", e);
    }

    return captcha.get();
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
    CaptchaSolution solution = ImmutableCaptchaSolution.builder()
        .id("")
        .apikey(propertyService.getApiKey())
        .captcha(solutionText)
        .build();

    J9kwCaptchaAPI jca = new DefaultJ9kwCaptcha(propertyService);
    CompletableFuture<CaptchaSolutionResponse> solveCaptcha = jca.solveCaptcha(solution);

    boolean accepted = false;

    try {
      CaptchaSolutionResponse acceptedSolution = solveCaptcha.get();
      LOG.debug(": {}.", acceptedSolution);
      accepted = acceptedSolution.accepted();
    } catch (InterruptedException e) {
      LOG.error("Interrupted?!", e);
    } catch (ExecutionException e) {
      LOG.error("Could not execute?!", e);
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
    J9kwShowImage display = new J9kwShowImage();
    String solution = display.show(captcha);

    return solution;
  }

  /**
   * Easy pause for testing.
   */
  public static void dosleep() {
    try {
      Thread.sleep(THEAD_SLEEP_TIME_MS);
    } catch (InterruptedException e) {
    }
  }

  /**
   * Call to J9kwServerAPI.getServerStatus().
   *
   * @return the Server status object or null, if unretrievable.
   */
  public static ServerStatus getServerStatus() {
    J9kwServerApi sa = new DefaultJ9kwServer();
    ServerStatus ss = sa.getServerStatus();

    LOG.debug("ServerStatus: {}.", ss);

    return ss;
  }

  /**
   * Call to J9kwUserAPI.getBalance().
   *
   * @return balance as int or -1 if unretrievable.
   */
  public UserBalance getBalance() {
    J9kwUserApi ua = new DefaultJ9kwUser(propertyService);

    UserBalance balance = ua.getBalance();
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
    String apiKey = propertyService.getApiKey();
    Preconditions.checkState(apiKey != null, "apiKey is null.");
    Preconditions.checkState(!apiKey.isEmpty(), "apiKey is empty.");
    Preconditions.checkState(apiKey.length() > APIKEY_MIN_LENGTH, "apiKey ist zu kurz.");
  }
}
