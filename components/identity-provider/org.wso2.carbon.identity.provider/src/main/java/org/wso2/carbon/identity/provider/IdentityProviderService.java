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

import org.wso2.carbon.identity.provider.model.AuthenticationConfig;
import org.wso2.carbon.identity.provider.model.IdPMetadata;
import org.wso2.carbon.identity.provider.model.IdentityProvider;
import org.wso2.carbon.identity.provider.model.ProvisioningConfig;

import java.util.List;
import java.util.Map;

/**
 * Defines the functionality that should be supported by Identity Provider Service.
 */
//ToDO Address patching the identity provider, without rewriting all again when only a section is modified.
public interface IdentityProviderService {

    /**
     * Lists the names of all the Identity Providers.
     *
     * @return List<IDP Name>
     * @throws IdentityProviderException
     */
    List<String> listIdentityProviders() throws IdentityProviderException;

    /**
     * Lists the names of all the Identity Providers which are currently enabled.
     *
     * @return List<IDP Name>
     * @throws IdentityProviderException
     */
    List<String> listEnabledIdentityProviders() throws IdentityProviderException;

    /**
     * Creates the Identity provider and returns the primary ID.
     *
     * @param identityProvider
     * @return
     * @throws IdentityProviderException when there is any error in creating the Identity Provider.
     */
    int createIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    /**
     * Returns the Identity provider given the primary ID of the IDP.
     *
     * @param identityProviderId the primary ID.
     * @return the Identity Provider matches the ID. Null if no Identity provider found for the given ID.
     * @throws IdentityProviderException
     */
    IdentityProvider getIdentityProvider(int identityProviderId) throws IdentityProviderException;

    /**
     * Returns the Identity provider given the unique name of the IDP.
     *
     * @param idPName unique name of IDP
     * @return the Identity Provider matches the name. Null if no Identity provider found for the given name.
     * @throws IdentityProviderException
     */
    IdentityProvider getIdentityProvider(String idPName) throws IdentityProviderException;

    /**
     * Returns the Identity provider identified by the unique property value of IDP.
     *
     * @param name  a unique property of an IDP
     * @param value value of the unique property
     * @return the Identity Provider matches the property value. Null if no Identity provider found for the given pair.
     * @throws IdentityProviderException
     */
    IdentityProvider getIdPByUniqueProperty(String name, Object value) throws IdentityProviderException;

    /**
     * Returns the Identity provider identified by the unique property value of a configured authenticator.
     *
     * @param name  a unique property of an authenticator
     * @param value value of the unique property
     * @return the Identity Provider matches the property value. Null if no Identity provider found for the given pair.
     * @throws IdentityProviderException
     */
    IdentityProvider getIdPByUniqueAuthenticatorProperty(String name, Object value) throws IdentityProviderException;

    /**
     * Returns the Identity provider identified by the unique property value of a configured provisioner.
     *
     * @param name  a unique property of a provisioner
     * @param value value of the unique property
     * @return the Identity Provider matches the property value. Null if no Identity provider found for the given pair.
     * @throws IdentityProviderException
     */
    IdentityProvider getIdPByUniqueProvisionerProperty(String name, Object value) throws IdentityProviderException;

    /**
     * Updates the saved information about the identity provider
     *
     * @param identityProvider carries the updated information of the identity provider.
     * @throws IdentityProviderException
     */
    void updateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException;

    /**
     * Delete the identity provider identified by the given id.
     *
     * @param identityProviderId primary of identity provider
     * @throws IdentityProviderException
     */
    void deleteIdentityProvider(int identityProviderId) throws IdentityProviderException;

    /**
     * Enables the identity provider identified by the given id, to be used by the service providers.
     *
     * @param identityProviderId primary of identity provider
     * @throws IdentityProviderException
     */
    void enableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    /**
     * Disables the identity provider identified by the given id. This avoids the identity provider been used by the
     * service providers. But keeps the configuration details.
     *
     * @param identityProviderId primary if of identity provider
     * @throws IdentityProviderException
     */
    void disableIdentityProvider(int identityProviderId) throws IdentityProviderException;

    /**
     * Update the meta details of an identity provider
     *
     * @param identityProviderId primary if of the identity provider
     * @param metadata           carries the updated meta details
     * @throws IdentityProviderException
     */
    void updateIdPMetadata(int identityProviderId, IdPMetadata metadata) throws IdentityProviderException;

    /**
     * Update just the authentication details of the identity provider, identified by the given id.
     *
     * @param identityProviderId   primary id of identity provider
     * @param authenticationConfig carries the updated details of authentication configuration
     * @throws IdentityProviderException
     */
    void updateIdPAuthenticationConfig(int identityProviderId, AuthenticationConfig authenticationConfig)
            throws IdentityProviderException;

    /**
     * Update just the provisioning details of the identity provider, identified by the given id.
     *
     * @param identityProviderId primary id of identity provider
     * @param provisioningConfig carries the updated details of provisioning configuration
     * @throws IdentityProviderException
     */
    void updateIdPProvisioningConfig(int identityProviderId, ProvisioningConfig provisioningConfig)
            throws IdentityProviderException;

    /**
     * Update just the property details of the identity provider, identified by the given id.
     *
     * @param identityProviderId primary id of the identity provider
     * @param properties         carries the updated property details
     * @throws IdentityProviderException
     */
    void updateIdPProperties(int identityProviderId, Map<String, Object> properties) throws IdentityProviderException;

}
