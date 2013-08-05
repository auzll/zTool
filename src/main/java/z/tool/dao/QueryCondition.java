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
package z.tool.dao;

import java.util.Date;
import java.util.Map;

import z.tool.util.ToStringBuilder;
import z.tool.web.Page;

import com.google.common.collect.Maps;

/**
 * 分页查询条件
 * @author auzll@163.com
 */
public final class QueryCondition<T> {
    /** 默认情况下，每次最多获取15条记录  */
    public static final int DEFAULT_LIMIT = 15;
    
    /** 所传入的对象，例如可以是一个account对象，dao实现类再通过account.getUid()之类的方式来获取条件 */
    private T entity;
    
    /** 其他查询参数，当entity不足以传递查询条件，可以使用该参数 */
    private Map<String, Object> others;
    
    /** 分页参数，数据的开始序号 */
    private int start;
    
    /** 分页参数，每页最多显示的记录数量，默认是 {@link QueryCondition#DEFAULT_LIMIT} */
    private int limit = DEFAULT_LIMIT;
    
    /** 开始时间 */
    private Date timeBegin;
    
    /** 结束时间 */
    private Date timeEnd;
    
    public QueryCondition() {}
    
    public QueryCondition(Page page) {
        this(null, page, null);
    }
    
    public QueryCondition(T entity) {
        this(entity, null, null);
    }
    
    public QueryCondition(T entity, Page page) {
        this(entity, page, null);
    }
    
    public QueryCondition(T entity, Page page, Map<String, Object> others) {
        this.entity = entity;
        this.page(page);
        this.others = others;
    }

    public QueryCondition<T> addOthers(Map<String, Object> others) {
        if (null == this.others) {
            this.others = Maps.newHashMap();
        }
        this.others.putAll(others);
        return this;
    }
    
    public QueryCondition<T> addOthers(String key, Object value) {
        if (null == others) {
            others = Maps.newHashMap();
        }
        others.put(key, value);
        return this;
    }

    public T getEntity() {
        return entity;
    }

    public int getLimit() {
        return limit;
    }
    
    public Map<String, Object> getOthers() {
        return others;
    }

    public int getStart() {
        return start;
    }

    public QueryCondition<T> page(Page page) {
        if (null != page) {
            this.limit = page.getPageMax();
            int startPage = page.getPage();
            if (startPage < 1) {
                startPage = 1;
            }
            this.start = (startPage - 1) * limit;
        }
        return this;
    }
    
    public QueryCondition<T> timeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
        return this;
    }
    
    public QueryCondition<T> timeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
        return this;
    }
    
    public Date getTimeBegin() {
        return timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("entity", entity)
            .add("start", start)
            .add("limit", limit)
            .add("limit", limit)
            .build();
    }
}
