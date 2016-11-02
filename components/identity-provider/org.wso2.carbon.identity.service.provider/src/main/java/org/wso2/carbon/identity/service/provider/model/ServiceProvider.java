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

import org.wso2.carbon.identity.provider.model.GenericBuilder;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * IAM Service Provider, representing an application.
 * Holds
 * <ul>
 *    <li>inbound authentication</li>
 *    <li>outbound authentication, Identity provider configs</li>
 *    <li>local authentication (if any)</li>
 *    <li>claim configurations and mapping</li>
 * </ul>
 */
public class ServiceProvider implements Serializable {

    private int applicationID = 0;
    private String applicationName;
    private String displayLabel;
    private String description;

    public int getApplicationID() {
        return applicationID;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public String getDescription() {
        return description;
    }

    private void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    private void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    private void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public static ServiceProviderBuilder newBuilder() {
        return ServiceProviderBuilder.of(ServiceProvider::new);
    }

    public interface ServiceProviderBuildStep {

        ServiceProviderBuildStep withApplicationName(String applicationName);

        ServiceProviderBuildStep withDisplayLabel(String displayLabel);

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
        public ServiceProviderBuilder withApplicationName(String applicationName) {
            return (ServiceProviderBuilder) this.with(ServiceProvider::setApplicationName, applicationName);
        }

        @Override
        public ServiceProviderBuilder withDisplayLabel(String displayLabel) {
            return (ServiceProviderBuilder) this.with(ServiceProvider::setDisplayLabel, displayLabel);
        }

        @Override
        public ServiceProviderBuilder withDescription(String description) {
            return (ServiceProviderBuilder) this.with(ServiceProvider::setDescription, description);
        }
    }
}
