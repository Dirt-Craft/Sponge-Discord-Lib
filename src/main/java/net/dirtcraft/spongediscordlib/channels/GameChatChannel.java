package net.dirtcraft.spongediscordlib.channels;

public interface GameChatChannel extends DiscordChannel {
    void sendPlayerMessage(String prefix, String playerName, String message);
}
