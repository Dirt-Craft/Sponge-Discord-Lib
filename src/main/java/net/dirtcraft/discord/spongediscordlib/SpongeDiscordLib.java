package net.dirtcraft.discord.spongediscordlib;

import com.google.inject.Inject;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfigManager;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Plugin(
        id = "sponge-discord-lib",
        name = "Sponge Discord Lib",
        description = "Sponge plugin made to manage every interaction with Discord.",
        url = "https://dirtcraft.net/",
        version = "1.0.0",
        authors = {
                "juliann",
                "ShinyAfro"
        }
)
public class SpongeDiscordLib {


    @DefaultConfig(sharedRoot = true)
    @Inject private ConfigurationLoader<CommentedConfigurationNode> loader;
    @Inject private Logger logger;

    private static SpongeDiscordLib instance;
    private static long startTime = System.currentTimeMillis();
    private static CompletableFuture<JDA> jda;

    private boolean initialized;

    public SpongeDiscordLib(){
        instance = this;
    }

    @Listener(order = Order.PRE)
    public void onPreInit(GameConstructionEvent event) {
        if (initialized) return;
        new DiscordConfigManager(loader);
        jda = CompletableFuture.supplyAsync(this::initJDA);
        initialized = true;
    }

    private JDA initJDA() {
        if (jda.getNow(null) != null) return jda.join();
        JDA jda;
        try {
            Collection<GatewayIntent> intents = Arrays.asList(
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.DIRECT_MESSAGES
            );
            jda = JDABuilder.createDefault(DiscordConfiguration.Discord.TOKEN, intents)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();
            return jda.awaitReady();
        } catch (LoginException | InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    public static JDA getJDA() {
        while (!jda.isDone() && !initTimeExceeded()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
        }
        return jda.join();
    }

    private static boolean initTimeExceeded(){
        final long SECOND = 1000;
        final long MINUTE = SECOND * 60;
        long duration = System.currentTimeMillis() - startTime;
        instance.logger.info("JDA has taken " + duration + "ms to boot!");
        return duration > 5 * MINUTE;
    }

    public static String getBotToken() {
        return DiscordConfiguration.Discord.TOKEN;
    }

    public static String getGamechatChannelID() {
        return DiscordConfiguration.Discord.GAMECHAT_CHANNEL_ID;
    }

    public static String getServerName() {
        return DiscordConfiguration.Discord.SERVER_NAME;
    }

    public static SpongeDiscordLib getInstance(){
        return instance;
    }

}