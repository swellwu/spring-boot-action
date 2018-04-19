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
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    @Test
    public void test2() {
        String key = "test";
        ValueOperations<String, String> strOps = redisTemplate.opsForValue();
        strOps.set(key, "hello");
        Object rs = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.watch(key);
                String origin = (String) operations.opsForValue().get(key);
                System.out.println(origin);
                operations.multi();
                operations.opsForValue().set("111", "111");
                operations.opsForValue().set("222", "222");
                Object rs = operations.exec();
                return rs;
            }
        });
        System.out.println(rs);
    }

    @Test
    public void test3() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String watch = jedis.watch("test");
        System.out.println(Thread.currentThread().getName() + "--" + watch);
        Transaction multi = jedis.multi();
        multi.set("111", "111");
        multi.set("222", "222");
        List<Object> exec = multi.exec();
        System.out.println("--->>" + exec);
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

    @Test
    public void redisScanTest() {
        List<String> keys = keyScan(redisTemplate, "a*", 10);
        int count = 0;
        while (!keys.isEmpty()) {
            count += keys.size();
            for (String key : keys) {
                redisTemplate.delete(key);
            }
            keys = keyScan(redisTemplate, "a*", 10);
        }
        System.out.println("count:" + count);
    }

    @SuppressWarnings("unchecked")
    public static List<String> keyScan(RedisTemplate redisTemplate, String pattern, int limit) {
        List<String> list = new ArrayList<>();
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisTemplate.getConnectionFactory().getConnection();
            ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
            Cursor<byte[]> c = redisConnection.scan(options);
            RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
            while (c.hasNext()) {
                String key = redisSerializer.deserialize(c.next());
                list.add(key);
            }
        } finally {
            if (redisConnection != null) {
                redisConnection.close();
            }
        }
        return list;
    }

    @Test
    public void redisGenerator() {
        for (int i = 1; i < 100; ++i) {
            String key = "a" + UUID.randomUUID().toString();
            redisTemplate.boundValueOps(key).set(key, 10, TimeUnit.HOURS);
        }
    }

    public static List<String> scan2(RedisTemplate redisTemplate, String pattern, int limit) {
        List<String> list = new ArrayList<>();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        redisTemplate.execute(new RedisCallback<Iterable<byte[]>>() {
            @Override
            public Iterable<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {

                List<byte[]> binaryKeys = new ArrayList<byte[]>();

                Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).count(limit).build());
                while (cursor.hasNext()) {
                    String key = redisSerializer.deserialize(cursor.next());
                    System.out.println(key);
                    list.add(key);
                }

                try {
                    cursor.close();
                } catch (IOException e) {
                    // do something meaningful
                }
                return binaryKeys;
            }
        });
        return list;
    }
}
