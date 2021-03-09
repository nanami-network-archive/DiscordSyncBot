package xyz.n7mn.dev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.File;
import java.util.Timer;

public class BotMain {

    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {

        Timer timer = new Timer();
        File file = new File("./setting.json");
        try {
            if (!file.exists()){
                if (file.createNewFile()){
                    SettingData settingData = new SettingData();

                    FileSystem.fileWrite("./setting.json", gson.toJson(settingData));
                    return;
                }
            }

            SettingData data = gson.fromJson(FileSystem.fileRead("./setting.json"), SettingData.class);
            JDABuilder.createLight(data.getDiscordToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_EMOJIS)
                    .addEventListeners(new DiscordListener(data, timer))
                    .enableCache(CacheFlag.VOICE_STATE)
                    .enableCache(CacheFlag.EMOTE)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
