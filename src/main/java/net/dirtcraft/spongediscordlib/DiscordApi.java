package net.dirtcraft.spongediscordlib;

import net.dirtcraft.spongediscordlib.channels.ChannelManager;
import net.dirtcraft.spongediscordlib.commands.DiscordCommandManager;
import net.dirtcraft.spongediscordlib.users.UserManager;
import net.dirtcraft.spongediscordlib.users.roles.RoleManager;
import net.dv8tion.jda.api.JDA;

public interface DiscordApi {
    JDA getJda();

    UserManager getUserManager();

    ChannelManager getChannelManager();

    DiscordCommandManager getCommandManager();

    RoleManager getRoleManager();

    boolean isLoaded();
}
