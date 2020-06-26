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

package de.bmarwell.j9kwsolver.lib.service;

import de.bmarwell.j9kwsolver.Constants;

import org.immutables.value.Value;


/**
 * Property Singleton Service.
 */
@Value.Immutable
public interface PropertyService {
  String getApiKey();

  default String getToolName() {
    return Constants.TOOL_NAME;
  }

  @Value.Default
  default boolean getDebug() {
    return false;
  }

  @Value.Derived
  default int getDebugAsInt() {
    return getDebug() ? 1 : 0;
  }

  @Value.Check
  default void check() {
    if (getApiKey().isEmpty()) {
      throw new IllegalArgumentException("apikey is empty");
    }
  }
}
