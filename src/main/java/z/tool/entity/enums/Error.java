/**
 * Code by auzll (http://auzll.iteye.com)
 */
package z.tool.entity.enums;

import z.tool.entity.interfaces.ErrorTip;

/**
 * 
 * @author auzll
 * @since 2013-7-2 上午01:10:30
 */
public enum Error implements ErrorTip {
    TOO_MANY_USER("too many user"),
    
    SYSTEM_BUSY("system busy, please try again later"),
    ;
    
    private final String enTip;
    
    private Error(String enTip) {
        this.enTip = enTip;
    }
    
    public String tip() {
        return enTip;
    }
}
