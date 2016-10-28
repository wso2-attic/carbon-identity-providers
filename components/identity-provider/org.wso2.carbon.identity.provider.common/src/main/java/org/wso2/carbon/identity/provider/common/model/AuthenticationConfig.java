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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class AuthenticationConfig implements Serializable {

    private static final long serialVersionUID = -2113099200158633460L;

    private Collection<String> requestedClaims = new HashSet<String>();
    private Collection<AuthenticatorConfig> authenticators = new HashSet<>();

    private AuthenticationConfig(AuthenticationConfigBuilder builder) {
        this.requestedClaims = builder.requestedClaims;
        this.authenticators = builder.authenticators;
    }

    public Collection<String> getRequestedClaims() {
        return Collections.unmodifiableCollection(requestedClaims);
    }

    public Collection<AuthenticatorConfig> getAuthenticators() {
        return authenticators;
    }

    static class AuthenticationConfigBuilder {

        private Collection<String> requestedClaims = new HashSet<>();
        private Collection<AuthenticatorConfig> authenticators = new HashSet<>();

        AuthenticationConfigBuilder setRequestedClaims(Collection<String> requestedClaims) {
            if (!requestedClaims.isEmpty()) {
                this.requestedClaims = new HashSet<String>(requestedClaims);
            }
            return this;
        }

        AuthenticationConfigBuilder addRequestedClaim(String requestedClaim) {
            if (requestedClaims != null) {
                this.requestedClaims.add(requestedClaim);
            }
            return this;
        }

        AuthenticationConfigBuilder addRequestedClaims(Collection<String> requestedClaims) {
            if (!requestedClaims.isEmpty()) {
                this.requestedClaims.addAll(requestedClaims);
            }
            return this;
        }

        AuthenticationConfigBuilder setAuthenticators(Collection<AuthenticatorConfig> authenticators) {
            if (!authenticators.isEmpty()) {
                this.authenticators = new HashSet<AuthenticatorConfig>(authenticators);
            }
            return this;
        }

        AuthenticationConfigBuilder addAuthenticator(AuthenticatorConfig authenticator) {
            if (authenticator != null) {
                this.authenticators.add(authenticator);
            }
            return this;
        }

        AuthenticationConfigBuilder addAuthenticators(Collection<AuthenticatorConfig> authenticators) {
            if (!authenticators.isEmpty()) {
                this.authenticators.addAll(authenticators);
            }
            return this;
        }

        AuthenticationConfig build() {
            return new AuthenticationConfig(this);
        }
    }
}
