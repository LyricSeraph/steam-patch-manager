package top.lyriclaw.spm.backend.config;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
@AllArgsConstructor
public class RedisConfig {

    public static final String REDIS_KEY_STEAM_ID_TO_NAME = "id_to_name";
    public static final String REDIS_KEY_STEAM_APP_LIST_UPDATE_TIME = "app_list_update_time";


    @Bean
    public RedisTemplate<String, Object> stringObjectTemplate(@Autowired RedisConnectionFactory connectionFactoy) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactoy);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public StringRedisTemplate stringStringTemplate(@Autowired RedisConnectionFactory connectionFactoy) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactoy);
        return template;
    }

}
