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
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

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
    private static SpongeDiscordLib instance;
    private static long startTime = System.currentTimeMillis();
    private static CompletableFuture<JDA> jda;

    @Inject private Logger logger;

    @DefaultConfig(sharedRoot = true)
    @Inject private ConfigurationLoader<CommentedConfigurationNode> loader;
    private Queue<Consumer<JDA>> onJdaInit = new ConcurrentLinkedQueue<>();
    DiscordConfigManager configManager;
    Collection<GatewayIntent> intents = Arrays.asList(
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.DIRECT_MESSAGES
    );

    public SpongeDiscordLib(){
        instance = this;
    }

    @Listener(order = Order.PRE)
    public void onPreInit(GameConstructionEvent event){
        if (jda == null) initialize();
    }

    private void initialize(){
        configManager = new DiscordConfigManager(loader);
        jda = CompletableFuture.supplyAsync(this::initJDA);
        jda.thenAccept(this::executeCallbacks);
        CommandSpec reload = CommandSpec.builder()
                .permission("spongediscordlink.reload")
                .executor((a,b)->{
                    configManager.update();
                    return CommandResult.success();
                }).build();
        CommandSpec main = CommandSpec.builder()
                .child(reload, "reload", "r")
                .build();
        Sponge.getCommandManager().register(this, main, "discordlib");
    }

    private JDA initJDA() {
        int i = 0;
        while (true) {
            try {
                int loops = i++;
                if (loops > 50) {
                    Thread.sleep(150000);
                    configManager.update();
                } else if (loops > 0){
                    Thread.sleep(30000);
                    configManager.update();
                }
                if (jda.getNow(null) != null) return jda.join();
                JDA jda =  JDABuilder.createDefault(DiscordConfiguration.Discord.TOKEN, intents)
                            .setMemberCachePolicy(MemberCachePolicy.ALL)
                            .build();
                jda.awaitStatus(JDA.Status.CONNECTED, JDA.Status.FAILED_TO_LOGIN);
                //noinspection ConstantConditions
                if (jda != null) return jda;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void executeCallbacks(JDA jda){
        onJdaInit.forEach(consumer-> {
            try{
                consumer.accept(jda);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public static JDA getJDA() {
        if (jda == null) instance.initialize();
        while (!jda.isDone() && !initTimeExceeded()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
        }
        return SpongeDiscordLib.jda.getNow(null);
    }

    public static void getJDA(Consumer<JDA> callback){
        if (jda == null) instance.initialize();
        JDA jda = SpongeDiscordLib.jda.getNow(null);
        if (jda == null) instance.onJdaInit.add(callback);
        else callback.accept(jda);
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