/**
 * https://github.com/auzll/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package z.tool.entity;

import z.tool.util.StringUtil;
import z.tool.util.ToStringBuilder;

/**
 * @author auzll
 */
public final class EmailAddress {
    /** email地址前缀 */
    private String account;

    /** email地址的域名 */
    private String domain;
    
    public static EmailAddress valueOf(String email) {
        email = StringUtil.lower(StringUtil.trimAndTryReturnNull(email));
        if (null == email) {
            return null;
        }
        
        int atIndex = email.indexOf('@');
        if (atIndex < 0) {
            return null;
        }
        
        return new EmailAddress(email.substring(0, atIndex), email.substring(atIndex + 1));
    }
    
    public EmailAddress() {}

    public EmailAddress(String account, String domain) {
        this.account = account;
        this.domain = domain;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("account", account)
            .add("domain", domain)
            .build();
    }
}
