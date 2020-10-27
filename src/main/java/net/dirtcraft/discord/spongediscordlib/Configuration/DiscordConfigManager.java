package net.dirtcraft.discord.spongediscordlib.Configuration;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

public class DiscordConfigManager {
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationOptions options;
    private DiscordConfiguration cfg;

    public DiscordConfigManager(ConfigurationLoader<CommentedConfigurationNode> loader) {
        this.loader = loader;
        options = ConfigurationOptions.defaults().setShouldCopyDefaults(true);
        update();
    }

    private void update() {
        try {
            CommentedConfigurationNode node = loader.load(options);
            DiscordConfiguration cfg = node.getValue(TypeToken.of(DiscordConfiguration.class), new DiscordConfiguration());
            loader.save(node);
            this.cfg = cfg;
        } catch (IOException | ObjectMappingException exception) {
            exception.printStackTrace();
        }
    }
}