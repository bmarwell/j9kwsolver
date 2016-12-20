package de.bmarwell.j9kwsolver.response;

import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * A solution to a captcha response.
 *
 * <p>Examples:
 *
 * <pre>
 { "error": "0013 Keine Antwort enthalten. / No answer found.",  "status": { "success": false, "https": 1} }
 { "error": "0007 Keine ID gefunden. / No ID found.",            "status": { "success": false, "https": 1} }
 { "newcredits": "", "message": "Not OK", "captchakey":"20888V", "status": { "success": true,  "https": 1} }
 { "newcredits":  7, "message": "OK",     "captchakey":"2C888V", "status": { "success": true,  "https": 1} }
 * </pre>
 *
 * </p>
 */
@Value.Immutable
@Gson.TypeAdapters
public interface CaptchaSolutionResponse {
  public static final String MESSAGE_ACCEPTED = "OK";

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
