package de.bmarwell.j9kwsolver.response;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public interface RequestCaptchaResponse {
  String message();
}
