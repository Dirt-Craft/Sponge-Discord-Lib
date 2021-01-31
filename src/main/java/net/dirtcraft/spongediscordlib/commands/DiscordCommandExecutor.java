package net.dirtcraft.spongediscordlib.commands;

import net.dirtcraft.spongediscordlib.exceptions.DiscordCommandException;
import net.dirtcraft.spongediscordlib.users.DiscordMember;
import net.dirtcraft.spongediscordlib.users.MessageSource;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Function;

public interface DiscordCommandExecutor {
    void execute(MessageSource source, String command, List<String> args) throws DiscordCommandException;

    default <T> Optional<T> removeIfPresent(List<String> args, Function<String, Optional<T>> valueMapper){
        ListIterator<String> iterator = args.listIterator();
        while (iterator.hasNext()){
            Optional<T> optionalT = valueMapper.apply(iterator.next());
            if (!optionalT.isPresent()) continue;
            iterator.remove();
            return optionalT;
        }
        return Optional.empty();
    }
}
