package br.com.url.shortener.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CashRepository {
	
	private HashOperations<String, String, Object> hashOperations;

    private RedisTemplate<String, Object> redisTemplate;

    @Value("${time-to-live:1}")
    private String timeToLive;
    
    public CashRepository(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }
    
    public void putOnCache(String key, String hashKey, Object obj){
    	redisTemplate.expire(getHashKey(key, hashKey),Long.parseLong(timeToLive), TimeUnit.MINUTES);
        hashOperations.put(key, hashKey, obj);
    }

    public Object getFromCache(String key, String hashKey){
        return hashOperations.get(key, hashKey);
    }
    
    private String getHashKey(String key, String hashKey) {
    	return key.concat(":").concat(hashKey);
    }
}
