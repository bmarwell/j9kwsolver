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

package de.bmarwell.j9kwsolver.response;

import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * Server response object for server status.
 * @author Benjamin Marwell
 *
 */
@Value.Immutable
@Gson.TypeAdapters
public interface ServerStatus {

  @SerializedName("worker")
  int worker();

  @SerializedName("inwork")
  int inwork();

  @SerializedName("queue")
  int queue();

  @SerializedName("queue1")
  int queue1();

  @SerializedName("queue2")
  int queue2();

  @SerializedName("useronline")
  int useronline();
}
