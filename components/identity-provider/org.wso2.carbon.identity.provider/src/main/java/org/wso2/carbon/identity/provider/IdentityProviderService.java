/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.provider;

import org.wso2.carbon.identity.provider.common.model.AuthenticationConfig;
import org.wso2.carbon.identity.provider.common.model.AuthenticatorConfig;
import org.wso2.carbon.identity.provider.common.model.ClaimConfig;
import org.wso2.carbon.identity.provider.common.model.IdPMetadata;
import org.wso2.carbon.identity.provider.common.model.IdentityProvider;
import org.wso2.carbon.identity.provider.common.model.ProvisionerConfig;
import org.wso2.carbon.identity.provider.common.model.ProvisioningConfig;
import org.wso2.carbon.identity.provider.common.model.ResidentIdentityProvider;
import org.wso2.carbon.identity.provider.common.model.RoleConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Defines the functionality that should be supported by Identity Provider Service.
 */
public interface IdentityProviderService {

    List<String> listIdentityProviders() throws IdentityProviderException;

    List<String> listEnabledIdentityProviders() throws IdentityProviderException;

    /**
     * Creates the Identity provider and returns the primary ID.
     *
     * @param identityProvider
     * @return
     * @throws IdentityProviderException when there is any error in creating the Identity Provider
     */
    int createIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    IdentityProvider getIdentityProvider(int identityProviderId) throws IdentityProviderException;

    IdentityProvider getIdentityProvider(String idPName) throws IdentityProviderException;

    IdentityProvider getIdPByProperty(String name, Object value) throws IdentityProviderException;

    IdentityProvider getIdPByAuthenticatorProperty(String name, Object value) throws IdentityProviderException;

    IdentityProvider getIdPByProvisionerProperty(String name, Object value) throws IdentityProviderException;

    void updateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    void deleteIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void enableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void disableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void updateIdPMetadata(int identityProviderId, IdPMetadata metadata) throws IdentityProviderException;

    void updateIdPAuthenticationConfig(int identityProviderId, AuthenticationConfig authenticationConfig) throws
                                                                                                          IdentityProviderException;

    void updateIdPProvisioningConfig(int identityProviderId, ProvisioningConfig provisioningConfig) throws
                                                                                                    IdentityProviderException;

    void updateIdPProperties(int identityProviderId, Map<String,Object> properties) throws IdentityProviderException;

}
