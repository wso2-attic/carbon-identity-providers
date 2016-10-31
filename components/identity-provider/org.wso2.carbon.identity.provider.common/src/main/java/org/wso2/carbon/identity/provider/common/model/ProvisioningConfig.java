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

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;

public class ProvisioningConfig {

    private JITProvisioningConfig jitProvisioningConfig;
    private Collection<ProvisioningClaim> provisioningClaims = new HashSet<ProvisioningClaim>();
    //ToDO This might be removed in the future. Kept for the moment as a triggering mechanism for provisioning
    private Collection<String> provisioningRoles = new HashSet<>();
    private Collection<ProvisionerConfig> provisioners = new HashSet<ProvisionerConfig>();

    private ProvisioningConfig(ProvisioningConfigBuilder builder) {
        this.jitProvisioningConfig = builder.jitProvisioningConfigBuilder.build();
        this.provisioningClaims = builder.provisioningClaims;
        this.provisioningRoles = builder.provisioningRoles;
        this.provisioners = builder.provisioners;
    }

    public Collection<ProvisioningClaim> getProvisioningClaims() {
        return provisioningClaims;
    }

    public Collection<String> getProvisioningRoles() {
        return provisioningRoles;
    }

    public JITProvisioningConfig getJitProvisioningConfig() {
        return jitProvisioningConfig;
    }

    public Collection<ProvisionerConfig> getProvisioners() {
        return CollectionUtils.unmodifiableCollection(provisioners);
    }

    /**
     * Builds the configurations available for provisioning.
     */
    public static class ProvisioningConfigBuilder {

        private Collection<ProvisioningClaim> provisioningClaims = new HashSet<ProvisioningClaim>();
        private Collection<String> provisioningRoles = new HashSet<>();
        private JITProvisioningConfig.JITProvisioningConfigBuilder jitProvisioningConfigBuilder =
                new JITProvisioningConfig.JITProvisioningConfigBuilder();
        private Collection<ProvisionerConfig> provisioners = new HashSet<ProvisionerConfig>();

        public ProvisioningConfigBuilder() {

        }

        public ProvisioningConfigBuilder setProvisioningClaims(
                Collection<ProvisioningClaim> provisioningClaims) {
            this.provisioningClaims.clear();
            this.provisioningClaims.addAll(provisioningClaims);
            return this;
        }

        public ProvisioningConfigBuilder addProvisionClaim(ProvisioningClaim provisioningClaim) {
            this.provisioningClaims.add(provisioningClaim);
            return this;
        }

        public ProvisioningConfigBuilder addProvisioningClaims(Collection<ProvisioningClaim> provisioningClaims) {
            this.provisioningClaims.addAll(provisioningClaims);
            return this;
        }

        public ProvisioningConfigBuilder setProvisioningRoles(
                Collection<String> provisioningRoles) {
            this.provisioningRoles.clear();
            this.provisioningRoles.addAll(provisioningRoles);
            return this;
        }

        public ProvisioningConfigBuilder addProvisionRole(String provisioningRole) {
            this.provisioningRoles.add(provisioningRole);
            return this;
        }

        public ProvisioningConfigBuilder addProvisioningRoles(
                Collection<String> provisioningRoles) {
            this.provisioningRoles.addAll(provisioningRoles);
            return this;
        }

        public ProvisioningConfigBuilder setProvisioningIdP(Collection<String> provisioningIdPs) {
            this.jitProvisioningConfigBuilder.setProvisioningIdPs(provisioningIdPs);
            return this;
        }

        public ProvisioningConfigBuilder addProvisioningIdP(String provisioningIdP) {
            this.jitProvisioningConfigBuilder.addProvisioningIdP(provisioningIdP);
            return this;
        }

        public ProvisioningConfigBuilder addProvisioningIdPs(Collection<String> provisioningIdPs) {
            this.jitProvisioningConfigBuilder.addProvisioningIdPs(provisioningIdPs);
            return this;
        }

        public ProvisioningConfigBuilder setProvisioners(
                Collection<ProvisionerConfig> provisioners) {
            if (!provisioners.isEmpty()) {
                this.provisioners.clear();
                this.provisioners.addAll(provisioners);
            }
            return this;
        }

        public ProvisioningConfigBuilder addProvisioner(ProvisionerConfig provisioner) {
            if (provisioner != null) {
                this.provisioners.add(provisioner);
            }
            return this;
        }

        public ProvisioningConfigBuilder addProvisioners(
                Collection<ProvisionerConfig> provisioners) {
            if (!provisioners.isEmpty()) {
                this.provisioners.addAll(provisioners);
            }
            return this;
        }

        public ProvisioningConfig build() {
            return new ProvisioningConfig(this);
        }
    }
}
