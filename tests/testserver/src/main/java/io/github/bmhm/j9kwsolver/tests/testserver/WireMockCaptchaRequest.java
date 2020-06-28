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

package io.github.bmhm.j9kwsolver.tests.testserver;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockCaptchaRequest {

  public static final String DEFAULT_PATH = "/index.cgi";
  private final WireMockServer wireMockServer;

  public WireMockCaptchaRequest(final WireMockServer server) {
    this.wireMockServer = server;
  }

  public void stubValidRequest() {
    this.wireMockServer.stubFor(
        get(urlPathEqualTo(DEFAULT_PATH))
            .withQueryParam("action", equalTo("usercaptchanew"))
            .withQueryParam("extended", equalTo("1"))
            .withQueryParam("json", equalTo("1"))
            .withQueryParam("filedata", equalTo("1"))
            .withQueryParam("apikey", equalTo("valid"))
            .withQueryParam("withok", equalTo("1"))
            .willReturn(aResponse()
                .withBodyFile("captcha.json")
                // 9kw.eu sends wrong headers.
                .withHeader("Content-Type", "text/plain"))
    );
  }

  public void stubValidConfirmation() {
    this.wireMockServer.stubFor(
        get(urlPathEqualTo(DEFAULT_PATH))
            .withQueryParam("action", equalTo("usercaptchanewok"))
            .withQueryParam("json", equalTo("1"))
            .withQueryParam("apikey", equalTo("valid"))
            // no id, that odd from 9kw.
            .willReturn(aResponse()
                .withBody("{\"message\":\"OK\",\"status\":{\"success\":true,\"https\":1}}")
                .withHeader("Content-Type", "application/json"))
    );
  }

  public void stubSolveRequest() {
    this.wireMockServer.stubFor(
        get(urlPathEqualTo(DEFAULT_PATH))
            .withQueryParam("action", equalTo("usercaptchacorrect"))
            .withQueryParam("json", equalTo("1"))
            .withQueryParam("apikey", equalTo("valid"))
            .withQueryParam("extended", equalTo("1"))
            .withQueryParam("captcha", equalTo("2C888V"))
            .withQueryParam("id", equalTo("9269003"))
            .willReturn(aResponse()
                .withBody("{\"message\":\"OK\",\"status\":{\"success\":true,\"https\":1},\"captchakey\":\"2C888V\",\"newcredits\":7}\n")
                .withHeader("Content-Type", "application/json"))
    );
  }
}
