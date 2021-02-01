package net.dirtcraft.spongediscordlib;

import net.dirtcraft.spongediscordlib.commands.DiscordCommand;
import net.dv8tion.jda.api.JDA;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class DiscordApiProvider {
    private static Queue<Consumer<JDA>> onJdaInit = new ConcurrentLinkedQueue<>();
    private static Queue<Consumer<DiscordApi>> onApiInit = new ConcurrentLinkedQueue<>();
    private static DiscordApi provider;

    private static void setProvider(DiscordApi provider, Supplier<DiscordCommand.Builder> builderFactory) throws NoSuchFieldException, IllegalAccessException {
        DiscordApiProvider.provider = provider;
        Class<DiscordCommand.Builder> clazz = DiscordCommand.Builder.class;
        Field field = clazz.getDeclaredField("builderSupplier");
        field.setAccessible(true);
        field.set(null, builderFactory);
        DiscordApiProvider.onJdaInit.forEach(callback->callback.accept(provider.getJda()));
        DiscordApiProvider.onApiInit.forEach(callback->callback.accept(provider));
        DiscordApiProvider.onJdaInit = null;
        DiscordApiProvider.onApiInit = null;

    }

    public static Optional<DiscordApi> getApi(){
        return Optional.ofNullable(provider);
    }

    public static Optional<JDA> getJda(){
        return getApi().map(DiscordApi::getJda);
    }

    public static void onJdaInit(Consumer<JDA> callback){
        if (provider != null) callback.accept(provider.getJda());
        else DiscordApiProvider.onJdaInit.add(callback);
    }

    public static void onApiInit(Consumer<DiscordApi> callback){
        if (provider != null) callback.accept(provider);
        else DiscordApiProvider.onApiInit.add(callback);
    }
}
