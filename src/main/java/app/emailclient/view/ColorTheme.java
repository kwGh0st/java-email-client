package app.emailclient.view;

public enum ColorTheme {
    LIGHT,
    DARK,
    DEFAULT;

    public static String getCssStyle(ColorTheme colorTheme) {
        return switch (colorTheme) {
            case LIGHT -> "themeLight.css";
            case DARK -> "themeDark.css";
            case DEFAULT -> "themeDefault.css";
        };
    }
}
