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

package org.wso2.carbon.identity.provider;

import org.wso2.carbon.identity.provider.model.AuthenticationConfig;
import org.wso2.carbon.identity.provider.model.IdPMetadata;
import org.wso2.carbon.identity.provider.model.IdentityProvider;
import org.wso2.carbon.identity.provider.model.ProvisioningConfig;

import java.util.List;
import java.util.Map;

/**
 * Pre/Post interceptor methods for all IdentityProviderService operations
 */
public interface IdentityProviderInterceptor {

    void preListIdentityProviders() throws IdentityProviderException;

    void postListIdentityProviders(List<String> identityProviders) throws IdentityProviderException;

    void preListEnabledIdentityProviders() throws IdentityProviderException;

    void postListEnabledIdentityProviders(List<String> identityProviders) throws IdentityProviderException;

    void preCreateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    void postCreateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    void preGetIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void preGetIdentityProvider(String idPName) throws IdentityProviderException;

    void preGetIdentityProvider(String propertyName, Object value) throws IdentityProviderException;

    void preGetIdPByAuthenticatorProperty(String propertyName, Object value) throws IdentityProviderException;

    void preGetIdPByProvisionerProperty(String propertyName, Object value) throws IdentityProviderException;

    void postGetIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    void preUpdateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    void postUpdateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    void preDeleteIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void postDeleteIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void preEnableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void postEnableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void preDisableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void postDisableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    void preUpdateIdPMetadata(int identityProviderId, IdPMetadata metadata) throws IdentityProviderException;

    void postUpdateIdPMetadata(int identityProviderId, IdPMetadata metadata) throws IdentityProviderException;

    void preUpdateIdPAuthenticationConfig(int identityProviderId, AuthenticationConfig authenticationConfig) throws
                                                                                                          IdentityProviderException;

    void postUpdateIdPAuthenticationConfig(int identityProviderId, AuthenticationConfig authenticationConfig) throws
                                                                                                             IdentityProviderException;

    void preUpdateIdPProvisioningConfig(int identityProviderId, ProvisioningConfig provisioningConfig) throws
                                                                                                    IdentityProviderException;

    void postUpdateIdPProvisioningConfig(int identityProviderId, ProvisioningConfig provisioningConfig) throws
                                                                                                       IdentityProviderException;

    void preUpdateIdPProperties(int identityProviderId, Map<String,Object> properties) throws IdentityProviderException;

    void postUpdateIdPProperties(int identityProviderId, Map<String,Object> properties) throws
                                                                                        IdentityProviderException;

}
