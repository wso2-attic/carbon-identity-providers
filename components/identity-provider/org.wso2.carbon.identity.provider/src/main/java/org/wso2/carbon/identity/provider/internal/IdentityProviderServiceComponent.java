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

package org.wso2.carbon.identity.provider.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.wso2.carbon.identity.provider.IdentityProviderService;
import org.wso2.carbon.identity.provider.IdentityProviderServiceImpl;

import java.util.Dictionary;
import java.util.Hashtable;

@Component(name = "org.wso2.carbon.identity.provider.IdentityProviderServiceComponent",
           service = {IdentityProviderService.class},
           immediate = true)
public class IdentityProviderServiceComponent {

    private static Logger log = LoggerFactory.getLogger(IdentityProviderServiceComponent.class);
    private BundleContext bundleContext;

    /**
     * Get called when this osgi component get registered.
     *
     * @param bundleContext Context of the osgi component.
     */
    @Activate
    protected void activate(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
        log.debug("Identity Provider Service Component activated.");

        Dictionary<String, String> dictionary = new Hashtable<>();
        bundleContext.registerService(IdentityProviderService.class, new IdentityProviderServiceImpl(), dictionary);
    }

    /**
     * Get called when this osgi component get unregistered.
     */
    @Deactivate
    protected void deactivate() {
        this.bundleContext = null;
        log.debug("Identity Provider Service Component deactivated.");
    }


}
