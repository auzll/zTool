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
package z.tool.util;
import static z.tool.util.StringUtil.*;

/**
 * @author auzll
 *
 */
public final class DomainUtil {
    private DomainUtil() {}
    
    private static final String HTTPS_PREFIX = "https://";
    private static final String HTTP_PREFIX = "http://";
    private static final String WWW_PREFIX = "www.";
    
    public static String format(String domain) {
        domain = lower(trimAndTryReturnNull(domain));
        if (null == domain) {
            return domain;
        }
        
        if (domain.length() > HTTPS_PREFIX.length()
                && domain.startsWith(HTTPS_PREFIX)) {
            domain = domain.substring(HTTPS_PREFIX.length());
        } else if (domain.length() > HTTP_PREFIX.length()
                && domain.startsWith(HTTP_PREFIX)) {
            domain = domain.substring(HTTP_PREFIX.length());
        }
        
        if (domain.length() > WWW_PREFIX.length()
                && domain.startsWith(WWW_PREFIX)) {
            domain = domain.substring(WWW_PREFIX.length());
        }
        
        return domain;
    }
}
