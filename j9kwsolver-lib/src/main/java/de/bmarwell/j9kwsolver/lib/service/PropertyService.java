/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.lib.service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.immutables.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Property Singleton Service.
 *
 * @author Benjamin Marwell
 *
 */
@Value.Immutable
public abstract class PropertyService {
  /**
   * The logger instance for this utility class.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PropertyService.class);

  private Config config;

  public PropertyService() {
    LOG.debug("Loading config: [{}]", System.getProperty("config.file"));
    config = ConfigFactory.load();
  }

  public Config getConfig() {
    return this.config;
  }

  public String getApiKey() {
    return config.getString("apikey");
  }

  public String getToolName() {
    return "j9kwsolver";
  }

  public String getDebug() {
    return config.getString("debug");
  }

  @Value.Check
  public void check() {
    config.getValue("apikey");
    config.getValue("debug");
  }
}
