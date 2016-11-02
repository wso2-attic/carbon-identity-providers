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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract representation of an Identity Provider.
 * @see org.wso2.carbon.identity.provider.model.ResidentIdentityProvider
 * @see org.wso2.carbon.identity.provider.model.FederatedIdentityProvider
 */
public abstract class IdentityProvider implements Serializable {

    private static final long serialVersionUID = 690422066395820761L;

    private boolean enabled;
    private final IdPMetadata idPMetadata;
    private AuthenticationConfig authenticationConfig;
    private ProvisioningConfig provisioningConfig;

    /**
     * IDP properties are that...
     * Map<Emp_Id, Name>
     */
    private Map<String,Object> properties = new HashMap<>();

    IdentityProvider(IdentityProviderBuilder builder) {
        this.enabled = builder.enabled;
        this.idPMetadata = builder.idPMetadataBuilder.build();
        this.provisioningConfig =
                builder.provisioningConfigBuilder != null ? builder.provisioningConfigBuilder.build() : null;
        this.authenticationConfig =
                builder.authenticationConfigBuilder != null ? builder.authenticationConfigBuilder.build() : null;
        this.properties = builder.properties;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public IdPMetadata getIdPMetadata() {
        return idPMetadata;
    }

    public ProvisioningConfig getProvisioningConfig() {
        return provisioningConfig;
    }

    public AuthenticationConfig getAuthenticationConfig() {
        return authenticationConfig;
    }

    // provisioning filter must go under JIT and Outbound provisioning configs
    // Prov. filter is a example to have multiple values for one IDP based on SP

    public Map<String,Object> getProperties() {
        return MapUtils.unmodifiableMap(properties);
    }

    /**
     * Builds the representation of identity provider including mata details, provisioning configuration builder,
     * authenticators and other properties.
     */
    public static abstract class IdentityProviderBuilder<T extends IdentityProvider> {

        private boolean enabled = true;
        private IdPMetadata.IdPMetadataBuilder idPMetadataBuilder;
        private ProvisioningConfig.ProvisioningConfigBuilder provisioningConfigBuilder =
                new ProvisioningConfig.ProvisioningConfigBuilder();
        private AuthenticationConfig.AuthenticationConfigBuilder authenticationConfigBuilder;
        private Map<String,Object> properties = new HashMap<>();

        public IdentityProviderBuilder(int id, String name) {
            this.idPMetadataBuilder = new IdPMetadata.IdPMetadataBuilder(id, name);
        }

        public IdentityProviderBuilder(String name) {
            this.idPMetadataBuilder = new IdPMetadata.IdPMetadataBuilder(name);
        }

        public IdentityProviderBuilder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public IdentityProviderBuilder setDisplayLabel(String displayName) {
            this.idPMetadataBuilder.setDisplayLabel(displayName);
            return this;
        }

        public IdentityProviderBuilder setDescription(String description) {
            this.idPMetadataBuilder.setDescription(description);
            return this;
        }

        public IdentityProviderBuilder setCerts(Map<String, String> certMap) {
            this.idPMetadataBuilder.setCerts(certMap);
            return this;
        }

        public IdentityProviderBuilder addCert(String alias, String thumbPrint) {
            this.idPMetadataBuilder.addCert(alias, thumbPrint);
            return this;
        }

        public IdentityProviderBuilder addCerts(Map<String, String> certMap) {
            this.idPMetadataBuilder.addCerts(certMap);
            return this;
        }

        public IdentityProviderBuilder setDialectId(int dialectId) {
            this.idPMetadataBuilder.setDialectId(dialectId);
            return this;
        }

        public IdentityProviderBuilder setRoleMappings(Map<Integer, String> roleMap) {
            this.idPMetadataBuilder.setRoleMappings(roleMap);
            return this;
        }

        public IdentityProviderBuilder addRoleMapping(int localRoleId, String role2) {
            this.idPMetadataBuilder.addRoleMapping(localRoleId, role2);
            return this;
        }

        public IdentityProviderBuilder addRoleMappings(Map<Integer, String> roleMap) {
            this.idPMetadataBuilder.addRoleMappings(roleMap);
            return this;
        }

        public IdentityProviderBuilder setProvisioningClaims(Collection<ProvisioningClaim> provisioningClaims) {
            this.provisioningConfigBuilder.setProvisioningClaims(provisioningClaims);
            return this;
        }

        public IdentityProviderBuilder addProvisionClaim(ProvisioningClaim provisioningClaim) {
            this.provisioningConfigBuilder.addProvisionClaim(provisioningClaim);
            return this;
        }

        public IdentityProviderBuilder addProvisioningClaims(Collection<ProvisioningClaim> provisioningClaims) {
            this.provisioningConfigBuilder.addProvisioningClaims(provisioningClaims);
            return this;
        }

        public IdentityProviderBuilder setProvisioningIdPs(Collection<String> provisioningIdPs) {
            this.provisioningConfigBuilder.setProvisioningIdP(provisioningIdPs);
            return this;
        }

        public IdentityProviderBuilder addProvisioningIdP(String provisioningIdP) {
            this.provisioningConfigBuilder.addProvisioningIdP(provisioningIdP);
            return this;
        }

        public IdentityProviderBuilder addProvisioningIdPs(Collection<String> provisioningIdPs) {
            this.provisioningConfigBuilder.addProvisioningIdPs(provisioningIdPs);
            return this;
        }

        public IdentityProviderBuilder setProvisioners(Collection<ProvisionerConfig> provisioners) {
            this.provisioningConfigBuilder.setProvisioners(provisioners);
            return this;
        }

        public IdentityProviderBuilder addProvisioner(ProvisionerConfig provisioner) {
            this.provisioningConfigBuilder.addProvisioner(provisioner);
            return this;
        }

        public IdentityProviderBuilder addProvisioners(Collection<ProvisionerConfig> provisioners) {
            this.provisioningConfigBuilder.addProvisioners(provisioners);
            return this;
        }

        public IdentityProviderBuilder setRequestedClaims(Collection<String> requestedClaims) {
            this.authenticationConfigBuilder.setRequestedClaims(requestedClaims);
            return this;
        }

        public IdentityProviderBuilder addRequestedClaim(String requestedClaim) {
            this.authenticationConfigBuilder.addRequestedClaim(requestedClaim);
            return this;
        }

        public IdentityProviderBuilder addRequestedClaims(Collection<String> requestedClaims) {
            this.authenticationConfigBuilder.addRequestedClaims(requestedClaims);
            return this;
        }

        public IdentityProviderBuilder setAuthenticators(Collection<AuthenticatorConfig> authenticators) {
            this.authenticationConfigBuilder.setAuthenticators(authenticators);
            return this;
        }

        public IdentityProviderBuilder addAuthenticator(AuthenticatorConfig authenticator) {
            this.authenticationConfigBuilder.addAuthenticator(authenticator);
            return this;
        }

        public IdentityProviderBuilder addAuthenticators(Collection<AuthenticatorConfig> authenticators) {
            this.authenticationConfigBuilder.addAuthenticators(authenticators);
            return this;
        }

        public IdentityProviderBuilder setIdentityProviderProperties(Map<String,Object> properties) {
            if (!properties.isEmpty()) {
                this.properties.clear();
                this.properties.putAll(properties);
            }
            return this;
        }

        public IdentityProviderBuilder addIdentityProviderProperty(String name, Object value) {
            if (StringUtils.isNotBlank(name) && value != null) {
                properties.put(name, value);
            }
            return this;
        }

        public IdentityProviderBuilder addIdentityProviderProperties(Map<String,Object> properties) {
            if (!properties.isEmpty()) {
                properties.putAll(properties);
            }
            return this;
        }

        public abstract T build() ;
    }
}
