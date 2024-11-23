package dev.frays.sandyakala.enums;

public enum TimerStatus {
    NORMAL  (4,  "#94E6A2", "#598A61", "#1E2E20"),
    EXTEND  (8,  "#E6E594", "#8A8959", "#2E2E1E"),
    OVERTIME(12, "#E694A7", "#8A5964", "#2E1E21");

    private final int id;
    private final String colorActive;
    private final String colorInactive;
    private final String background;
    public TimeStatus timeStatus;

    private TimerStatus(int id, String active, String inactive, String bg) {
        this.id = id;
        this.colorActive = active;
        this.colorInactive = inactive;
        this.background = bg;
        this.timeStatus = TimeStatus.STOP;
    }

    public int getId() {
        return id;
    }

    public String getColorActive() {
        if (timeStatus == TimeStatus.PLAY) return colorActive;
        else return timeStatus.colorActive;
    }

    public String getColorInactive() {
        if (timeStatus == TimeStatus.PLAY) return colorInactive;
        else return timeStatus.colorInactive;
    }

    public String getBackground() {
        if (timeStatus == TimeStatus.PLAY) return background;
        else return timeStatus.background;
    }

    public int getTimeStatus() {
        return this.id | this.timeStatus.id;
    }
}
