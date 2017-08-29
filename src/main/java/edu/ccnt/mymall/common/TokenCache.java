package edu.ccnt.mymall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static String TOKEN_PREFIX = "token_";

    private static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder().concurrencyLevel(20).initialCapacity(1000).maximumSize(10000).
            expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String s) throws Exception {
            return "null";
        }
    });

    public static void setKey(String key,String value){
        loadingCache.put(key,value);
    }

    public static String getKey(String key)
    {
        String value = null;
        try {
            value = loadingCache.get(key);
            if("null".equals(value)){
                return null;
            }
        } catch (ExecutionException e) {
            logger.error("localCache get error",e);
        }
        return null;
    }
}
