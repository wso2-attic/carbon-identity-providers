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

package org.wso2.carbon.identity.provider.common.model;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Representation of meta information of an Identity Provider.
 */
public class IdPMetadata implements Serializable {

    private static final long serialVersionUID = -4977395047974321120L;

    private int id;
    private String name;
    private String displayLabel;
    private String description;

    // idp:cert -> 1..n
    // certs must be managed in keystore component
    // Map<certId,certAlias>
    private Map<String, String> certMap;

    private ClaimConfig claimConfig;
    private RoleConfig roleConfig;

    private IdPMetadata() {

    }

    private IdPMetadata(IdPMetadataBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.displayLabel = builder.displayName;
        this.description = builder.description;
        this.certMap = builder.certMap;
        this.claimConfig = (builder.claimConfigBuilder == null) ? null : builder.claimConfigBuilder.build();
        this.roleConfig = builder.roleConfigBuilder.build();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getCertMap() {
        return MapUtils.unmodifiableMap(certMap);
    }

    public ClaimConfig getClaimConfig() {
        return claimConfig;
    }

    public RoleConfig getRoleConfig() {
        return roleConfig;
    }


    static IdPMetadataBuilder newBuilder(int identityProviderId, String name) {
        return new IdPMetadataBuilder(identityProviderId, name);
    }

    /**
     * Builder class for meta representation of an identity provider.
     */
    static class IdPMetadataBuilder {

        private int id;
        private String name;
        private String displayName;
        private String description;
        private Map<String, String> certMap;
        private ClaimConfig.ClaimConfigBuilder claimConfigBuilder;
        private RoleConfig.RoleConfigBuilder roleConfigBuilder = new RoleConfig.RoleConfigBuilder();

        public IdPMetadataBuilder(int id, String name) {
            this.id = id;
            if (StringUtils.isNoneBlank(name)) {
                this.name = name;
            } else {
                throw new IllegalArgumentException("Invalid Identity Provider name: " + name);
            }
        }

        public IdPMetadataBuilder(String name) {
            if (StringUtils.isNoneBlank(name)) {
                this.name = name;
            } else {
                throw new IllegalArgumentException("Invalid Identity Provider name: " + name);
            }
        }

        public IdPMetadataBuilder setDisplayLabel(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public IdPMetadataBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public IdPMetadataBuilder setCerts(Map<String, String> certMap) {
            if (!certMap.isEmpty()) {
                this.certMap.clear();
                this.certMap.putAll(certMap);
            }
            return this;
        }

        public IdPMetadataBuilder addCert(String alias, String thumbPrint) {
            if (StringUtils.isNotBlank(alias) && StringUtils.isNotBlank(thumbPrint)) {
                this.certMap.put(alias, thumbPrint);
            }
            return this;
        }

        public IdPMetadataBuilder addCerts(Map<String, String> certMap) {
            if (!certMap.isEmpty()) {
                this.certMap.putAll(certMap);
            }
            return this;
        }

        public IdPMetadataBuilder setDialectId(int dialectId) {
            this.claimConfigBuilder = new ClaimConfig.ClaimConfigBuilder(dialectId);
            return this;
        }

        public IdPMetadataBuilder setRoleMappings(Map<Integer, String> roleMap) {
            this.roleConfigBuilder.setRoleMappings(roleMap);
            return this;
        }

        public IdPMetadataBuilder addRoleMapping(int localRoleId, String role2) {
            this.roleConfigBuilder.addRoleMapping(localRoleId, role2);
            return this;
        }

        public IdPMetadataBuilder addRoleMappings(Map<Integer, String> roleMap) {
            this.roleConfigBuilder.addRoleMappings(roleMap);
            return this;
        }

        public IdPMetadata build() {
            IdPMetadata result = new IdPMetadata(this);

            return result;
        }
    }

}
