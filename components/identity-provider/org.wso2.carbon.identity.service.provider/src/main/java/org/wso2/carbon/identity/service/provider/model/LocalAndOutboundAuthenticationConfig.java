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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalAndOutboundAuthenticationConfig implements Serializable {

    //    private static final String USE_USERSTORE_DOMAIN_IN_USERNAME = "UseUserstoreDomainInUsername";
    //    private static final String USE_TENANT_DOMAIN_IN_USERNAME = "UseTenantDomainInUsername";
    //    private static final String SUBJECT_CLAIM_URI = "subjectClaimUri";
    //    private static final String ALWAYS_SEND_BACK_AUTHENTICATED_LIST_OF_ID_PS = "alwaysSendBackAuthenticatedListOfIdPs";
    //    private static final String AUTHENTICATION_STEP_FOR_ATTRIBUTES = "AuthenticationStepForAttributes";
    //    private static final String AUTHENTICATION_STEP_FOR_SUBJECT = "AuthenticationStepForSubject";
    //    private static final String AUTHENTICATION_STEPS = "AuthenticationSteps";

    private List<AuthenticationStep> authenticationSteps = new ArrayList<>();

    /**
     * Send the authenticated IDP list for the current request back to the caller.
     * e.g. JWT (TODO: Explain)
     */
    private boolean sendAuthenticatedIdPs;

    /**
     * Return "Domain"/"User" for the subject instead of "User" as the subject.
     */
    private boolean useDomainInLocalSubjectIdentifier = false;

    /**
     * Use the Local subject ID instead of IdP provided subject ID.
     */
    private boolean useLocalSubjectIdentifier = true;

}