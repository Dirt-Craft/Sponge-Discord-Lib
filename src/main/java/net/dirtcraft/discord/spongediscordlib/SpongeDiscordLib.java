package net.dirtcraft.discord.spongediscordlib;

import com.google.inject.Inject;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfigManager;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfiguration;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.security.auth.login.LoginException;

@Plugin(
        id = "sponge-discord-lib",
        name = "Sponge Discord Lib",
        description = "Sponge plugin made to manage every interaction with Discord.",
        url = "https://dirtcraft.net/",
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

    @Listener(order = Order.FIRST)
    public void onPreInit(GamePreInitializationEvent event) {
        this.cfgManager = new DiscordConfigManager(loader);
        try {
            initJDA();
        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void initJDA() throws InterruptedException, LoginException {

        if (hasInitialized) {
            throw new InterruptedException("JDA has already been initialized!");
        }

        jda = new JDABuilder(DiscordConfiguration.Discord.TOKEN)
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

    public static String getServerName() {
        return DiscordConfiguration.Discord.SERVER_NAME;
    }

}
