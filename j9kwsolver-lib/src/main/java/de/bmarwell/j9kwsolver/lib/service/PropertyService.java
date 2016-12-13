/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.lib.service;

import de.bmarwell.j9kwsolver.lib.err.PropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Property Singleton Service.
 *
 * @author Benjamin Marwell
 *
 */
public final class PropertyService {
  /**
   * The logger instance for this utility class.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PropertyService.class);

  /**
   * Path to API KEY.
   */
  private static final String APIKEY_FILE_PATH = "/.config/j9kwsolver/apikey";

  private final String apiKey;

  private final String debug;

  private final String toolname;

  public PropertyService() {
    String userHome = System.getProperty("user.home");
    LOG.debug("ApiKeyFile = {}.", userHome + APIKEY_FILE_PATH);
    File apikeyFile = new File(userHome, APIKEY_FILE_PATH);

    try (Scanner in = new Scanner(apikeyFile)) {
      this.apiKey = in.nextLine();
    } catch (FileNotFoundException e) {
      LOG.warn("Konnte apikey-File nicht lesen!", e);

      throw new PropertyException("Could not read apikey file!", e);
    }

    this.toolname = "j9kwsolver";
    this.debug = "1";
  }

  public String getApiKey() {
    return this.apiKey;
  }

  public String getToolName() {
    return this.toolname;
  }
}
