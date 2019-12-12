package io.illyria.factionsx.config;

import io.illyria.factionsx.utils.ChatUtil;

import java.util.List;

public enum Message {

    PREFIX("Command.prefix", "&4&lFactionsX &f➤ "),

    GENERAL_NOPERMISSION("General.no-permission", "&7[&4✕&7] &cYou don't have permissions!"),
    GENERAL_NOPERMISSION_SPECIFIC("General.no-permission-specific", "&7[&4✕&7] &cYou must have the permission &e%perm%&c in order to do this!"),

    CMD_RELOAD_SUCCESS("Command.reload-success", "&7[&a✔&7] &aConfig Reloaded!"),

    TIME_DAYS("Time.days", "days"),
    TIME_DAY("Time.day", "day"),
    TIME_HOURS("Time.hours", "hours"),
    TIME_HOUR("Time.hour", "hour"),
    TIME_MINUTES("Time.minutes", "minutes"),
    TIME_MINUTE("Time.minute", "minute"),
    TIME_SECONDS("Time.seconds", "seconds"),
    TIME_SECOND("Time.second", "second");

    String config, message;
    String[] messages;

    Message(String config, String message) {
        this.config = config;
        this.message = message;
    }

    Message(String config, String[] messages) {
        this.config = config;
        this.messages = messages;
    }

    public String getConfig() {
        return config;
    }

    public String getMessage() {
        return message;
    }

    public String[] getMessages() {
        return this.messages;
    }

    public void setMessages(List<String> list) {
        this.messages = list.toArray(new String[0]);
    }

    public void setMessage(String message) {
        this.message = ChatUtil.color(message);
    }

}
