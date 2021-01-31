package net.dirtcraft.spongediscordlib.channels;

import net.dirtcraft.spongediscordlib.users.DiscordMember;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.concurrent.CompletableFuture;

public interface DiscordChannel {
    void sendMessage(String header, String message);

    CompletableFuture<Message> sendMessage(MessageEmbed embed);

    CompletableFuture<Message> sendMessage(String message);

    CompletableFuture<Message> sendMessage(MessageEmbed message, int delay);

    CompletableFuture<Message> sendMessage(String message, int delay);

    void sendMessage(DiscordMember source, String header, String message);
}
