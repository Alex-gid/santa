package dev.cher.santa.util;

import lombok.Getter;

@Getter
public enum BotCallBack {
    YES_BUTTON("YES_BUTTON"),
    NO_BUTTON("NO_BUTTON"),
    VOTING_FOR_CORPORATE_YES("VOTING_FOR_CORPORATE_YES"),
    VOTING_FOR_CORPORATE_NO("VOTING_FOR_CORPORATE_NO"),
    NO_BUTTON_NE_HOCHU("Не хочу быть оленем("),
    NO_BUTTON_BUY("До свидания"),
    NO_BUTTON_NE_HOCHU_YES("Точно да!"),
    BUTTON_TUROVSKY_YES("ДА"),
    BUTTON_TUROVSKY_NO("НЕТ");


    private final String callback;

    BotCallBack(String callback) {
        this.callback = callback;
    }

    public static BotCallBack fromString(String callback) {
        for (BotCallBack bcb : BotCallBack.values()) {
            if (bcb.getCallback().equalsIgnoreCase(callback)) {
                return bcb;
            }
        }
        return null;
    }
}
