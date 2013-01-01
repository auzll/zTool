/**
 * https://github.com/auzll/zTool
 */
package z.tool.util.cache;

import java.util.Date;

/**
 * 缓存服务接口
 */
public interface ICache {

    /**
     * 从缓存中删除指定key的对象
     */
    boolean delete(String key);

    /**
     * 从缓存中获取指定key的对象
     */
    Object get(String key);

    /**
     * 从缓存中获取指定key的对象，同时重设该对象的到期时间
     */
    Object get(String key, Date expireDate);

    /**
     * 从缓存中获取指定key的对象，同时重设该对象的超时时间(从当前时间开始算)
     * 
     * @param TTL
     *            超时时间，单位是秒
     */
    Object get(String key, long TTL);

    /**
     * 把对象按指定的key存放到缓存中，该对象永不超时。
     */
    boolean set(String key, Object value);

    /**
     * 把对象按指定的key存放到缓存中，该对象将在expireDate到期
     */
    boolean set(String key, Object value, Date expireDate);

    /**
     * 把对象按指定的key存放到缓存中，该对象将在TTL时间之后到期
     * 
     * @param TTL
     *            超时时间，单位是秒
     */
    boolean set(String key, Object value, long TTL);
    
    void destroy();
}
