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

package org.wso2.carbon.identity.provider.model;

import java.io.Serializable;

/**
 * Representation of a user claim configuration.
 */
public class ClaimConfig implements Serializable {

    private static final long serialVersionUID = -3886962663799281628L;

    private int dialectId;

    private ClaimConfig(ClaimConfigBuilder builder) {
        this.dialectId = builder.dialectId;
    }

    public int getDialectId() {
        return dialectId;
    }

    /**
     * Builds the claim configuration to be used by the IDPs.
     */
    static class ClaimConfigBuilder {

        private int dialectId;

        ClaimConfigBuilder(int dialectId) {
            this.dialectId = dialectId;
        }

        ClaimConfig build() {
            return new ClaimConfig(this);
        }
    }

}
