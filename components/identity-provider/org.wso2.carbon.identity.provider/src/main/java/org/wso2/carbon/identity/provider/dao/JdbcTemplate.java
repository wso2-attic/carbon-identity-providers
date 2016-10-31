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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to call JDBC with lambda expressions.
 */
public class JdbcTemplate {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Executes a query on JDBC and return the result as a list of domain objects.
     *
     * @param query the SQL query with the parameter placeholders.
     * @param rowMapper Row mapper functional interface
     * @param params parameters for the SQL query parameter replacement.
     * @return List of domain objects of required type.
     */
    public <T extends Object> List<T> executeQuery(String query, RowMapper<T> rowMapper, Object... params) {
        List<T> result = new ArrayList();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                T row = rowMapper.mapRow(resultSet, i);
                result.add(row);
                i++;
            }
        } catch (SQLException e) {
            logger.error("Error in performing Database query: " + query + "\n parameters " + params);
        }
        return result;
    }

    /**
     * Executes a query on JDBC and return the result as a domain object.
     *
     * @param query the SQL query with the parameter placeholders.
     * @param rowMapper Row mapper functional interface
     * @param filter parameters for the SQL query parameter replacement.
     * @return domain object of required type.
     */
    public <T extends Object> T fetchSingleRecord(String query, RowMapper<T> rowMapper, QueryFilter filter) {
        T result = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if(filter != null) {
                filter.filter(preparedStatement);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = rowMapper.mapRow(resultSet, 0);
            }
            if(resultSet.next()) {
                logger.error("There are more records than one found for query: {} , \n parameters: {}", query, filter);
            }
        } catch (SQLException e) {
            logger.error("Error in performing Database query: {} :\n {}parameters", query, filter);
        }
        return result;
    }

    public void executeUpdate(String query, Object... params) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (params != null) {
                int i = 1;

            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in performing Database update: " + query + "\n params " + params);
        }
    }

    /**
     * Executes the jdbc insert/update query.
     *
     * @param query The SQL for insert/update.
     * @param rowExtractor Domain object (bean) to prepared statement parameter binding.
     * @param bean the Domain object to be inserted/updated.
     * @param <T>
     */
    public <T extends Object> void executeUpdate(String query, RowExtractor<T> rowExtractor, T bean) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            doInternalUpdate(rowExtractor, bean, preparedStatement);
        } catch (SQLException e) {
            logger.error("Error in performing Database update: " + query + "\n bean " + bean);
        }
    }

    private <T extends Object> void doInternalUpdate(RowExtractor<T> rowExtractor, T bean,
            PreparedStatement preparedStatement) throws SQLException {
        if (bean != null && rowExtractor != null) {
            rowExtractor.extract(preparedStatement, bean);
        } else {
            logger.error("Error in performing Database Row Extractor: " + rowExtractor + "\n bean " + bean);
        }
        preparedStatement.executeUpdate();
    }

    /**
     * Executes the jdbc insert/update query.
     *
     * @param query The SQL for insert/update.
     * @param rowExtractor Domain object (bean) to prepared statement parameter binding.
     * @param bean the Domain object to be inserted/updated.
     * @param <T>
     */
    public <T extends Object> int executeInsert(String query, RowExtractor<T> rowExtractor, T bean, boolean fetchInsertedId) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            doInternalUpdate(rowExtractor, bean, preparedStatement);
            if(fetchInsertedId) {
                if(logger.isDebugEnabled()) {
                    logger.debug("Mapping generated key (Auto Increment ID) to the object");
                }
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error in performing Database update: " + query + "\n bean " + bean);
        }
        return 0;
    }
}
