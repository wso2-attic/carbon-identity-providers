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

import org.apache.commons.lang3.tuple.Pair;
import org.wso2.carbon.identity.provider.IdentityProviderException;
import org.wso2.carbon.identity.provider.dao.DataAccessException;
import org.wso2.carbon.identity.provider.dao.JdbcTemplate;
import org.wso2.carbon.identity.service.provider.model.ServiceProvider;

import java.util.Collections;
import java.util.List;

/**
 * Data Access Object for Service Provider.
 */
public class ServiceProviderDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pair<Integer, String>> listAllServiceProviders() throws IdentityProviderException {
        return Collections.emptyList();
    }

    public List<Pair<Integer, String>> listEnabledServiceProviders() throws IdentityProviderException {
        return Collections.emptyList();
    }

    /**
     * Persists the Service provider and return the ID for the persisted Service Provider.
     *
     * @param serviceProvider SP to be saved.
     * @return the persisted ID of the Service provider.
     * @throws IdentityProviderException
     */
    public int createServiceProvider(ServiceProvider serviceProvider) throws IdentityProviderException {
        final String insertSpSql = "INSERT INTO SP (NAME, DISPLAY_NAME, DESCRIPTION) " + "VALUES(?,?,?)";

        int insertedId = 0;
        try {
            insertedId = this.jdbcTemplate.executeInsert(insertSpSql, (preparedStatement) -> {
                preparedStatement.setString(1, serviceProvider.getApplicationName());
                preparedStatement.setString(2, serviceProvider.getDisplayLabel());
                preparedStatement.setString(3, serviceProvider.getDescription());

            }, serviceProvider, true);
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred in creating new the Service provider with name : " + serviceProvider
                            .getApplicationName(), e);
        }

        return insertedId;
    }

    public ServiceProvider getServiceProvider(int serviceProviderId) throws IdentityProviderException {
        final String listAllSpSql = "SELECT ID, NAME, DISPLAY_NAME, DESCRIPTION " + "FROM SP WHERE ID=?";

        ServiceProvider serviceProvider = null;
        try {
            serviceProvider = this.jdbcTemplate.fetchSingleRecord(listAllSpSql, (resultSet, rowNumber) -> {
                ServiceProvider.ServiceProviderBuilder serviceProviderBuilder = ServiceProvider.newBuilder()
                        .withApplicationName(resultSet.getString("NAME"))
                        .withDescription(resultSet.getString("DESCRIPTION"))
                        .withDisplayLabel(resultSet.getString("DISPLAY_NAME"));
                return serviceProviderBuilder.build();
            }, (preparedStatement) -> {
                preparedStatement.setInt(1, serviceProviderId);
            });
        } catch (DataAccessException e) {
            throw new IdentityProviderException(
                    "Error occurred in retrieving the Service provider with ID : " + serviceProviderId, e);
        }

        return serviceProvider;
    }
}
