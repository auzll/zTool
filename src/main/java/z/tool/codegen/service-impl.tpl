@Singleton
public class ${entityName}ServiceImpl extends AbstractService implements ${entityName}Service {
    private static final Logger LOG = Logger.getLogger(${entityName}Service.class);
    private static final boolean LOG_INFO = LOG.isInfoEnabled();
    
    @Inject private ${entityName}Dao ${lowerEntityName}Dao;

    @Override @Transactional public ${entityName} save(${entityName} ${lowerEntityName}) {
        StringBuilder logBuff = null;
        if (LOG_INFO) {
            logBuff = beginLog("save");
            LogUtil.appendLog(logBuff, "${lowerEntityName}", ${lowerEntityName});
        }
        
        try {
            // TODO Auto-generated method stub
            
            ${lowerEntityName}.setCreateTime(new Date());
            ${lowerEntityName}.setCreator(getOperator());
            ${lowerEntityName}.setUpdateTime(${lowerEntityName}.getCreateTime());
            ${lowerEntityName}.setUpdator(${lowerEntityName}.getUpdator());
            
            int dbRet = ${lowerEntityName}Dao.save(${lowerEntityName});
            LogUtil.appendLog(logBuff, "dbRet", dbRet);
            if (1 != dbRet) {
                throw new HumanNeededError(tip("${lowerEntityName}service.save_fail"));
            }
            
            return ${lowerEntityName};
        } finally {
            if (LOG_INFO) {
                LOG.info(LogUtil.finishLog(logBuff));
            }
        }
    }

    @Override @Transactional public ${entityName} delete(${entityName} ${lowerEntityName}) {
        StringBuilder logBuff = null;
        if (LOG_INFO) {
            logBuff = beginLog("delete");
            LogUtil.appendLog(logBuff, "${lowerEntityName}", ${lowerEntityName});
        }
        
        try {
            // TODO Auto-generated method stub
            int dbRet = ${lowerEntityName}Dao.delete(${lowerEntityName});
            LogUtil.appendLog(logBuff, "dbRet", dbRet);
            if (1 != dbRet) {
                throw new HumanNeededError(tip("${lowerEntityName}service.delete_fail"));
            }
            
            return ${lowerEntityName};
        } finally {
            if (LOG_INFO) {
                LOG.info(LogUtil.finishLog(logBuff));
            }
        }
    }

    @Override @Transactional public ${entityName} update(${entityName} ${lowerEntityName}) {
        StringBuilder logBuff = null;
        if (LOG_INFO) {
            logBuff = beginLog("update");
            LogUtil.appendLog(logBuff, "${lowerEntityName}", ${lowerEntityName});
        }
        
        try {
            // TODO Auto-generated method stub
            ${lowerEntityName}.setUpdateTime(new Date());
            ${lowerEntityName}.setUpdator(getOperator());
            
            int dbRet = ${lowerEntityName}Dao.update(${lowerEntityName});
            LogUtil.appendLog(logBuff, "dbRet", dbRet);
            if (1 != dbRet) {
                throw new HumanNeededError(tip("${lowerEntityName}service.update_fail"));
            }
            
            return ${lowerEntityName};
        } finally {
            if (LOG_INFO) {
                LOG.info(LogUtil.finishLog(logBuff));
            }
        }
    }

    @Override public ${entityName} querySimple(${entityName} ${lowerEntityName}) {
        // TODO Auto-generated method stub
        return ${lowerEntityName}Dao.queryById(${lowerEntityName});
    }

    @Override public ${entityName} queryDetail(${entityName} ${lowerEntityName}) {
        ${entityName} db${entityName} = this.querySimple(${lowerEntityName});
        if (null != db${entityName}) {
            // TODO Auto-generated method stub
        }
        return db${entityName};
    }

    @Override public QueryResult<${entityName}> queryPage(QueryCondition<${entityName}> condition) {
        int count = ${lowerEntityName}Dao.queryPageCount(condition);
        if (count < 1) {
            return QueryResult.emptyResult();
        }
        
        // TODO Auto-generated method stub
        
        List<${entityName}> list = ${lowerEntityName}Dao.queryPageList(condition);
        return new QueryResult<>(count, list);
    }

    @Override public List<${entityName}> queryListByIdList(List<Long> idList) {
        // TODO Auto-generated method stub
        return ${lowerEntityName}Dao.queryListByIdList(idList);
    }
}