package io.illyria.factionsx.utils;

import io.illyria.factionsx.BukkitFactionsBootstrap;
import io.illyria.factionsx.FactionsX;
import io.illyria.factionsx.config.Config;
import io.illyria.factionsx.config.Message;
import io.illyria.factionsx.utils.hooks.HookManager;
import io.illyria.factionsx.utils.hooks.PlaceholderAPIHook;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Methods related to messages, actionbars, titles et similia.
 * Anything related to sending text feedback should be here.
 */

public class ChatUtil {

    private ChatUtil() {
        throw new AssertionError("Instantiating utility class.");
    }

    // Color messages

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    // Parse PAPI placeholders

    public static String parsePAPI(String toParse, OfflinePlayer player) {
        if (!HookManager.getInstance().getEnabledHooks().contains("PlaceholderAPI")) return toParse;
        return PlaceholderAPIHook.getPapiExt().parse(toParse, player);
    }

    public static List<String> parsePAPI(List<String> toParse, OfflinePlayer player) {
        if (!HookManager.getInstance().getEnabledHooks().contains("PlaceholderAPI")) return toParse;
        return PlaceholderAPIHook.getPapiExt().parse(toParse, player);
    }

    // Console Feedback messages

    public static void sendConsole(String str) {
        try {
            Bukkit.getConsoleSender().sendMessage(color(str));
        } catch (Exception ex) { // no api for this? strip color and println
            System.out.println(stripColor(str));
        }
    }

    public static void debug(String str) {
        if (Config.DEBUG.getBoolean())
            sendConsole(Message.PREFIX_DEBUG.getMessage() + str);
    }

    public static void error(String str) {
        sendConsole(Message.PREFIX_ERROR.getMessage() + str);
    }

    // StripColor method, independent from ChatColor, same regEx
    private static String stripColor(final String input) {
        if (input == null) return null;
        char colorChar = '\u00A7';
        return Pattern.compile("(?i)" + colorChar + "[0-9A-FK-OR]").matcher(input).replaceAll("");
    }

    // Send Title only

    public static void sendTitle(Player[] player, String title, int fadeIn, int stay, int fadeOut) {
        sendTitle(player, title, "", fadeIn, stay, fadeOut);
    }

    public static void sendTitle(Player[] player, String title) {
        sendTitle(player, title, 10, 20, 10);
    }

    // Send Subtitle only

    public static void sendSubtitle(Player[] player, String subtitle, int fadeIn, int stay, int fadeOut) {
        sendTitle(player, "", subtitle, fadeIn, stay, fadeOut);
    }

    public static void sendSubtitle(Player[] player, String subtitle) {
        sendSubtitle(player, subtitle, 10, 20, 10);
    }

    // Send full Title+Subtitle

    public static void sendTitle(Player[] player, @Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        JSONMessage.create(color(title != null ? title : ""))
                .title(fadeIn, stay, fadeOut, player);
        if (subtitle != null && subtitle.length() > 0) {
            JSONMessage.create(color(subtitle))
                    .subtitle(player);
        }
    }

    // Send normal ActionBar

    public static void sendActionBar(Player[] players, String message) {
        JSONMessage.actionbar(color(message), players);
    }

    // Send ActionBar with duration

    public static void sendActionBar(Player[] players, String message, int duration) {
        sendActionBar(players, message);
        if (duration >= 0) {
            // Allow ActionBar messages to be shorter than 3 seconds.
            Bukkit.getScheduler().runTaskLater(BukkitFactionsBootstrap.getInstance(), () -> sendActionBar(players, ""), duration + 1);
        }
        // Re-send ActionBar every 3 seconds so it doesn't go away.
        while (duration > 40) {
            duration -= 40;
            Bukkit.getScheduler().runTaskLater(BukkitFactionsBootstrap.getInstance(), () -> sendActionBar(players, message), duration);
        }
    }

}
