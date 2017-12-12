package com.swellwu.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.mapper.JsonMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>Description:</p>
 *
 * @author heng
 * @date 2017-12-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    String stringKey = "stringKey";
    String stringValue = "stringValue";

    @Before
    public void reidsBaseTest() {
        Assert.assertTrue(redisTemplate != null);

        redisTemplate.boundValueOps(stringKey).set(stringValue);
        Assert.assertEquals(stringValue, redisTemplate.boundValueOps(stringKey).get());
    }

    /**
     * 用以测试redis事务
     * watch multi exec
     * 应该是watch 到 exec期间，watch 的key值有变化会导致事务执行失败，但是以下代码没有出现事务执行失败的情况
     * 根据网上资料，是通过exec()的返回值来判断事务是否执行，如果返回null，表示事务执行失败
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void redisTransactionTest() throws InterruptedException, ExecutionException {
        String key = "test";
        ValueOperations<String, String> strOps = redisTemplate.opsForValue();
        strOps.set(key, "hello");
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            final int idx = i;
            tasks.add((Callable) () -> redisTemplate.execute(new SessionCallback() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.watch(key);
                    String origin = (String) operations.opsForValue().get(key);
                    operations.multi();
                    operations.opsForValue().set("111", origin + idx);
                    Object rs = operations.exec();
                    System.out.println("set:" + origin + idx + " rs:" + rs);
                    return rs;
                }
            }));
        }
        List<Future<Object>> futures = pool.invokeAll(tasks);
        for (Future<Object> f : futures) {
            System.out.println(f.get());
        }
        pool.shutdown();
        pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}