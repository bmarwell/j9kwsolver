/**
 * J9KW Solver Library
 * Copyright (C) 2016, j9kwsolver contributors.
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
 *
 */

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
