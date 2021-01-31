package net.dirtcraft.spongediscordlib.channels;

public interface ChannelManager {

    GameChatChannel getGameChat();

    LogChannel getLogChannel();

    DiscordChannel getChannel(long channel);

    DiscordChannel getChannel(long channel, boolean isPrivate);
}
