/**
 * https://github.com/auzll/zTool
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
