package app.emailclient.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCssStyle(FontSize fontSize) {
        return switch (fontSize) {
            case SMALL -> "fontSmall.css";
            case MEDIUM -> "fontMedium.css";
            case BIG -> "fontBig.css";
        };
    }
}
