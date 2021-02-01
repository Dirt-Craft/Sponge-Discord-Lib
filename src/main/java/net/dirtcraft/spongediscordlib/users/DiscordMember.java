package net.dirtcraft.spongediscordlib.users;

import net.dirtcraft.spongediscordlib.users.platform.PlatformPlayer;
import net.dirtcraft.spongediscordlib.users.platform.PlatformUser;
import net.dirtcraft.spongediscordlib.users.roles.DiscordRole;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Optional;

public interface DiscordMember extends Member {

    Optional<PlatformPlayer> getPlayer();

    Optional<PlatformUser> getPlayerData();

    void sendPrivateMessage(MessageEmbed embed);

    void sendPrivateMessage(String message);

    boolean isStaff();

    boolean isMuted();

    boolean isDonor();

    boolean isBoosting();

    boolean isVerified();

    boolean hasRole(DiscordRole role);

    void setRoleIfAbsent(DiscordRole role);

    void removeRoleIfPresent(DiscordRole role);

    void tryChangeNickname(String name);

    DiscordRole getHighestRank();

    String getChevron();

    String getNameStyle();

    void reloadRoles();
}
