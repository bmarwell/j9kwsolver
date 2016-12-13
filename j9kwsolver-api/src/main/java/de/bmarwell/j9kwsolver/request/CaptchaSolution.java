package de.bmarwell.j9kwsolver.request;

import de.bmarwell.j9kwsolver.Constants;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public interface CaptchaSolution {

  public static final String ACTION_STRING = "usercaptchacorrect";

  default String action() {
    return ACTION_STRING;
  }

  default int json() {
    return 1;
  }

  String id();

  /**
   * @return the solution.
   */
  String captcha();

  /**
   * @return the apikey.
   */
  String apikey();

  /**
   * @return the tool name.
   */
  default String source() {
    return Constants.TOOL_NAME;
  }

  default int extended() {
    return 1;
  }

  public static ImmutableCaptchaSolution.Builder builder() {
    return ImmutableCaptchaSolution.builder();
  }
}
