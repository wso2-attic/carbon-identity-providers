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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.osgi.service.jndi.JNDIContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.provider.IdentityProviderService;
import org.wso2.carbon.identity.provider.IdentityProviderServiceImpl;
import org.wso2.carbon.identity.provider.dao.IdentityProviderDAO;
import org.wso2.carbon.identity.provider.dao.JdbcTemplate;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Map;

/**
 * Identity provider service component
 */
@Component(
        name = "identity.provider.dscomponent",
        immediate = true)
public class IdentityProviderServiceComponent {

    private static Logger logger = LoggerFactory.getLogger(IdentityProviderServiceComponent.class);

    private ServiceRegistration<?> identityProviderServiceRegistration;
    private IdentityProviderServiceImpl identityProviderService;
    private JdbcTemplate jdbcTemplate;

    @Activate
    private void activate(ComponentContext componentContext, BundleContext bundleContext, Map<String, ?> properties) {
        identityProviderService = new IdentityProviderServiceImpl();
        identityProviderServiceRegistration = bundleContext
                .registerService(IdentityProviderService.class.getName(), identityProviderService, null);
        if (logger.isDebugEnabled()) {
            logger.debug("Identity Provider Service Bundle Activated.");
        }
    }

    @Deactivate
    private void deactivate(ComponentContext componentContext, BundleContext bundleContext, Map<String, ?> properties,
            int reason) {

        if (identityProviderServiceRegistration != null) {
            identityProviderServiceRegistration.unregister();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Identity Provider Service Bundle Deactivated.");
        }
    }

    @Reference(
            name = "org.wso2.carbon.datasource.jndi",
            service = JNDIContextManager.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "onJNDIUnregister"
    )
    protected void onJNDIReady(JNDIContextManager jndiContextManager) {
        try {
            Context ctx = jndiContextManager.newInitialContext();
            DataSource dsObject = (DataSource)ctx.lookup("java:comp/env/jdbc/WSO2CarbonDB");
            if(dsObject != null) {
                jdbcTemplate = new JdbcTemplate(dsObject);
                initializeDao(jdbcTemplate);
            } else {
                logger.error("Could not find WSO2CarbonDB");
            }
        } catch (NamingException e) {
            logger.error("Error occurred while looking up the Datasource", e);
        }
    }

    protected void onJNDIUnregister(JNDIContextManager jndiContextManager) {
        logger.info("Unregistering data sources");
    }

    private void initializeDao(JdbcTemplate jdbcTemplate) {
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);
        identityProviderService.setIdentityProviderDAO(identityProviderDAO);
    }
}
