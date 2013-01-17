/**
 * 
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
