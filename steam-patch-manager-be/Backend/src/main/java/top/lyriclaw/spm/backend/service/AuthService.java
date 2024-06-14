package top.lyriclaw.spm.backend.service;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.lyriclaw.spm.backend.config.props.AuthConfigProps;

import java.time.Instant;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private static final String AUTH_SALT = "steam-patch-manager";

    private final AuthConfigProps authConfigProps;

    private Set<String> tokenSet;

    @PostConstruct
    public void init() {
        refreshToken();
    }

    @Autowired
    public AuthService(AuthConfigProps authConfigProps) {
        this.authConfigProps = authConfigProps;
        tokenSet = new ConcurrentSkipListSet<>();
    }

    public boolean isAuthenticated(String token) {
        return !authConfigProps.isEnableAuthKey() || tokenSet.contains(token);
    }

    @Scheduled(cron = "0 * * * * *")
    void refreshToken() {
        Instant time = Instant.now();
        int currentMinute = (int) (time.getEpochSecond() / 60);
        Set<String> newTokenSet = new ConcurrentSkipListSet<>();
        for (int i = -2; i <= 2; i++) {
            String minutePart = DigestUtils.md5DigestAsHex(String.valueOf(currentMinute + i).toUpperCase(Locale.ROOT).getBytes());
            String keyPart = DigestUtils.md5DigestAsHex((authConfigProps.getAuthKey() + AUTH_SALT).toUpperCase(Locale.ROOT).getBytes());
            String token = DigestUtils.md5DigestAsHex((minutePart + keyPart).toUpperCase(Locale.ROOT).getBytes());
            newTokenSet.add(token.toUpperCase(Locale.ROOT));
        }
        tokenSet = newTokenSet;
    }
}
