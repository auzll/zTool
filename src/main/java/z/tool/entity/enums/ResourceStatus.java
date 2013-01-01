/**
 * https://github.com/auzll/zTool
 */
package z.tool.entity.enums;

import z.tool.entity.interfaces.IEnumName;

/**
 * ResourceStatus
 */
public enum ResourceStatus implements IEnumName {
    /** 启用 */
    EnAble("启用"), 
    
    /** 禁用 */
    Disable("禁用");
    
    private final String cnName;

    private ResourceStatus(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return this.cnName;
    }

    public String getEnName() {
        return this.toString();
    }
}
