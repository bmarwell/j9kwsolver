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

import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import ru.lanwen.wiremock.config.WiremockConfigFactory;

public class SourceInMainConfigurator implements WiremockConfigFactory {

  @Override
  public WireMockConfiguration create() {
    return WireMockConfiguration.options()
        .bindAddress("127.0.0.1")
        .dynamicPort()
        .usingFilesUnderClasspath("wiremock")
        // enable Templating Response!
        // @see : http://wiremock.org/docs/response-templating/
        .extensions(new ResponseTemplateTransformer(true))
        .notifier(new Slf4jNotifier(true));
  }
}
