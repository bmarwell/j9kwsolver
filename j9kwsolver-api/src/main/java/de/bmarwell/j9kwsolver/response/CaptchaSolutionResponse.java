package de.bmarwell.j9kwsolver.response;

public interface CaptchaSolutionResponse {
  boolean accepted();

  int points();
}
