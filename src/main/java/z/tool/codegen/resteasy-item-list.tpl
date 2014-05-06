${LICENSE}
package ${restEntityPackageName};

import java.util.List;

import javax.xml.bind.annotation.*;

import ${entityPackageName}.*;
import z.tool.util.ToStringBuilder;

${ABOUT}
@XmlRootElement(name="result") @XmlAccessorType(XmlAccessType.NONE)
public final class ${entityName}ItemList {
    @XmlElement private boolean success = true;
    @XmlElement private String descr;
    private List<${entityName}> items;

    @XmlElement(name="items")
    public List<${entityName}> getResults() {
        return items;
    }

    public ${entityName}ItemList items(List<${entityName}> items) {
        this.items = items;
        return this;
    }
    
    public ${entityName}ItemList success(boolean success) {
        this.success = success;
        return this;
    }

    public ${entityName}ItemList descr(String descr) {
        this.descr = descr;
        return this;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("success", success)
            .add("descr", descr)
            .add("items", items)
            .build();
    }
}
