package br.medtec.services;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RedisService {

    private final ReactiveKeyCommands<String> keyCommands;
    private final ValueCommands<String, Object> countCommands;

    public RedisService(ReactiveRedisDataSource reactiveRedisDataSource, RedisDataSource redisDataSource) {
        this.keyCommands = reactiveRedisDataSource.key();
        this.countCommands = redisDataSource.value(Object.class);
    }


    public Object get(String key) {
        return countCommands.get(key);
    }

    public void set(String key, Object value) {
        countCommands.set(key, value);
    }

    void increment(String key, Long incrementBy) {
        countCommands.incrby(key, incrementBy);
    }

    public Uni<Void> del(String key) {
        return keyCommands.del(key)
                .replaceWithVoid();
    }

    Uni<List<String>> keys() {
        return keyCommands.keys("*");
    }

}
