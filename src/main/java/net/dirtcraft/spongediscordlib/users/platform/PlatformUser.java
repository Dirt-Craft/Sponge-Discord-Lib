package net.dirtcraft.spongediscordlib.users.platform;



import java.util.Optional;
import java.util.UUID;

public interface PlatformUser {

    Optional<String> getNameIfPresent();

    UUID getUUID();

    boolean isOnline();

    Optional<PlatformPlayer> getPlatformPlayer();


    /**
     * Returns an offline player object specific of the platform
     * Sponge: User
     * Spigot: OfflinePlayer
     * Forge:  GameProfile
     * @return Platform specific representation of an offline player.
     */
    <T> T getOfflinePlayer();
}
