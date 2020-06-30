/*
 * J9kw solver library
 * Copyright 2013-2020 the j9kwsolver team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.bmhm.j9kwsolver.impl.json.jaxrs.jsonb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.bmhm.j9kwsolver.api.ImmutableJ9kwSolverConfig;
import io.github.bmhm.j9kwsolver.api.J9kwSolverConfig;
import io.github.bmhm.j9kwsolver.api.request.J9kwApiResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaId;
import io.github.bmhm.j9kwsolver.api.value.CaptchaReceiptResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaRequest;
import io.github.bmhm.j9kwsolver.api.value.CaptchaRequestImage;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolution;
import io.github.bmhm.j9kwsolver.api.value.CaptchaSolutionResponse;
import io.github.bmhm.j9kwsolver.api.value.CaptchaType;
import io.github.bmhm.j9kwsolver.api.value.ImmutableCaptchaSolution;
import io.github.bmhm.j9kwsolver.tests.testserver.SourceInMainConfigurator;
import io.github.bmhm.j9kwsolver.tests.testserver.WireMockCaptchaRequest;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;

import java.net.URI;
import javax.ws.rs.core.Response;

@ExtendWith(
    value = {
        WiremockResolver.class,
        WiremockUriResolver.class
    }
)
public class JaxRsJsonbJ9kwSolverApiTest {

  private static final CaptchaId CAPTCHA_ID = () -> "9269003";

  @Test
  public void testNewCaptcha(@WiremockResolver.Wiremock(factory = SourceInMainConfigurator.class) final WireMockServer server,
                             @WiremockUriResolver.WiremockUri final String uri) {
    // given
    // … valid request stubbed
    final WireMockCaptchaRequest wireMockCaptchaRequest = new WireMockCaptchaRequest(server);
    wireMockCaptchaRequest.stubValidRequest();
    // … implementation
    final J9kwSolverConfig config = ImmutableJ9kwSolverConfig.builder()
        .apiKey("valid")
        .apiURI(URI.create(uri))
        .build();
    final JaxRsJsonbJ9kwSolverApi jaxRsJsonb = new JaxRsJsonbJ9kwSolverApi();
    jaxRsJsonb.setConfig(config);

    // when
    final J9kwApiResponse<CaptchaRequest> apiResponse = jaxRsJsonb.requestNewCaptcha();

    // then
    assertTrue(apiResponse.isSuccessful(), apiResponse::toString);
    final CaptchaRequest captchaRequest = apiResponse.getResult().orElseThrow();
    assertAll(
        () -> assertEquals(CaptchaType.IMAGE, captchaRequest.getCaptchaType()),
        () -> assertEquals(300, captchaRequest.getTimeoutSeconds()),
        () -> assertEquals("130904348", captchaRequest.getCaptchaId().getValue()),
        () -> assertThat(captchaRequest, instanceOf(CaptchaRequestImage.class))
    );
  }

  @Test
  public void testNoNewCaptcha(@WiremockResolver.Wiremock(factory = SourceInMainConfigurator.class) final WireMockServer server,
                               @WiremockUriResolver.WiremockUri final String uri) {
    // given
    // … valid request stubbed
    final WireMockCaptchaRequest wireMockCaptchaRequest = new WireMockCaptchaRequest(server);
    wireMockCaptchaRequest.stubNoNewCaptchaRequest();
    // … implementation
    final J9kwSolverConfig config = ImmutableJ9kwSolverConfig.builder()
        .apiKey("valid")
        .apiURI(URI.create(uri))
        .build();
    final JaxRsJsonbJ9kwSolverApi jaxRsJsonb = new JaxRsJsonbJ9kwSolverApi();
    jaxRsJsonb.setConfig(config);

    // when
    final J9kwApiResponse<CaptchaRequest> apiResponse = jaxRsJsonb.requestNewCaptcha();

    // then
    assertFalse(apiResponse.isSuccessful(), apiResponse::toString);
    assertTrue(apiResponse.getResult().isEmpty());
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), apiResponse.getResponseCode().orElseThrow());
  }

  @Test
  public void testConfirmation(@WiremockResolver.Wiremock(factory = SourceInMainConfigurator.class) final WireMockServer server,
                               @WiremockUriResolver.WiremockUri final String uri) {
    // given
    // … valid request stubbed
    final WireMockCaptchaRequest wireMockCaptchaRequest = new WireMockCaptchaRequest(server);
    wireMockCaptchaRequest.stubValidConfirmation();
    // … implementation
    final J9kwSolverConfig config = ImmutableJ9kwSolverConfig.builder()
        .apiKey("valid")
        .apiURI(URI.create(uri))
        .build();
    final JaxRsJsonbJ9kwSolverApi jaxRsJsonb = new JaxRsJsonbJ9kwSolverApi();
    jaxRsJsonb.setConfig(config);

    // when
    final J9kwApiResponse<CaptchaReceiptResponse> confirmReception = jaxRsJsonb.confirmReception(CAPTCHA_ID);

    // then
    assertTrue(confirmReception.isSuccessful());
    final CaptchaReceiptResponse result = confirmReception.getResult().orElseThrow();
    assertAll(
        () -> assertEquals("OK", result.getMessage())
    );
  }

  // TODO: test skip

  @Test
  public void testSolve(@WiremockResolver.Wiremock(factory = SourceInMainConfigurator.class) final WireMockServer server,
                        @WiremockUriResolver.WiremockUri final String uri) {
    // given
    // … valid request stubbed
    final WireMockCaptchaRequest wireMockCaptchaRequest = new WireMockCaptchaRequest(server);
    wireMockCaptchaRequest.stubSolveRequest();
    // … implementation
    final J9kwSolverConfig config = ImmutableJ9kwSolverConfig.builder()
        .apiKey("valid")
        .apiURI(URI.create(uri))
        .build();
    final JaxRsJsonbJ9kwSolverApi jaxRsJsonb = new JaxRsJsonbJ9kwSolverApi();
    jaxRsJsonb.setConfig(config);
    // solved captcha
    final String solution = "2C888V";
    final CaptchaSolution captchaSolution = ImmutableCaptchaSolution.builder()
        .captchaId(CAPTCHA_ID)
        .isConfirmCaptcha(false)
        .solution(solution)
        .build();

    // when
    final J9kwApiResponse<CaptchaSolutionResponse> confirmReception = jaxRsJsonb.solveCaptcha(captchaSolution);

    // then
    assertTrue(confirmReception.isSuccessful());
    final CaptchaSolutionResponse result = confirmReception.getResult().orElseThrow();
    assertAll(
        () -> assertEquals("OK", result.getMessage()),
        () -> assertEquals(7, result.getNewCredits()),
        () -> assertEquals(solution, result.getCaptchaKey())
    );

  }
}
