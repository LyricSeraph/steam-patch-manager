package top.lyriclaw.spm.backend.service;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SessionService {

    private static final ThreadLocal<Map<KeyType, String>> SESSION_CONTEXT = new ThreadLocal<>();

    public void init() {
        SESSION_CONTEXT.set(new HashMap<>());
    }

    @NotNull
    public String get(@NotNull KeyType key) {
        return SESSION_CONTEXT.get().getOrDefault(key, "");
    }

    public void set(@NotNull KeyType key, @NotNull String value) {
        SESSION_CONTEXT.get().put(key, value);
    }

    public void freeze() {
        var context = SESSION_CONTEXT.get();
        SESSION_CONTEXT.set(Collections.unmodifiableMap(context));
    }

    public enum KeyType {
        Token,
        ;
    }

}
