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

package org.wso2.carbon.identity.provider;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.wso2.carbon.datasource.core.api.DataSourceService;
import org.wso2.carbon.datasource.core.exception.DataSourceException;
import org.wso2.carbon.identity.provider.utils.OSGiTestUtils;

import javax.inject.Inject;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

/**
 * Test cases for  IdentityProviderService.
 */
@Listeners(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class IdentityProviderServiceOsgiTest {

    @Inject
    BundleContext bundleContext;

    @Inject
    private DataSourceService dataSourceService;

    @Test
    public void testGetIdPs() throws Exception {

    }

    @Test
    public void testGetIdP() throws Exception {

    }

    @Test
    public void testGetIdP1() throws Exception {

    }

    @Configuration
    public Option[] createConfiguration() {
        OSGiTestUtils.setEnv();

        Option[] options = CoreOptions.options(
                CoreOptions.mavenBundle().artifactId("commons-io").groupId("commons-io.wso2").version("2.4.0.wso2v1"),
                mavenBundle().artifactId("HikariCP").groupId("com.zaxxer").version("2.4.1"),
                mavenBundle().artifactId("h2").groupId("com.h2database").version("1.4.191"),
                mavenBundle().artifactId("org.wso2.carbon.datasource.core").groupId("org.wso2.carbon.datasources")
                        .versionAsInProject(),
                mavenBundle().artifactId("org.wso2.carbon.jndi").groupId("org.wso2.carbon.jndi").versionAsInProject(),
                mavenBundle().artifactId("commons-collections").groupId("apache-collections").versionAsInProject(),
                mavenBundle().artifactId("org.wso2.carbon.identity.provider.common")
                        .groupId("org.wso2.carbon.identity.provider").versionAsInProject());
        return OSGiTestUtils.getDefaultPaxOptions(options);
    }

    @Test
    public void testGetAllDataSources() {
        try {
            Object obj = dataSourceService.getDataSource("WSO2_CARBON_DB");
            Assert.assertNotNull(obj, "WSO2_CARBON_DB should exist");
        } catch (DataSourceException e) {
            Assert.fail("Threw an exception while retrieving data sources");
        }
    }

}