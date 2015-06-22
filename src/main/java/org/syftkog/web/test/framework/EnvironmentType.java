/*
 * Copyright 2014 BenjaminLimb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syftkog.web.test.framework;

/**
 *
 * @author BenjaminLimb
 */
public enum EnvironmentType {

  /**
   *
   */
  LOCALHOST,

  /**
   *
   */
  DEVELOPMENT,

  /**
   *
   */
  INTEGRATION,

  /**
   *
   */
  STAGING,

  /**
   *
   */
  BETA,

  /**
   *
   */
  PRODUCTION;

  /**
   *
   * @return
   */
  public Environment load() {
    String key = "environments." + this.toString().toLowerCase() + ".default.host";
    String url = PropertiesRetriever.getString(key, null);
    if(url == null){
      throw new RuntimeException(key + " property could not be found.");
    }
    return new Environment(this, url);
  }

}
