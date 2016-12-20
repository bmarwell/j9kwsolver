package de.bmarwell.j9kwsolver.request;

import de.bmarwell.j9kwsolver.Constants;

import com.google.common.base.Preconditions;

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

  int id();

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

  @Value.Check
  default void check() {
    Preconditions.checkState(!captcha().isEmpty());
    Preconditions.checkState(id() != 0);
  }

  public static ImmutableCaptchaSolution.Builder builder() {
    return ImmutableCaptchaSolution.builder();
  }
}
