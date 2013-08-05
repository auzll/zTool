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
package z.tool.util.cache;

//import java.util.Date;
//
//import com.danga.MemCached.MemCachedClient;
//import com.danga.MemCached.SockIOPool;

public final class DangaMemcache /*implements ICache*/ {
//    private MemCachedClient memCachedClient; 
//    private SockIOPool pool;
//    
//    public DangaMemcache(String cacheName, String server, int initConn, 
//            int minConn, int maxConn, long maxIdle, long maintSleep,
//            boolean nagle, int socketTO, int socketConnectTo,
//            int hashingAlg, boolean primitiveAsString, boolean sanitizeKeys,
//            boolean compressEnable, long compressThreshold) {
//        
//        this.pool = SockIOPool.getInstance(cacheName);
//        this.pool.setServers(new String[] {server}); 
//        this.pool.setInitConn(initConn);
//        this.pool.setMinConn(minConn);
//        this.pool.setMaxConn(maxConn);
//        this.pool.setMaxIdle(maxIdle);
//        this.pool.setMaintSleep(maintSleep);
//        this.pool.setNagle(nagle);
//        this.pool.setSocketTO(socketTO);
//        this.pool.setSocketConnectTO(socketConnectTo);
//        this.pool.setHashingAlg(hashingAlg);
//        this.pool.initialize();
//        
//        this.memCachedClient = new MemCachedClient(cacheName);
//        this.memCachedClient.setPrimitiveAsString(primitiveAsString);
//        this.memCachedClient.setSanitizeKeys(sanitizeKeys);
//        this.memCachedClient.setCompressEnable(compressEnable);
//        this.memCachedClient.setCompressThreshold(compressThreshold); // 大于多少k才压缩
//        
//    }
//    
//    public DangaMemcache(SockIOPool pool, MemCachedClient memCachedClient) {
//        this.pool = pool;
//        this.memCachedClient = memCachedClient;
//    }
//    
//    /**
//     * @param cacheName cacheName
//     * @param server cacheHost:cachePort
//     */
//    public DangaMemcache(String cacheName, String server) {
//        this(
//                cacheName, /*cacheName*/
//                server, /*server*/
//                5, /*initConn*/
//                5, /*minConn*/
//                250, /*maxConn*/
//                21600000, /*maxIdle*/
//                1000 * 30, /*maintSleep*/
//                false, /*nagle*/
//                3000, /*socketTO*/
//                0, /*socketConnectTo*/
//                SockIOPool.NEW_COMPAT_HASH, /*hashingAlg*/
//                false, /*primitiveAsString*/
//                false, /*sanitizeKeys*/
//                true, /*compressEnable*/
//                65536 /*compressThreshold*/
//        );
//        
////      String cacheName = "xxxx";
////      String[] servers = { "host:port" };
////
////      pool = SockIOPool.getInstance(cacheName);
////      pool.setServers(servers); 
////      pool.setInitConn(5);
////      pool.setMinConn(5);
////      pool.setMaxConn(250);
////      pool.setMaxIdle(21600000);
////      pool.setMaintSleep(1000 * 30);
////      pool.setNagle(false);
////      pool.setSocketTO(3000);
////      pool.setSocketConnectTO(0);
////      pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);
////      pool.initialize();
////
////      memCachedClient = new MemCachedClient(cacheName);
////      memCachedClient.setPrimitiveAsString(false);
////      memCachedClient.setSanitizeKeys(false);
////      memCachedClient.setCompressEnable(true);
////      memCachedClient.setCompressThreshold(65536); // 大于64KB才压缩
//    }
//
//    public boolean delete(String key) {
//        return memCachedClient.delete(key);
//    }
//
//    public Object get(String key) {
//        return memCachedClient.get(key);
//    }
//
//    public Object get(String key, Date expireDate) {
//        Object value = get(key);
//        if (null != value && null != expireDate) { 
//            set(key, value, expireDate);
//        }
//        return value;
//    }
//
//    public Object get(String key, long TTL) {
//        Object value = get(key);
//        if (null != value && TTL > 0) { 
//            set(key, value, TTL);
//        }
//        return value;
//    }
//
//    public boolean set(String key, Object value) {
//        return memCachedClient.set(key, value);
//    }
//
//    public boolean set(String key, Object obj, Date expireDate) {
//        return memCachedClient.set(key, obj, expireDate);
//    }
//
//    public boolean set(String key, Object value, long TTL) {
//        return memCachedClient.set(key, value, new Date(System.currentTimeMillis() + TTL * 1000));
//    }
//
//    public void destroy() {
//    }
    
}