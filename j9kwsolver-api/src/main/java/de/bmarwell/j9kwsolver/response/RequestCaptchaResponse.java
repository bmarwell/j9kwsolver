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

import com.google.common.base.MoreObjects;
import com.google.common.io.Closeables;
import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import javax.imageio.ImageIO;

/**
 * Response to requestcaptchanew.
 *
 * <p>Fields:<br>
 *
 * <pre>
 * <code>{
  "maxtimeout":600,
  "userstart":1481658533,
  "speed":null,
  "textonly":0,
  "confirm":0,
  "cookies":"",
  "captchakey":"",
  "textinstructions":"",
  "securetoken":"",
  "userstart_standard":1481658533,
  "timeout":30,
  "serverdate":1481658531,
  "withok":null,
  "file1":"PNGDATA",
  "multimouse":0,
  "startdate":1481658529,
  "captchaid":9269003,
  "pageurl":"",
  "proxy":"",
  "status":{"success":true,  "https":null},
  "angle":40,
  "mouse":0,
  "userstart_confirm":1481658533,
  "useragent":"",
  "rotate":0,
  "sitekey":"",
  "selfsolve":0,
  "speedlevel":0,
  "text":1,
  "interactive":0,
  "audio":0,
  "files":1,
  "puzzle":0
}</code>
 * </pre>
 */
@Value.Immutable
@Gson.TypeAdapters
public abstract class RequestCaptchaResponse {

  private static final Logger LOG = LoggerFactory.getLogger(RequestCaptchaResponse.class);

  @SerializedName("message")
  public abstract Optional<String> message();

  public abstract Optional<Integer> captchaid();

  public abstract Optional<Integer> serverdate();

  @SerializedName("file1")
  public abstract Optional<String> file1();

  @Value.Auxiliary
  public Optional<BufferedImage> image() {
    if (!file1().isPresent()) {
      LOG.debug("No file present.");
      return Optional.empty();
    }

    ByteArrayInputStream bais = null;

    try {
      LOG.debug("Getting bytes.");

      String[] split = file1().get().split(",");
      if (split.length != 2) {
        LOG.debug("Split hat nicht länge 2 sondern: [{}].", split.length);

        return Optional.empty();
      }

      byte[] decodecBytes = Base64.getDecoder().decode(split[1]);
      bais = new ByteArrayInputStream(decodecBytes);

      BufferedImage bi = ImageIO.read(bais);
      bais.close();

      LOG.debug("Habe Bild gelesen: [{}].", bi);

      return Optional.ofNullable(bi);
    } catch (IOException e) {
      LOG.error("Error reading.", e);
    } finally {
      Closeables.closeQuietly(bais);
    }
    LOG.debug("returning empty.");
    return Optional.empty();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("message", message())
        .add("captchaid", captchaid())
        .add("serverdate", serverdate())
        .add("hasImage", image().isPresent())
        .add("hasFile", file1() == null ? false : true)
        .toString();
  }
}
