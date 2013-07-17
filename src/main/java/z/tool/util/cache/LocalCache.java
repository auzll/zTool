/**
 * https://github.com/auzll/zTool
 */
package z.tool.util.cache;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;



/**
 * 本地缓存。内部用ConcurrentHashMap来实现。
 */
public final class LocalCache implements ICache, Runnable {
    private static final Logger LOG = Logger.getLogger(LocalCache.class);

    /** 处理超时缓存的时间间隔，默认是3600秒，即1小时 */
    public static final long DEFAULT_CHECK_INTERVAL = 60 * 60;

    /** 负责缓存的map的数量，默认是10个 */
    public static final int DEFAULT_MODULE_SIZE = 10;

    /** 负责缓存的map的数量 */
    private int moduleSize;

    /** 具体存放缓存信息的map */
    private ConcurrentMap<String, Object>[] caches;

    /** 存放超时缓存的信息 */
    private ConcurrentMap<String, Long> expireCache;

    /** 定时器 */
    private ScheduledExecutorService executorService;
    
    private boolean isLocalExecutorService;

    public LocalCache() {
        this(Executors.newScheduledThreadPool(1));
        this.isLocalExecutorService = true;
    }
    
    public LocalCache(ScheduledExecutorService executorService) {
        this(executorService, DEFAULT_CHECK_INTERVAL, DEFAULT_MODULE_SIZE);
    }

    /**
     * @param checkInterval
     *            检查超时缓存的时间间隔，单位是秒
     * @param moduleSize
     *            负责缓存的map的数量
     */
    @SuppressWarnings("unchecked")
    public LocalCache(ScheduledExecutorService executorService, long checkInterval, int moduleSize) {
        if (checkInterval < 0 || moduleSize < 0) {
            throw new IllegalArgumentException(
                    "The checkInterval or moduleSize is less than 0");
        }
        this.moduleSize = moduleSize;
        caches = new ConcurrentMap[moduleSize];
        for (int i = 0; i < moduleSize; i++) {
            caches[i] = new ConcurrentHashMap<String, Object>();
        }
        expireCache = new ConcurrentHashMap<String, Long>();
        this.executorService = executorService;
        this.executorService.scheduleAtFixedRate(this, 0, checkInterval, TimeUnit.SECONDS);
    }

    /**
     * 检查缓存是否已超时，如果已超时，从相关的map中清除
     * 
     * @param key
     *            缓存对象的key
     */
    private void checkValidate(String key) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("method:checkValidate,key:" + key);
        }
        Long expireDate = expireCache.get(key);
        if (null != expireDate && new Date(expireDate).before(new Date())) {
            getCache(key).remove(key);
            expireCache.remove(key);
        }
    }

    /**
     * 查找跟key关联的对象所在的缓存map
     */
    private ConcurrentMap<String, Object> getCache(String key) {
        long hashCode = (long) key.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return caches[(int) hashCode % moduleSize];
    }

    public boolean delete(String key) {
        if (null == key) {
            throw new IllegalArgumentException("The key is null");
        }
        getCache(key).remove(key);
        expireCache.remove(key);
        return true;
    }

    public Object get(String key) {
        return get(key, null);
    }

    public Object get(String key, Date expireDate) {
        if (null == key) {
            throw new IllegalArgumentException("The key is null");
        }
        checkValidate(key);
        Object value = getCache(key).get(key);
        if (null != value && null != expireDate) {
            expireCache.replace(key, expireDate.getTime());
        }
        return value;
    }

    public Object get(String key, long TTL) {
        return get(key, new Date(System.currentTimeMillis() + 1000 * TTL));
    }

    /**
     * 检查过期的缓存
     */
    public void run() {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("method:run,info:checking expire cache,cachesSize:" + (null != caches ? caches.length : 0));
            }
            if (null != caches && caches.length > 0) {
                for (ConcurrentMap<String, Object> cache: caches) {
                    for (String key: cache.keySet()) {
                        Long expireTime = expireCache.get(key);
                        if (null != expireTime) {
                            if (new Date(expireTime).before(new Date())) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("method:run,info:remove key[" + key
                                            + "]");
                                }
                                cache.remove(key);
                                expireCache.remove(key);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("method:run", e);
        }
    }

    public boolean set(String key, Object value) {
        return set(key, value, null);
    }

    public boolean set(String key, Object value, Date expireDate) {
        if (null == key) {
            throw new IllegalArgumentException("The key is null");
        }
        getCache(key).put(key, value);
        if (null != expireDate) {
            expireCache.put(key, expireDate.getTime());
        }
        return true;
    }

    public boolean set(String key, Object value, long TTL) {
        return set(key, value, new Date(System.currentTimeMillis() + 1000 * TTL));
    }

    public void destroy() {
        expireCache = null;
        caches = null;
        
        if (isLocalExecutorService && null != executorService) {
            executorService.shutdownNow();
            executorService = null;
        }
    }
}
