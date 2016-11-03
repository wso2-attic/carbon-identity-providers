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

package org.wso2.carbon.identity.provider.dao;

import org.apache.commons.lang3.tuple.Pair;
import org.h2.jdbcx.JdbcConnectionPool;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.provider.internal.dao.IdentityProviderDAO;
import org.wso2.carbon.identity.provider.model.IdPMetadata;
import org.wso2.carbon.identity.provider.model.IdentityProvider;
import org.wso2.carbon.identity.provider.model.ResidentIdentityProvider;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

@Test
public class IdentityProviderDAOTest {

    @Test
    public void testListAllIdentityProviders() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        jdbcTemplate.executeUpdate(
                "INSERT INTO IDP (NAME, DISPLAY_NAME, DESCRIPTION) VALUES ('No Name', 'No Name', 'No Name')");
        List<Pair<Integer, String>> identityProviderList = identityProviderDAO.listAllIdentityProviders();
        assertNotNull(identityProviderList, "The identity Providers should not be null");
        assertEquals(identityProviderList.size(), 1);

    }

    @Test
    public void testListEnabledIdentityProviders() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        jdbcTemplate.executeUpdate(
                "INSERT INTO IDP (NAME, DISPLAY_NAME, DESCRIPTION, IS_ENABLED) VALUES ('No Name', 'No Name', 'No Name', '1')");
        jdbcTemplate.executeUpdate(
                "INSERT INTO IDP (NAME, DISPLAY_NAME, DESCRIPTION, IS_ENABLED) VALUES ('No Name2', 'No Name2', 'No Name2', '0')");
        List<Pair<Integer, String>> identityProviderList = identityProviderDAO.listEnabledIdentityProviders();
        assertNotNull(identityProviderList, "The identity Providers should not be null");
        assertEquals(identityProviderList.size(), 1, "There can be only one enabled Identity providers");
        identityProviderList = identityProviderDAO.listAllIdentityProviders();
        assertEquals(identityProviderList.size(), 2, "However there are 2 Identity providers");
    }

    @Test
    public void testAddIdentityProvider() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        IdentityProvider identityProvider = createIdentityProvider("Test Name", "Test Label", "Test Desc");

        List<Pair<Integer, String>> identityProviderList = identityProviderDAO.listAllIdentityProviders();
        assertEquals(identityProviderList.size(), 0, "There are not IdP initially");

        int idpId = identityProviderDAO.createIdentityProvider(identityProvider);
        assertNotEquals(idpId, 0, "New IDP should have non zero ID");

        identityProviderList = identityProviderDAO.listAllIdentityProviders();
        assertEquals(identityProviderList.size(), 1, "There should be on IdP after adding");
    }

    @Test
    public void testGetIdentityProvider_Int() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        IdentityProvider identityProvider = createIdentityProvider("Test Name", "Test Label", "Test Desc");

        int idpId = identityProviderDAO.createIdentityProvider(identityProvider);

        IdentityProvider identityProvider2 = identityProviderDAO.getIdentityProvider(idpId);

        assertEquals(idpId, identityProvider2.getIdPMetadata().getId(), "Idp ID should match after query");
    }

    @Test
    public void testGetIdentityProvider_String() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        IdentityProvider identityProvider = createIdentityProvider("Test Name", "Test Label", "Test Desc");

        identityProviderDAO.createIdentityProvider(identityProvider);

        IdentityProvider identityProvider2 = identityProviderDAO.getIdentityProvider("Test Name");
        assertEquals("Test Name", identityProvider2.getIdPMetadata().getName(), "Idp Name should match after query");

        IdentityProvider identityProvider3 = identityProviderDAO.getIdentityProvider("Test Name-Not Exists");
        assertNull(identityProvider3, "Non existing record needs to return null");
    }

    @Test
    public void testDisableIdentityProvider() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        IdentityProvider identityProvider = createIdentityProvider("TestName2", "Test Label", "Test Desc");
        int idpId = identityProviderDAO.createIdentityProvider(identityProvider);

        identityProviderDAO.disableIdentityProvider(idpId);

        IdentityProvider identityProvider2 = identityProviderDAO.getIdentityProvider("TestName2");
        assertFalse(identityProvider2.isEnabled(), "Identity provider should have been disabled.");
    }

    @Test
    public void testDeleteIdentityProvider_String() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        identityProviderDAO.deleteIdentityProvider("Test Name");

        IdentityProvider identityProvider = identityProviderDAO.getIdentityProvider("Test Name");
        assertNull(identityProvider, "Non existing record needs to return null");
    }

    @Test
    public void testDeleteIdentityProvider_int() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        identityProviderDAO.deleteIdentityProvider(1);

        IdentityProvider identityProvider = identityProviderDAO.getIdentityProvider("Test Name");
        assertNull(identityProvider, "Non existing record needs to return null");
    }

    @Test
    public void testUpdateMetaData() throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        IdentityProviderDAO identityProviderDAO = new IdentityProviderDAO();
        identityProviderDAO.setJdbcTemplate(jdbcTemplate);

        IdentityProvider identityProvider = createIdentityProvider("TestName2", "Test Label", "Test Desc");
        int idpId = identityProviderDAO.createIdentityProvider(identityProvider);

        identityProviderDAO.updateIdentityProviderMetaData(idpId,
                createIdentityProviderMetaData("TestName2", "Test Label", "Test Desc", false, "Test Realm Id"));

        IdentityProvider identityProvider2 = identityProviderDAO.getIdentityProvider("TestName2");
        assertFalse(identityProvider2.getIdPMetadata().isFederationHub(),
                "Identity provider should not be a federation hub.");
        //ToDO - check update for all parameters
//        assertEquals(identityProvider2.getIdPMetadata().getHomeRealmId(), "Test Realm Id",
//                "Identity provider home realm id is not updated.");

    }

    private IdentityProvider createIdentityProvider(String name, String label, String description) {
        IdentityProvider.IdentityProviderBuilder identityProviderBuilder = ResidentIdentityProvider.newBuilder(0, name);
        identityProviderBuilder.setDialectId(1).setDisplayLabel(label).setDescription(description);

        return identityProviderBuilder.build();
    }

    private IdPMetadata createIdentityProviderMetaData(String name, String label, String description,
            Boolean isFederationHub, String homeRealmId) {

        IdPMetadata.IdPMetadataBuilder idPMetadataBuilder = new IdPMetadata.IdPMetadataBuilder(name);
        idPMetadataBuilder.setDisplayLabel(label).setDescription(description).setIsFederationHub(isFederationHub)
                .setHomeRealmId(homeRealmId);

        return idPMetadataBuilder.build();

    }

    private JdbcTemplate getJdbcTemplate() {
        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");

        try (InputStream databaseInputStream = this.getClass().getClassLoader().getResourceAsStream("dbscripts/h2.sql");
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