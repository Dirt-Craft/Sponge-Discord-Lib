package net.dirtcraft.spongediscordlib.channels;

public interface LogChannel extends DiscordChannel {
    void sendLog(String header, String message);
}
