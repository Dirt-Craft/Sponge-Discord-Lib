package net.dirtcraft.discord.spongediscordlib;

import com.google.inject.Inject;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfigManager;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.minecraftforge.fml.common.FMLCommonHandler;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.security.auth.login.LoginException;
import java.util.concurrent.CompletableFuture;

@Plugin(
        id = "sponge-discord-lib",
        name = "Sponge Discord Lib",
        description = "Sponge plugin made to manage every interaction with Discord.",
        url = "https://dirtcraft.net/",
        version = "1.0.0",
        authors = {
                "juliann"
        }
)
public class SpongeDiscordLib {

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private DiscordConfigManager cfgManager;

    private static boolean hasInitialized = false;

    private static JDA jda;

    @Listener(order = Order.PRE)
    public void onPreInit(GameConstructionEvent event) {
        CompletableFuture<Void> initTimer = null;
        this.cfgManager = new DiscordConfigManager(loader);
        try {
            initTimer = CompletableFuture.runAsync(this::initJdaConnectTimer);
            initJDA();
        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            if (initTimer != null) initTimer.cancel(true);
        }
    }

    private void initJDA() throws InterruptedException, LoginException {
        if (hasInitialized) {
            throw new InterruptedException("JDA has already been initialized!");
        }

        jda = JDABuilder.createLight(DiscordConfiguration.Discord.TOKEN)
                .build()
                .awaitReady();
        hasInitialized = true;
    }

    public static JDA getJDA() {
        return jda;
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

    private void initJdaConnectTimer(){
        int n = 0;
        try {
            while (jda == null) {
                Thread.sleep(1000);
                System.out.println("JDA has taken " + ++n + " second(s) to initialize.");
                if (n > 60){
                    System.out.println("Rebooting server automatically due to JDA initialization failure.");
                    FMLCommonHandler.instance().exitJava(-1, true);
                }
            }
        } catch (InterruptedException ignored){ }
    }

}
