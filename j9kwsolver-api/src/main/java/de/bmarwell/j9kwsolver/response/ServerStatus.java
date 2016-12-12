/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
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
