${LICENSE}
package ${restEntityPackageName};

import javax.xml.bind.annotation.*;

import ${entityPackageName}.*;
import z.tool.util.ToStringBuilder;

${ABOUT}
@XmlRootElement(name="result") @XmlAccessorType(XmlAccessType.NONE)
public final class ${entityName}Item {
    @XmlElement private boolean success = true;
    @XmlElement private String descr;
    private ${entityName} item;

    @XmlElement(name="item")
    public ${entityName} getResults() {
        return item;
    }

    public ${entityName}Item item(${entityName} item) {
        this.item = item;
        return this;
    }
    
    public ${entityName}Item success(boolean success) {
        this.success = success;
        return this;
    }

    public ${entityName}Item descr(String descr) {
        this.descr = descr;
        return this;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("success", success)
            .add("descr", descr)
            .add("item", item)
            .build();
    }
}
