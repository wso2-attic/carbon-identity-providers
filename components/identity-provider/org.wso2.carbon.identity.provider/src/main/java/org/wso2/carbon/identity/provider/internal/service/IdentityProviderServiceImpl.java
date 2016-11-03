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

package org.wso2.carbon.identity.provider.internal.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.provider.IdentityProviderException;
import org.wso2.carbon.identity.provider.IdentityProviderService;
import org.wso2.carbon.identity.provider.model.AuthenticationConfig;
import org.wso2.carbon.identity.provider.model.IdPMetadata;
import org.wso2.carbon.identity.provider.model.IdentityProvider;
import org.wso2.carbon.identity.provider.model.ProvisioningConfig;
import org.wso2.carbon.identity.provider.internal.dao.IdentityProviderDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default implementation of Identity Provider Service.
 */
public class IdentityProviderServiceImpl implements IdentityProviderService {

    private static final Logger log = LoggerFactory.getLogger(IdentityProviderServiceImpl.class);

    private IdentityProviderDAO identityProviderDAO;

    public void setIdentityProviderDAO(IdentityProviderDAO identityProviderDAO) {
        this.identityProviderDAO = identityProviderDAO;
    }

    @Override
    public List<String> listIdentityProviders() throws IdentityProviderException {
        List<Pair<Integer, String>> identityProviderList = identityProviderDAO.listAllIdentityProviders();
        return identityProviderList.stream()
                .map(pair -> pair.getRight())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listEnabledIdentityProviders() throws IdentityProviderException {
        List<Pair<Integer, String>> identityProviderList = identityProviderDAO.listEnabledIdentityProviders();
        return identityProviderList.stream()
                .map(pair -> pair.getRight())
                .collect(Collectors.toList());
    }

    @Override
    public int createIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException {
        return identityProviderDAO.createIdentityProvider(identityProvider);
    }

    @Override
    public IdentityProvider getIdentityProvider(int identityProviderId) throws IdentityProviderException {
        return identityProviderDAO.getIdentityProvider(identityProviderId);
    }

    @Override
    public IdentityProvider getIdentityProvider(String idPName) throws IdentityProviderException {
        List<IdentityProvider> identityProviderList = identityProviderDAO.listIdentityProviderByName(idPName);

        if (identityProviderList.size() > 0) {
            if (identityProviderList.size() > 1) {
                if (log.isDebugEnabled()) {
                    log.debug("There was {} number of IDP match for the IDP name : {}. Returning the first one",
                            identityProviderList.size(), idPName);
                }
            }
            return identityProviderList.get(0);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Could not find an IDP with name : " + idPName);
            }
        }
        return null;
    }

    @Override
    public IdentityProvider getIdPByUniqueProperty(String name, Object value) throws IdentityProviderException {
        return null;
    }

    @Override
    public IdentityProvider getIdPByUniqueAuthenticatorProperty(String name, Object value) throws IdentityProviderException {
        return null;
    }

    @Override
    public IdentityProvider getIdPByUniqueProvisionerProperty(String name, Object value) throws IdentityProviderException {
        return null;
    }

    @Override
    public void updateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException {

    }

    @Override
    public void deleteIdentityProvider(int identityProviderId) throws IdentityProviderException {
        identityProviderDAO.deleteIdentityProvider(identityProviderId);

    }

    @Override
    public void enableIdentityProvider(int identityProviderId) throws IdentityProviderException {
        identityProviderDAO.enableIdentityProvider(identityProviderId);

    }

    @Override
    public void disableIdentityProvider(int identityProviderId) throws IdentityProviderException {
        identityProviderDAO.disableIdentityProvider(identityProviderId);

    }

    @Override
    public void updateIdPMetadata(int identityProviderId, IdPMetadata metadata) throws IdentityProviderException {
        identityProviderDAO.updateIdentityProviderMetaData(identityProviderId, metadata);

    }

    @Override
    public void updateIdPAuthenticationConfig(int identityProviderId, AuthenticationConfig authenticationConfig)
            throws IdentityProviderException {

    }

    @Override
    public void updateIdPProvisioningConfig(int identityProviderId, ProvisioningConfig provisioningConfig)
            throws IdentityProviderException {

    }

    @Override
    public void updateIdPProperties(int identityProviderId, Map properties) throws IdentityProviderException {

    }
}
