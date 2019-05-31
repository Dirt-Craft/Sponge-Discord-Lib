package net.dirtcraft.discord.spongediscordlib;

import com.google.inject.Inject;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfigManager;
import net.dirtcraft.discord.spongediscordlib.Configuration.DiscordConfiguration;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
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

    public static void setStatus(Game.GameType type, String name, String url) {


        if (type.equals(Game.GameType.DEFAULT)) {
            if (url != null) {
                jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, name, ""));
            } else {
                jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, name));
            }
            return;
        }

        if (type.equals(Game.GameType.LISTENING)) {
            if (url != null) {
                jda.getPresence().setGame(Game.of(Game.GameType.LISTENING, name, url));
            } else {
                jda.getPresence().setGame(Game.of(Game.GameType.LISTENING, name));
            }
            return;
        }

        if (type.equals(Game.GameType.WATCHING)) {

            if (url != null) {
                jda.getPresence().setGame(Game.of(Game.GameType.WATCHING, name, url));
            } else {
                jda.getPresence().setGame(Game.of(Game.GameType.WATCHING, name));
            }
            return;
        }

        if (type.equals(Game.GameType.STREAMING)) {
            if (url != null) {
                jda.getPresence().setGame(Game.of(Game.GameType.STREAMING, name, url));
            } else {
                jda.getPresence().setGame(Game.of(Game.GameType.STREAMING, name));
            }
            return;
        }

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
