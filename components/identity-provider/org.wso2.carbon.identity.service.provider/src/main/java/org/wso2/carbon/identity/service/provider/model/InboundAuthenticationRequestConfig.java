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

package org.wso2.carbon.identity.service.provider.model;

import java.io.Serializable;
import java.util.Properties;

public class InboundAuthenticationRequestConfig implements Serializable {

    private String inboundAuthKey;
    private String inboundAuthType;
    private String inboundConfigType;
    private String friendlyName;
    private Properties properties = new Properties();


    /**
     * @return
     */
    public String getInboundAuthKey() {
        return inboundAuthKey;
    }

    /**
     * @param inboundAuthKey
     */
    public void setInboundAuthKey(String inboundAuthKey) {
        this.inboundAuthKey = inboundAuthKey;
    }

    /**
     * @return
     */
    public String getInboundAuthType() {
        return inboundAuthType;
    }

    /**
     * @param inboundAuthType
     */
    public void setInboundAuthType(String inboundAuthType) {
        this.inboundAuthType = inboundAuthType;
    }

    /**
     *
     * @return inboundUIType
     */
    public String getInboundConfigType() {
        return inboundConfigType;
    }

    /**
     * Sets the UIType of the inbound authentication config.
     * @param inboundConfigType
     */
    public void setInboundConfigType(String inboundConfigType) {
        this.inboundConfigType = inboundConfigType;
    }


    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
