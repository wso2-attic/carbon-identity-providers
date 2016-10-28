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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.provider.common.model.IdPMetadata;
import org.wso2.carbon.identity.provider.common.model.AuthenticationConfig;
import org.wso2.carbon.identity.provider.common.model.IdentityProvider;
import org.wso2.carbon.identity.provider.common.model.ProvisioningConfig;
import org.wso2.carbon.identity.provider.dao.IdentityProviderDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default implementation of Identity Prvider Service.
 */
public class IdentityProviderServiceImpl implements IdentityProviderService {

    private static final Logger log = LoggerFactory.getLogger(IdentityProviderServiceImpl.class);

    private IdentityProviderDAO identityProviderDAO;

    public void setIdentityProviderDAO(IdentityProviderDAO identityProviderDAO) {
        this.identityProviderDAO = identityProviderDAO;
    }

    @Override
    public List<String> listIdentityProviders() throws IdentityProviderException {
        return null;
    }

    @Override
    public List<String> listEnabledIdentityProviders() throws IdentityProviderException {
        List<IdentityProvider> identityProviderList = identityProviderDAO.listIdentityProviders(true);

        return identityProviderList.stream().map(
                identityProvider -> identityProvider.getIdPMetadata().getName())
                .collect(
                    Collectors.toList()
        );
    }

    @Override
    public int createIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException {
        return 0;
    }

    @Override
    public IdentityProvider getIdentityProvider(int identityProviderId) throws IdentityProviderException {
        return null;
    }

    @Override
    public IdentityProvider getIdentityProvider(String idPName) throws IdentityProviderException {
        List<IdentityProvider> identityProviderList =  identityProviderDAO.listIdentityProviderByName(idPName);

        if(identityProviderList.size() > 0) {
            if(identityProviderList.size() >1) {
                if(log.isDebugEnabled()) {
                    log.debug("There was {} number of IDP match for the IDP name : {}. Returning the first one",
                            identityProviderList.size(), idPName);
                }
            }
            return identityProviderList.get(0);
        } else {
            if(log.isDebugEnabled()) {
                log.debug("Could not find an IDP with name : "+idPName);
            }
        }
        return null;
    }

    @Override
    public IdentityProvider getIdPByProperty(String name, Object value) throws IdentityProviderException {
        return null;
    }

    @Override
    public IdentityProvider getIdPByAuthenticatorProperty(String name, Object value) throws IdentityProviderException {
        return null;
    }

    @Override
    public IdentityProvider getIdPByProvisionerProperty(String name, Object value) throws IdentityProviderException {
        return null;
    }

    @Override
    public void updateIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException {

    }

    @Override
    public void deleteIdentityProvider(int identityProviderId) throws IdentityProviderException {

    }

    @Override
    public void enableIdentityProvider(int identityProviderId) throws IdentityProviderException {

    }

    @Override
    public void disableIdentityProvider(int identityProviderId) throws IdentityProviderException {

    }

    @Override
    public void updateIdPMetadata(int identityProviderId, IdPMetadata metadata) throws IdentityProviderException {

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
