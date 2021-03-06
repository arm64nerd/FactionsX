package io.illyria.factionsx.config.file.types;

import io.illyria.factionsx.config.Config;
import io.illyria.factionsx.config.Message;
import io.illyria.factionsx.config.file.CustomFile;
import io.illyria.factionsx.config.file.CustomYamlFile;
import io.illyria.factionsx.config.file.ICustomFile;
import io.illyria.factionsx.internal.FactionsBootstrap;
import io.illyria.factionsx.utils.ChatUtil;
import org.bukkit.ChatColor;

public class MessageFile extends CustomYamlFile {

    private FactionsBootstrap instance;

    public MessageFile(FactionsBootstrap instance) {
        super(instance, "translations");
        this.instance = instance;
        for (Message message : Message.values()) {
            if (message.getMessages() != null) {
                for (String string : message.getMessages()) {
                    ChatColor.translateAlternateColorCodes('&', string);
                    getConfig().addDefault(message.getConfig(), message.getMessages());
                }
            } else {
                getConfig().addDefault(message.getConfig(), message.getMessage());
            }
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public ICustomFile init() {
        this.reloadConfig();
        for (Message message : Message.values()) {
            if (message.getMessages() == null) {
                message.setMessage(getConfig().getString(ChatUtil.color(message.getConfig())));
            } else {
                message.setMessages(getConfig().getStringList(message.getConfig()));
            }
        }
        return this;
    }

    @Override
    public String getName() {
        return "messages_" + Config.LOCALE.getString();
    }
}