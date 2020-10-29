package net.dirtcraft.discord.spongediscordlib;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordUtil {

    private static JDA jda = SpongeDiscordLib.getJDA();

    public static void setStatus(Activity.ActivityType type, String name, String url) {
        if (url != null) {
            jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(type, name, url));
        } else {
            jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(type, name));
        }
    }
}