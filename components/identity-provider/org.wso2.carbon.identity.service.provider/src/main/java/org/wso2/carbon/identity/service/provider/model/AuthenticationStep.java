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

import org.wso2.carbon.identity.provider.model.IdentityProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Authenticator flow runs in number of steps. This AuthenticationStep provides the configuration for one step
 * in the authentication flow.
 */
public class AuthenticationStep implements Serializable {

    public enum AuthStrategy {
        REQUEST_PATH_ONLY,
        SHOW_AUTH_PAGE,
        REQUEST_PATH_OR_SHOW_AUTH,
        SHOW_AUTH_WHEN_REQUEST_PATH_FAIL
    }

    private AuthStrategy authStrategy;

    /**
     * List of local authenticator configurations for this step.
     */
    private List<LocalAuthenticatorConfig> localAuthenticatorConfigs = new ArrayList<>();

    /**
     * List of the federated Identity Providers used in this step.
     */
    private List<IdentityProvider> federatedIdentityProviders = new ArrayList<>();

//    /**
//     * Signifies that this step supplies the "Subject" to the authentication flow.
//     */
//    private boolean subjectIdentifierStep;
//
//    /**
//     * Signifies that this step supplies all the attributes to the authentication flow.
//     */
//    private boolean attributeStep;

}
