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

import static io.restassured.RestAssured.given;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;

@ExtendWith(
    value = {
        WiremockResolver.class,
        WiremockUriResolver.class
    }
)
public class WireMockCaptchaRequestTest {


  @Test
  public void testStubOk(@WiremockResolver.Wiremock(factory = SourceInMainConfigurator.class) final WireMockServer server,
                         @WiremockUriResolver.WiremockUri final String uri) {
    // given
    RestAssured.baseURI = uri;

    //when
    final WireMockCaptchaRequest wireMockCaptchaRequest = new WireMockCaptchaRequest(server);
    wireMockCaptchaRequest.stubValidRequest();

    // then
    given()
        .when()
        .get("index.cgi?action=usercaptchanew&apikey=valid&text=yes&extended=1&json=1&withok=1&filedata=1")
        .then()
        .assertThat().statusCode(200);
  }
}
