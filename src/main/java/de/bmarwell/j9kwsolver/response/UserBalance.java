package de.bmarwell.j9kwsolver.response;

import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public interface UserBalance {

  @SerializedName("credits")
  int credits();
}
