package com.github.bc.doMyTraining.cache;


import com.github.bc.doMyTraining.catalys.course.QuizzQuestion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
@EnableConfigurationProperties(CacheProperties.class)
@EnableCaching
public class CacheConfig {

    private final CacheProperties cacheProperties;

    public CacheConfig(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    /**
     * Configure the JedisConnectionFactory
     *
     * @param properties The redis properties
     * @return a JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisProperties properties) {

        if (properties.getCluster() != null) {
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(properties.getCluster().getNodes());
            redisClusterConfiguration.setPassword(properties.getPassword());

            if (properties.getCluster().getMaxRedirects() != null)
                redisClusterConfiguration.setMaxRedirects(properties.getCluster().getMaxRedirects());

            return new JedisConnectionFactory(redisClusterConfiguration);
        }

        if (properties.getSentinel() != null) {
            RedisSentinelConfiguration redisSantinelConfiguration = new RedisSentinelConfiguration();
            redisSantinelConfiguration.setMaster(properties.getSentinel().getMaster());
            redisSantinelConfiguration.setSentinels(createSentinels(properties.getSentinel()));
            redisSantinelConfiguration.setPassword(properties.getPassword());
            return new JedisConnectionFactory(redisSantinelConfiguration);
        }

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(properties.getHost());
        redisStandaloneConfiguration.setPort(properties.getPort());
        redisStandaloneConfiguration.setPassword(properties.getPassword());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {

        List<RedisNode> nodes = new ArrayList<>();
        for (String node : sentinel.getNodes()) {
            try {
                String[] parts = node.split(":");
                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            } catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return nodes;
    }

        /**
         * Configures the cache manager
         * @param jedisConnectionFactory A jedisConnectionFactory
         * @return
         */
        @Bean(name = "quizzQuestionCacheManager")
        @Primary
        public CacheManager quizzQuestionCacheManager(
        JedisConnectionFactory jedisConnectionFactory,
        @Qualifier("quizzQuestionSerializer") Jackson2JsonRedisSerializer quizzQuestionSerializer) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .disableCachingNullValues()
        .serializeValuesWith(RedisSerializationContext
        .SerializationPair.fromSerializer(quizzQuestionSerializer));

        redisCacheConfiguration.usePrefix();

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory)
        .cacheDefaults(redisCacheConfiguration).build();
        }

        @Bean(name = "quizzQuestionSerializer")
        @Primary
        public Jackson2JsonRedisSerializer quizzQuestionSerializer() {
            return new Jackson2JsonRedisSerializer(QuizzQuestion.class);
        }


}

