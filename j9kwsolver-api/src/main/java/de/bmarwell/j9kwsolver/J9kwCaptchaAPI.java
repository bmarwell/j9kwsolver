/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver;

import de.bmarwell.j9kwsolver.request.CaptchaSolution;
import de.bmarwell.j9kwsolver.response.Captcha;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;

import java.util.concurrent.CompletableFuture;


/**
 * An API for sending and retrieving captchas.
 *
 * @author Benjamin Marwell
 */
public interface J9kwCaptchaAPI {

  /**
   * Requests and accepts a captcha from the server.
   *
   * @param tryLoop
   *          - set yes to loop until captcha is received.
   * @return a captcha or null if none received (only possible with <code>tryLoop=false</code>.
   */
  CompletableFuture<Captcha> getNewCaptcha(final boolean tryLoop);

  /**
   * @param solution
   *          the solution object provided by user input.
   * @return the Future object which holds the request.
   */
  CompletableFuture<CaptchaSolutionResponse> solveCaptcha(final CaptchaSolution solution);

}