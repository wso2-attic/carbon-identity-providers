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

package org.wso2.carbon.identity.provider.internal.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.provider.IdentityProviderException;
import org.wso2.carbon.identity.provider.dao.DataAccessException;
import org.wso2.carbon.identity.provider.dao.JdbcTemplate;
import org.wso2.carbon.identity.provider.model.AuthenticationConfig;
import org.wso2.carbon.identity.provider.model.FederatedIdentityProvider;
import org.wso2.carbon.identity.provider.model.IdPMetadata;
import org.wso2.carbon.identity.provider.model.IdentityProvider;
import org.wso2.carbon.identity.provider.model.ProvisioningConfig;
import org.wso2.carbon.identity.provider.model.ResidentIdentityProvider;

import java.util.List;

/**
 * Data Access Object to the data storage to retrieve and store identity provider and related configurations.
 */
public class IdentityProviderDAO {

    private static final Logger log = LoggerFactory.getLogger(IdentityProviderDAO.class);

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Adds an identity provider to the persistent store.
     *
     * @param identityProvider The IdP to be added.
     * @return the ID of the newly inserted Identity provider.
     * @throws IdentityProviderException when any database level exception occurs.
     */
    public int createIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException {
        final String INSERT_IDP_SQL = "INSERT INTO IDP (NAME, DISPLAY_NAME, DESCRIPTION) " + "VALUES(?,?,?)";

        int insertedId = 0;
        try {
            insertedId = this.jdbcTemplate.executeInsert(INSERT_IDP_SQL, (preparedStatement) -> {
                IdPMetadata idPMetadata = identityProvider.getIdPMetadata();
                preparedStatement.setString(1, idPMetadata.getName());
                preparedStatement.setString(2, idPMetadata.getDisplayLabel());
                preparedStatement.setString(3, idPMetadata.getDescription());

            }, identityProvider, true);
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred in inserting new Identity Provider with name " + identityProvider.getIdPMetadata()
                            .getName(), e);
        }

        return insertedId;
    }

    /**
     * Lists the ID and name of all identity providers.
     *
     * @return
     * @throws IdentityProviderException
     */
    public List<Pair<Integer, String>> listAllIdentityProviders() throws IdentityProviderException {

        final String GET_ALL_IDP_SQL = "SELECT ID, NAME FROM IDP";

        List<Pair<Integer, String>> idpList = null;
        try {
            idpList = this.jdbcTemplate.executeQuery(GET_ALL_IDP_SQL,
                    (resultSet, rowNumber) -> ImmutablePair.of(resultSet.getInt(1), resultSet.getString(2)));
        } catch (DataAccessException e) {
            throw new IdentityProviderException("Error occurred in listing all the Identity providers ", e);
        }

        return idpList;
    }

    /**
     * Lists the ID and name of all identity providers.
     *
     * @return list of Pair of {ID, Name} of identity provider
     * @throws IdentityProviderException
     */
    public List<Pair<Integer, String>> listEnabledIdentityProviders() throws IdentityProviderException {

        final String GET_ALL_IDP_SQL = "SELECT ID, NAME FROM IDP WHERE IS_ENABLED=?";

        List<Pair<Integer, String>> idpList = null;
        try {
            idpList = this.jdbcTemplate.executeQuery(GET_ALL_IDP_SQL,
                    (resultSet, rowNumber) -> ImmutablePair.of(resultSet.getInt(1), resultSet.getString(2)),
                    (preparedStatement -> preparedStatement.setBoolean(1, true)));
        } catch (DataAccessException e) {
            throw new IdentityProviderException("Error occurred in listing 'Enabled' the Identity providers ", e);
        }

        return idpList;
    }

    public IdentityProvider getIdentityProvider(int identityProviderId) throws IdentityProviderException {
        final String GET_ALL_IDP_SQL = "SELECT ID, NAME, DISPLAY_NAME, DESCRIPTION, "
                + "IS_FEDERATION_HUB, IS_LOCAL_CLAIM_DIALECT, IS_ENABLED, ID FROM IDP WHERE ID=?";

        IdentityProvider identityProvider = null;
        try {
            identityProvider = this.jdbcTemplate.fetchSingleRecord(GET_ALL_IDP_SQL, (resultSet, rowNumber) -> {
                IdentityProvider.IdentityProviderBuilder identityProviderBuilder = ResidentIdentityProvider
                        .newBuilder(resultSet.getInt("ID"), resultSet.getString("NAME"));
                return identityProviderBuilder.build();
            }, (preparedStatement) -> {
                preparedStatement.setInt(1, identityProviderId);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred retrieving the Identity provider by the given ID: " + identityProviderId, e);
        }

        return identityProvider;
    }

    public IdentityProvider getIdentityProvider(String identityProviderName) throws IdentityProviderException {
        final String GET_ALL_IDP_SQL = "SELECT ID, NAME, DISPLAY_NAME, DESCRIPTION, "
                + "IS_FEDERATION_HUB, IS_LOCAL_CLAIM_DIALECT, IS_ENABLED, HOME_REALM_ID FROM IDP WHERE NAME=?";

        IdentityProvider identityProvider = null;
        try {
            identityProvider = this.jdbcTemplate.fetchSingleRecord(GET_ALL_IDP_SQL, (resultSet, rowNumber) -> {
                IdentityProvider.IdentityProviderBuilder identityProviderBuilder = FederatedIdentityProvider
                        .newBuilder(resultSet.getInt("ID"), resultSet.getString("NAME"))
                        .setDisplayLabel(resultSet.getString("DISPLAY_NAME"))
                        .setDescription(resultSet.getString("DESCRIPTION"))
                        .setIsFederationHub(resultSet.getBoolean("IS_FEDERATION_HUB"))
                        .setEnabled(resultSet.getBoolean("IS_ENABLED"))
                        .setHomeRealmId(resultSet.getString("HOME_REALM_ID"));
                return identityProviderBuilder.build();
            }, (preparedStatement) -> {
                preparedStatement.setString(1, identityProviderName);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred retrieving the Identity provider by the given Name: " + identityProviderName, e);
        }

        return identityProvider;
    }

    public List<IdentityProvider> listIdentityProviderByName(String identityProviderName) {
        return null;
    }

    public void deleteIdentityProvider(int identityProviderId) throws IdentityProviderException {
        final String DELETE_IDP_SQL = "DELETE FROM IDP WHERE ID=?";
        try {
            this.jdbcTemplate.executeUpdate(DELETE_IDP_SQL, preparedStatement -> {
                preparedStatement.setInt(1, identityProviderId);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred deleting the Identity provider by the given ID: " + identityProviderId, e);
        }
    }

    public void deleteIdentityProvider(String identityProviderName) throws IdentityProviderException {
        final String DELETE_IDP_SQL = "DELETE FROM IDP WHERE NAME=?";
        try {
            this.jdbcTemplate.executeUpdate(DELETE_IDP_SQL, (preparedStatement -> {
                preparedStatement.setString(1, identityProviderName);
            }));
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred deleting the Identity provider by the given ID: " + identityProviderName, e);
        }
    }

    public void enableIdentityProvider(int identityProviderId) throws IdentityProviderException {
        final String ENABLE_IDP_SQL = "UPDATE IDP SET IS_ENABLED=? WHERE ID=?";
        try {
            this.jdbcTemplate.executeUpdate(ENABLE_IDP_SQL, preparedStatement -> {
                preparedStatement.setString(1, "1");
                preparedStatement.setInt(2, identityProviderId);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred \"Enabling\" the Identity provider by the given ID: " + identityProviderId, e);
        }
    }

    public void disableIdentityProvider(int identityProviderId) throws IdentityProviderException {
        final String DISABLE_IDP_SQL = "UPDATE IDP SET IS_ENABLED=? WHERE ID=?";
        try {
            this.jdbcTemplate.executeUpdate(DISABLE_IDP_SQL, preparedStatement -> {
                preparedStatement.setString(1, "0");
                preparedStatement.setInt(2, identityProviderId);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred \"Disabling\" the Identity provider by the given ID: " + identityProviderId, e);
        }

    }

    public void enableIdentityProvider(String identityProviderName) throws IdentityProviderException {
        final String ENABLE_IDP_SQL = "UPDATE IDP SET IS_ENABLED=? WHERE ID=?";
        try {
            this.jdbcTemplate.executeUpdate(ENABLE_IDP_SQL, preparedStatement -> {
                preparedStatement.setString(1, "1");
                preparedStatement.setString(1, identityProviderName);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred \"Enabling\" the Identity provider by the given Name: " + identityProviderName, e);
        }
    }

    public void disableIdentityProvider(String identityProviderName) throws IdentityProviderException {
        final String DISABLE_IDP_SQL = "UPDATE IDP SET IS_ENABLED=? WHERE ID=?";
        try {
            this.jdbcTemplate.executeUpdate(DISABLE_IDP_SQL, preparedStatement -> {
                preparedStatement.setString(1, "0");
                preparedStatement.setString(1, identityProviderName);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred \"Disabling\" the Identity provider by the given Name: " + identityProviderName, e);
        }

    }

    public String getIdPNameById(int idpId) throws IdentityProviderException {
        final String GET_IDP_NAME_BY_ID_SQL = "SELECT NAME FROM IDP WHERE ID=?";

        String identityProviderName = null;
        try {
            identityProviderName = this.jdbcTemplate
                    .fetchSingleRecord(GET_IDP_NAME_BY_ID_SQL, (resultSet, rowNumber) -> resultSet.getString("NAME"),
                            (preparedStatement -> preparedStatement.setInt(1, idpId)));
        } catch (DataAccessException e) {
            throw new IdentityProviderException("Error retrieving the Identity provider Name by the given ID: " + idpId,
                    e);
        }

        return identityProviderName;

    }

    public int getIdPIdByName(String idpName) throws IdentityProviderException {
        final String GET_IDP_ID_BY_NAME_SQL = "SELECT ID FROM IDP WHERE NAME=?";

        int identityProviderId = 0;
        try {
            identityProviderId = this.jdbcTemplate
                    .fetchSingleRecord(GET_IDP_ID_BY_NAME_SQL, (resultSet, rowNumber) -> resultSet.getInt("Id"),
                            (preparedStatement -> preparedStatement.setString(1, idpName)));
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error retrieving the Identity provider ID by the given Name: " + idpName, e);
        }

        return identityProviderId;
    }

    public void updateIdentityProviderMetaData(int identityProviderId, IdPMetadata idPMetadata)
            throws IdentityProviderException {
        final String UPDATE_IDP_METADATA_SQL = "UPDATE IDP SET NAME=? , DISPLAY_NAME=? , DESCRIPTION=? , "
                + "HOME_REALM_ID=? , IS_FEDERATION_HUB=? WHERE ID=?";

        try {
            this.jdbcTemplate.executeUpdate(UPDATE_IDP_METADATA_SQL, preparedStatement -> {
                preparedStatement.setString(1, idPMetadata.getName());
                preparedStatement.setString(2, idPMetadata.getDisplayLabel());
                preparedStatement.setString(3, idPMetadata.getDescription());
                preparedStatement.setString(4, idPMetadata.getHomeRealmId());
                preparedStatement.setString(5, idPMetadata.isFederationHub() ? "1" : "0");
                preparedStatement.setInt(6, identityProviderId);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred updating meta data for the Identity provider by the given Name: " + idPMetadata
                            .getName(), e);
        }

    }

    //ToDo after deciding the deployment model (file based or database based)
    public void updateupdateIdPAuthenticationConfig(int identityProviderId, AuthenticationConfig authenticationConfig)
            throws IdentityProviderException {
//        authenticationConfig.getRequestedClaims();
//        authenticationConfig.getAuthenticators();
        final String UPDATE_IDP_METADATA_SQL = "UPDATE IDP SET NAME=? , DISPLAY_NAME=? , DESCRIPTION=? , "
                + "HOME_REALM_ID=? , IS_FEDERATION_HUB=? WHERE ID=?";

        try {
            this.jdbcTemplate.executeUpdate(UPDATE_IDP_METADATA_SQL, preparedStatement -> {
                preparedStatement.setInt(1, identityProviderId);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred updating meta data for the Identity provider by the given ID: "
                            + identityProviderId, e);
        }

    }

    //ToDO after deciding the deployment model (file based or database based)
    public void updateIdPProvisioningConfig(int identityProviderId, ProvisioningConfig provisioningConfig)
            throws IdentityProviderException {

    }

    //ToDO after deciding the deployment model (file based or database based)
    public IdentityProvider getIdPByAuthenticatorProperty(String key, String value) throws IdentityProviderException{
        final String GET_IDP_BY_AUTHENTICATOR_PROPERTY = "SELECT idp.ID, idp.NAME, idp.IS_PRIMARY, " +
                "idp.HOME_REALM_ID, " +
                "idp.CERTIFICATE, idp.ALIAS, idp.INBOUND_PROV_ENABLED, idp.INBOUND_PROV_USER_STORE_ID, " +
                "idp.USER_CLAIM_URI, " +
                "idp.ROLE_CLAIM_URI, idp.DEFAULT_AUTHENTICATOR_NAME, idp.DEFAULT_PRO_CONNECTOR_NAME, " +
                "idp.DESCRIPTION, " +
                "idp.IS_FEDERATION_HUB, idp.IS_LOCAL_CLAIM_DIALECT, idp.PROVISIONING_ROLE, idp.IS_ENABLED, " +
                "idp.DISPLAY_NAME " +
                "FROM IDP idp INNER JOIN  IDP_AUTHENTICATOR idp_auth ON idp.ID = idp_auth.IDP_ID INNER JOIN " +
                "IDP_AUTHENTICATOR_PROPERTY idp_auth_pro ON idp_auth.ID = idp_auth_pro.AUTHENTICATOR_ID " +
                "WHERE  idp_auth_pro.PROPERTY_KEY =?  AND idp_auth_pro.PROPERTY_VALUE = ? ";

        IdentityProvider identityProvider = null;
        try {
            identityProvider = this.jdbcTemplate.fetchSingleRecord(GET_IDP_BY_AUTHENTICATOR_PROPERTY, (resultSet, rowNumber) -> {
                IdentityProvider.IdentityProviderBuilder identityProviderBuilder = ResidentIdentityProvider
                        .newBuilder(resultSet.getInt("ID"), resultSet.getString("NAME"));
                return identityProviderBuilder.build();
            }, (preparedStatement) -> {
                preparedStatement.setString(1, key);
                preparedStatement.setString(2, value);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred retrieving the Identity provider by key: " + key + " value:" + value, e);
        }

        return identityProvider;
    }
}
