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
import org.wso2.carbon.identity.provider.common.model.IdPMetadata;
import org.wso2.carbon.identity.provider.common.model.IdentityProvider;
import org.wso2.carbon.identity.provider.common.model.ResidentIdentityProvider;
import org.wso2.carbon.identity.provider.dao.JdbcTemplate;


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
     * @param identityProvider The IdP to be added.
     * @return the ID of the newly inserted Identity provider.
     * @throws IdentityProviderException when any database level exception occurs.
     */
    public int createIdentityProvider(IdentityProvider identityProvider) throws IdentityProviderException {
        final String INSERT_IDP_SQL = "INSERT INTO IDP (NAME, DISPLAY_NAME, DESCRIPTION) "
                + "VALUES(?,?,?)";

        int insertedId = this.jdbcTemplate.executeInsert(INSERT_IDP_SQL, (preparedStatement, bean) -> {
            IdPMetadata idPMetadata = identityProvider.getIdPMetadata();
            preparedStatement.setString(1, idPMetadata.getName());
            preparedStatement.setString(2, idPMetadata.getDisplayLabel());
            preparedStatement.setString(3, idPMetadata.getDescription());

        }, identityProvider, true);

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

        List<Pair<Integer, String>> idpList = this.jdbcTemplate.executeQuery(GET_ALL_IDP_SQL, (resultSet, rowNumber) ->
                ImmutablePair.of(resultSet.getInt(1), resultSet.getString(2))
        );

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

        List<Pair<Integer, String>> idpList = this.jdbcTemplate.executeQuery(GET_ALL_IDP_SQL,
                (resultSet, rowNumber) -> ImmutablePair.of(resultSet.getInt(1), resultSet.getString(2)),
                (preparedStatement -> preparedStatement.setBoolean(1, true))
        );

        return idpList;
    }

    public IdentityProvider getIdentityProvider(int identityProviderId) {
        final String GET_ALL_IDP_SQL = "SELECT ID, NAME, DISPLAY_NAME, DESCRIPTION, "
                + "IS_FEDERATION_HUB, IS_LOCAL_CLAIM_DIALECT, IS_ENABLED, ID FROM IDP WHERE ID=?";

        IdentityProvider identityProvider = this.jdbcTemplate.fetchSingleRecord(GET_ALL_IDP_SQL, (resultSet, rowNumber) -> {
            IdentityProvider.IdentityProviderBuilder identityProviderBuilder = ResidentIdentityProvider
                    .newBuilder(resultSet.getInt("ID"), resultSet.getString("NAME"));
            identityProviderBuilder.build();
            return identityProviderBuilder.build();
        },(preparedStatement) -> {
            preparedStatement.setInt(1, identityProviderId);
        });

        return identityProvider;
    }

    public IdentityProvider getIdentityProvider(String identityProviderName) {
        final String GET_ALL_IDP_SQL = "SELECT ID, NAME, DISPLAY_NAME, DESCRIPTION, "
                + "IS_FEDERATION_HUB, IS_LOCAL_CLAIM_DIALECT, IS_ENABLED, ID FROM IDP WHERE NAME=?";

        IdentityProvider identityProvider = this.jdbcTemplate.fetchSingleRecord(GET_ALL_IDP_SQL, (resultSet, rowNumber) -> {
            IdentityProvider.IdentityProviderBuilder identityProviderBuilder = ResidentIdentityProvider
                    .newBuilder(resultSet.getInt("ID"), resultSet.getString("NAME"));
            identityProviderBuilder.build();
            return identityProviderBuilder.build();
        },(preparedStatement) -> {
            preparedStatement.setString(1, identityProviderName);
        });

        return identityProvider;
    }

    public List<IdentityProvider> listIdentityProviderByName(String identityProviderName) {
        return null;
    }
}
