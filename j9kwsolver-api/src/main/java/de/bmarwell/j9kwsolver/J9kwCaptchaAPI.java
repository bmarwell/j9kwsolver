/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver;

import de.bmarwell.j9kwsolver.request.CaptchaSolution;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;
import de.bmarwell.j9kwsolver.response.RequestCaptchaResponse;

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
   * @return a captcha.
   */
  CompletableFuture<RequestCaptchaResponse> getNewCaptcha(final boolean tryLoop);

  /**
   * Submit a solved captcha.
   * 
   * @param solution
   *          the solution object provided by user input.
   * @return the Future object which holds the request.
   */
  CompletableFuture<CaptchaSolutionResponse> solveCaptcha(final CaptchaSolution solution);

}
