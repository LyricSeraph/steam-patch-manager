package top.lyriclaw.spm.backend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import retrofit2.Retrofit;
import top.lyriclaw.spm.backend.api.SteamApis;
import top.lyriclaw.spm.backend.config.RedisConfig;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class SteamGameService {

    private final Retrofit retrofit;
    private final StringRedisTemplate stringRedisTemplate;

    public String getAppNameById(long appId) {
        return (String) stringRedisTemplate.opsForHash().get(RedisConfig.REDIS_KEY_STEAM_ID_TO_NAME, String.valueOf(appId));
    }

    public boolean updateSteamRecords() {
        boolean success = true;
        SteamApis service = retrofit.create(SteamApis.class);
        try {
            var result = service.listGames().execute();
            if (result.isSuccessful() && result.body() != null && result.body().getApplist() != null) {
                Map<String, String> idToNameMap = new HashMap<>();
                for (var item : result.body().getApplist().getApps()) {
                    idToNameMap.put(String.valueOf(item.getAppid()), item.getName());
                }
                stringRedisTemplate.opsForHash().putAll(RedisConfig.REDIS_KEY_STEAM_ID_TO_NAME, idToNameMap);
            } else {
                success = false;
            }
        } catch (Exception e) {
            log.error("updateSteamGameRecords failed", e);
            success = false;
        }
        return success;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    void checkAndUpdateSteamList() {
        String updatedTime = stringRedisTemplate.opsForValue().get(RedisConfig.REDIS_KEY_STEAM_APP_LIST_UPDATE_TIME);
        Instant lastUpdateTime = (StringUtils.hasLength(updatedTime) ? Instant.parse(updatedTime) : Instant.MIN);
        if (Instant.now().minus(1, ChronoUnit.DAYS).isAfter(lastUpdateTime)) {
            boolean success = updateSteamRecords();
            if (success) {
                stringRedisTemplate.opsForValue().set(RedisConfig.REDIS_KEY_STEAM_APP_LIST_UPDATE_TIME,
                        DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
            }
        }

    }
}
