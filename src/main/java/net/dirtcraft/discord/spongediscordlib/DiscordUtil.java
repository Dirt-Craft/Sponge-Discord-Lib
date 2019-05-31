package net.dirtcraft.discord.spongediscordlib;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

public class DiscordUtil {

    private static JDA jda = SpongeDiscordLib.getJDA();

    public static void setStatus(Game.GameType type, String name, String url) {
        if (type.equals(Game.GameType.DEFAULT)) {
            if (url != null) {
                jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, name, url));
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

}
