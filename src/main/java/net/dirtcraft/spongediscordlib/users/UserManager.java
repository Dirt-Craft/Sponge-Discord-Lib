package net.dirtcraft.spongediscordlib.users;

import net.dirtcraft.spongediscordlib.users.platform.PlatformPlayer;
import net.dirtcraft.spongediscordlib.users.platform.PlatformUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserManager {
    Optional<DiscordMember> getMember(long id);

    Optional<DiscordMember> getMember(UUID player);

    Optional<DiscordMember> getMember(String s);

    Optional<PlatformUser> getUser(String s);

    Optional<PlatformUser> getUser(UUID uuid);

    List<PlatformPlayer> getPlayers();
}
