package me.brotherhong.fishinglife2;

import me.brotherhong.fishinglife2.configs.ConfigType;
import me.brotherhong.fishinglife2.utils.TextUtil;
import org.bukkit.entity.Player;

public enum Messages {

    PREFIX("prefix"),

    // general
    YES("general.yes-msg"),
    NO("general.no-msg"),
    CAUGHT_MESSAGE("general.caught-message"),
    BROADCAST_MESSAGE("general.broadcast-message"),
    RELOAD("general.reload"),
    ITEM_FORMAT("general.drop-format"),
    BOUNDARY_COLOR("general.boundary-color"),
    INPUT_QUERY_CHANCE("general.input-query-chance"),
    INPUT_QUERY_AMOUNT("general.input-query-amount"),
    LIST_TITLE("general.list-title"),

    // warning
    NO_PERMISSION("warning.no-permission"),
    REGION_NOT_FOUND("warning.region-not-found"),
    REGION_CONFLICT("warning.region-conflict"),
    REGION_NAME_REPEAT("warning.region-name-repeat"),
    CANCEL_DELETION("warning.cancel-delete"),
    CANCEL_EDIT("warning.cancel-edit"),
    SELECT_FIRST("warning.select-first"),
    WRONG_VALUE_CHANCE("warning.wrong-value-chance"),
    WRONG_VALUE_BROADCAST("warning.wrong-value-broadcast"),
    WRONG_VALUE_AMOUNT("warning.wrong-value-amount"),
    ADD_AIR("warning.add-air"),
    AMOUNT_OVERFLOW("warning.amount-overflow"),

    // success
    SUCCESS_CREATE("success.create"),
    SUCCESS_DELETE_DROP("success.delete-drop"),
    SUCCESS_DELETE_REGION("success.delete-region"),
    SUCCESS_ADD_DROP("success.add-drop"),
    SUCCESS_EDIT_CHANCE("success.edit-chance"),
    SUCCESS_EDIT_AMOUNT("success.edit-amount"),
    SUCCESS_TP("success.tp"),
    SUCCESS_RESELECT("success.reselect"),

    // menu
    MENU_DISPLAY_NAME("menu.display-name"),
    PREVIOUS_PAGE("menu.previous-page"),
    NEXT_PAGE("menu.next-page"),
    CURRENT_PAGE("menu.current-page"),
    NO_PREVIOUS_PAGE("menu.no-previous-page"),
    NO_NEXT_PAGE("menu.no-next-page"),
    ITEM_CHANCE("menu.item.chance"),
    ITEM_BROADCAST("menu.item.broadcast"),
    EDIT_CHANCE("menu.item.edit-chance"),
    EDIT_AMOUNT("menu.item.edit-amount"),
    ITEM_DELETE("menu.item.delete"),
    EDIT_BROADCAST("menu.item.edit-broadcast"),
    ;


    private final String path;

    Messages(String path) {
        this.path = path;
    }

    private String getByPath(String path) {
        return TextUtil.colorize(FishingLife2.getInstance().getConfigManager()
                .getConfigHandler(ConfigType.MESSAGES)
                .getConfig().getString(path));
    }

    public String get(String... replaces) {
        String msg = getByPath(path);
        return replace(msg, replaces);
    }

    public void send(Player p, String... replaces) {
        String msg = getByPath(path);
        p.sendMessage(PREFIX.get() + " " + replace(msg, replaces));
    }

    public void broadcast(String... replacer) {
        String msg = getByPath(path);
        FishingLife2.getInstance().getServer().broadcastMessage(PREFIX.get() + " " + replace(msg, replacer));
    }

    /* Placeholders
    #  in order of argument priority
    #   %player% - player's display name
    #   %chance% - chance of the drop
    #   %broadcast% - broadcast or not
    #   %drop% - drop's display name
    #   %region_name% - region's name
    #   %region_amount% - the amount of fishing regions
    #   %current_page% - current page
    * */

    private String replace(String str, String[] replaces) {
        int idx = 0;
        if (idx < replaces.length && str.contains("%player%")) {
            str = str.replaceAll("%player%", replaces[idx]);
            idx++;
        }
        if (idx < replaces.length && str.contains("%chance%")) {
            str = str.replaceAll("%chance%", replaces[idx]);
            idx++;
        }
        if (idx < replaces.length && str.contains("%broadcast%")) {
            str = str.replaceAll("%broadcast%", replaces[idx]);
            idx++;
        }
        if (idx < replaces.length && str.contains("%drop%")) {
            str = str.replaceAll("%drop%", replaces[idx]);
            idx++;
        }
        if (idx < replaces.length && str.contains("%region_name%")) {
            str = str.replaceAll("%region_name%", replaces[idx]);
            idx++;
        }
        if (idx < replaces.length && str.contains("%region_amount%")) {
            str = str.replaceAll("%region_amount%", replaces[idx]);
            idx++;
        }
        if (idx < replaces.length && str.contains("%current_page%")) {
            str = str.replaceAll("%current_page%", replaces[idx]);
            idx++;
        }
        return str;
    }
}
