package top.lyriclaw.spm.backend.api;

import lombok.Data;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface SteamApis {

    String BASE_URL = "https://api.steampowered.com/";

    @GET("ISteamApps/GetAppList/v2/")
    Call<GameListResult> listGames();

    @Data
    class GameListResult {

        public AppList applist;

        @Data
        public static class AppList {
            public List<GameItem> apps;
        }

        @Data
        public static class GameItem {
            public long appid;
            public String name;
        }


    }
}
