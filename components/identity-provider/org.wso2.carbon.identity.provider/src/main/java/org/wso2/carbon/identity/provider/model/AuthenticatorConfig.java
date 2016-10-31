/*
 * Copyright (c) 2016 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.provider.model;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent the configurations of an authenticator of IDP.
 */
public class AuthenticatorConfig implements Serializable {

    private static final long serialVersionUID = -4569973060498183209L;

    protected String name;
    protected boolean isEnabled;
    protected Map<String,Object> properties = new HashMap<>();

    protected AuthenticatorConfig(AuthenticatorConfigBuilder builder) {
        this.name = builder.name;
        this.properties = builder.properties;
    }

    public String getName() {
        return name;
    }

    public Map<String,Object> getProperties() {
        return MapUtils.unmodifiableMap(properties);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Builds the configuration of an authenticator of IDP.
     */
    public static class AuthenticatorConfigBuilder {

        private String name;
        private boolean isEnabled;
        private Map<String,Object> properties = new HashMap<>();

        public AuthenticatorConfigBuilder(String name) {
            this.name = name;
        }

        public AuthenticatorConfigBuilder setEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public AuthenticatorConfigBuilder setProperties(Map<String,Object> properties) {
            if (!properties.isEmpty()) {
                this.properties = new HashMap<>(properties);
            }
            return this;
        }

        public AuthenticatorConfigBuilder addProperty(String name, Object value) {
            if (StringUtils.isNotBlank(name) && value != null) {
                this.properties.put(name, value);
            }
            return this;
        }

        public AuthenticatorConfigBuilder addProperties(Map<String,Object> properties) {
            if (!properties.isEmpty()) {
                this.properties.putAll(properties);
            }
            return this;
        }

        public AuthenticatorConfig build() {
            return new AuthenticatorConfig(this);
        }
    }

}
