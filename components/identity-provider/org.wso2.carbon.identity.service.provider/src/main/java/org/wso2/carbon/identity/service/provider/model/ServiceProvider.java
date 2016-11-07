/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.identity.service.provider.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.provider.model.GenericBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ServiceProvider implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ServiceProvider.class);

    private int id;
    private String name;
    private String description;
    private LocalAndOutboundAuthenticationConfig localAndOutBoundAuthenticationConfig;
    private InboundAuthenticationConfig inboundAuthenticationConfig;
    //    private RequestPathAuthenticatorConfig[] requestPathAuthenticatorConfigs;  need to move into InboundAuthConfig
    private InboundProvisioningConfig inboundProvisioningConfig;
    private OutboundProvisioningConfig outboundProvisioningConfig;
    private ClaimConfig claimConfig;
    private PermissionsAndRoleConfig permissionAndRoleConfig;
    private boolean saasApp;
    private List<ServiceProviderProperty> spProperties = new ArrayList<>();

    private ServiceProvider() {

    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public InboundAuthenticationConfig getInboundAuthenticationConfig() {
        return inboundAuthenticationConfig;
    }

    /**
     * @param inboundAuthenticationConfig
     */
    public void setInboundAuthenticationConfig(InboundAuthenticationConfig inboundAuthenticationConfig) {
        this.inboundAuthenticationConfig = inboundAuthenticationConfig;
    }

    /**
     * @return
     */
    public LocalAndOutboundAuthenticationConfig getLocalAndOutBoundAuthenticationConfig() {
        return localAndOutBoundAuthenticationConfig;
    }

    /**
     * @param localAndOutBoundAuthenticationConfig
     */
    public void setLocalAndOutBoundAuthenticationConfig(
            LocalAndOutboundAuthenticationConfig localAndOutBoundAuthenticationConfig) {
        this.localAndOutBoundAuthenticationConfig = localAndOutBoundAuthenticationConfig;
    }

    /**
     * @return
     */
    public InboundProvisioningConfig getInboundProvisioningConfig() {
        return inboundProvisioningConfig;
    }

    /**
     * @param inboundProvisioningConfig
     */
    public void setInboundProvisioningConfig(InboundProvisioningConfig inboundProvisioningConfig) {
        this.inboundProvisioningConfig = inboundProvisioningConfig;
    }

    /**
     * @return
     */
    public OutboundProvisioningConfig getOutboundProvisioningConfig() {
        return outboundProvisioningConfig;
    }

    /**
     * @param outboundProvisioningConfig
     */
    public void setOutboundProvisioningConfig(OutboundProvisioningConfig outboundProvisioningConfig) {
        this.outboundProvisioningConfig = outboundProvisioningConfig;
    }

    /**
     * @return
     */
    public ClaimConfig getClaimConfig() {
        return claimConfig;
    }

    /**
     * @param claimConfig
     */
    public void setClaimConfig(ClaimConfig claimConfig) {
        this.claimConfig = claimConfig;
    }

    /**
     * @return
     */
    public PermissionsAndRoleConfig getPermissionAndRoleConfig() {
        return permissionAndRoleConfig;
    }

    /**
     * @param permissionAndRoleConfig
     */
    public void setPermissionAndRoleConfig(PermissionsAndRoleConfig permissionAndRoleConfig) {
        this.permissionAndRoleConfig = permissionAndRoleConfig;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSaasApp() {
        return saasApp;
    }

    public void setSaasApp(boolean saasApp) {
        this.saasApp = saasApp;
    }

    public static ServiceProviderBuilder newBuilder() {
        return ServiceProviderBuilder.of(ServiceProvider::new);
    }

    public interface ServiceProviderBuildStep {

        ServiceProviderBuildStep withName(String applicationName);

        ServiceProviderBuildStep withDescription(String description);
    }

    public static class ServiceProviderBuilder extends GenericBuilder<ServiceProvider>
            implements ServiceProviderBuildStep {

        public static ServiceProviderBuilder of(Supplier<ServiceProvider> instantiator) {
            return new ServiceProviderBuilder(instantiator);
        }

        private ServiceProviderBuilder(Supplier<ServiceProvider> instantiator) {
            super(instantiator);
        }

        @Override
        public ServiceProviderBuilder withName(String name) {
            return (ServiceProviderBuilder) this.with(ServiceProvider::setName, name);
        }

        @Override
        public ServiceProviderBuilder withDescription(String description) {
            return (ServiceProviderBuilder) this.with(ServiceProvider::setDescription, description);
        }
    }
}