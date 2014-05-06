${LICENSE}
package ${restEntityPackageName};

import java.util.List;

import javax.xml.bind.annotation.*;

import ${entityPackageName}.*;
import z.tool.util.ToStringBuilder;
import z.tool.dao.QueryResult;
import z.tool.web.Page;

${ABOUT}
@XmlRootElement(name="result") @XmlAccessorType(XmlAccessType.NONE)
public final class ${entityName}ItemPage {
    @XmlElement private boolean success = true;
    @XmlElement private String descr;
    private QueryResult<${entityName}> result;

    @XmlElement(name="totalCount")
    public int getTotalCount() {
        return result.getCount();
    }
    
    @XmlElement(name="pageSize")
    public int getPageSize() {
        return Page.DEFAULT_PAGE_MAX;
    }

    @XmlElement(name="items")
    public List<${entityName}> getResults() {
        return result.getResults();
    }

    public ${entityName}ItemPage result(QueryResult<${entityName}> result) {
        this.result = result;
        return this;
    }
    
    public ${entityName}ItemPage success(boolean success) {
        this.success = success;
        return this;
    }

    public ${entityName}ItemPage descr(String descr) {
        this.descr = descr;
        return this;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("success", success)
            .add("descr", descr)
            .build();
    }
}
