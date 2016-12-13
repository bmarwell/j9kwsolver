package de.bmarwell.j9kwsolver.lib.err;

public class PropertyException extends RuntimeException {

  /**
   * PropertyException.
   */
  private static final long serialVersionUID = -925214728958198023L;

  public PropertyException(String message, Throwable cause) {
    super(message, cause);
  }

  public PropertyException(String message) {
    super(message);
  }

  public PropertyException(Throwable cause) {
    super(cause);
  }

}
