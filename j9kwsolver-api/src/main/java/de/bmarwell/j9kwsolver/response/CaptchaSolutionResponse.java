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

package de.bmarwell.j9kwsolver.response;

import com.google.gson.annotations.SerializedName;
import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * A solution to a captcha response.
 *
 * <p>Examples:</p>
 *
 * <code>
 * { "error": "0013 Keine Antwort enthalten. / No answer found.",  "status": { "success": false, "https": 1} }
 * { "error": "0007 Keine ID gefunden. / No ID found.",            "status": { "success": false, "https": 1} }
 * { "newcredits": "", "message": "Not OK", "captchakey":"20888V", "status": { "success": true,  "https": 1} }
 * { "newcredits":  7, "message": "OK",     "captchakey":"2C888V", "status": { "success": true,  "https": 1} }
 * </code>
 */
@Value.Immutable
@Gson.TypeAdapters
public interface CaptchaSolutionResponse {
  String MESSAGE_ACCEPTED = "OK";

  @SerializedName("error")
  Optional<String> error();

  @SerializedName("message")
  Optional<String> message();

  @SerializedName("status")
  CaptchaStatusObject status();

  @Value.Derived
  default boolean accepted() {
    if (error().isPresent()) {
      return false;
    }

    if (!message().equals(MESSAGE_ACCEPTED)) {
      return false;
    }

    if (!status().success()) {
      return false;
    }

    return true;
  }

  @SerializedName("newcredits")
  Optional<Integer> newcredits();
}
