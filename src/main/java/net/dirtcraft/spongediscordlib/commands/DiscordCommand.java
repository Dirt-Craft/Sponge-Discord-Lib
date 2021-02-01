package net.dirtcraft.spongediscordlib.commands;

import net.dirtcraft.spongediscordlib.users.roles.DiscordRole;

import java.util.function.Supplier;

public interface DiscordCommand {
    static Builder builder(){
        return Builder.builderSupplier.get();
    }

    abstract class Builder {
        static Supplier<Builder> builderSupplier;

        public abstract Builder setRequiredRoles(DiscordRole... roles);

        public abstract Builder setCommandExecutor(DiscordCommandExecutor executor);

        public abstract Builder setDescription(String description);

        public abstract Builder setCommandUsage(String commandUsage);

        public abstract Builder setPreBootEnabled(boolean b);

        public abstract DiscordCommand build();
    }
}
