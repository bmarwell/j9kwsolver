package de.bmarwell.j9kwsolver.api.domain;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.awt.image.BufferedImage;
import java.util.Optional;

@Value.Immutable
@Gson.TypeAdapters
public interface Captcha {
  Optional<BufferedImage> image();
}
