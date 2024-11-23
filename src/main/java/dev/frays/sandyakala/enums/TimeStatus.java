package dev.frays.sandyakala.enums;

public enum TimeStatus {
    STOP (0, "#888888", "#888888", "#303030"),
    PLAY (1, "#94E6A2", "#598A61", "#598A61"),
    PAUSE(2, "#94C7E6", "#59778A", "#1E282E");

    public final int id;
    public final String colorActive;
    public final String colorInactive;
    public final String background;

    private TimeStatus(int id, String active, String inactive, String bg) {
        this.id = id;
        this.colorActive = active;
        this.colorInactive = inactive;
        this.background = bg;
    }
}
