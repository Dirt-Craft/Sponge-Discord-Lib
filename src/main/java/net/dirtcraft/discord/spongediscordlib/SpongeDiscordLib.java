package net.dirtcraft.discord.spongediscordlib;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
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

    private static JDA jda;

    public static void initJDA(String token) {

        try {

            jda = new JDABuilder(token)
                    .build()
                    .awaitReady();

        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();

        }

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

}
