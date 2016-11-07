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

package org.wso2.carbon.identity.service.provider.internal.dao;

import org.h2.jdbcx.JdbcConnectionPool;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.provider.dao.JdbcTemplate;
import org.wso2.carbon.identity.provider.internal.dao.IdentityProviderDAO;
import org.wso2.carbon.identity.provider.model.IdentityProvider;
import org.wso2.carbon.identity.service.provider.model.ServiceProvider;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class ServiceProviderDAOTest {

    @Test
    public void testListAllServiceProviders() throws Exception {

    }

    @Test
    public void testListEnabledServiceProviders() throws Exception {

    }

    @Test
    public void testCreateServiceProvider() throws Exception {

    }

    @Test
    public void testGetServiceProvider() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO();
        serviceProviderDAO.setJdbcTemplate(jdbcTemplate);

        jdbcTemplate.executeUpdate(
                "INSERT INTO SP (NAME, DISPLAY_NAME, DESCRIPTION, IS_ENABLED) VALUES ('No Name', 'No Name', 'No Name', '1')");


        ServiceProvider serviceProvider = serviceProviderDAO.getServiceProvider(1);

        assertNotNull(serviceProvider);
    }

    /**
     * We need to make these common test class.
     * @return
     */
    private JdbcTemplate getJdbcTemplate() {
        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");

        try (InputStream databaseInputStream = this.getClass().getClassLoader().getResourceAsStream("dbscripts/sp/h2.sql");
                Connection conn = ds.getConnection();
                Statement statement = conn.createStatement()) {
            statement.execute("DROP ALL OBJECTS");
            String sql = read(databaseInputStream);
            statement.executeUpdate(sql);
        } catch (SQLException | IOException e) {
            fail("Could not create in-memory h2 database", e);
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate;
    }

    private String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

}