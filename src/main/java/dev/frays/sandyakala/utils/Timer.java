package dev.frays.sandyakala.utils;

public class Timer {
    public String id;

    public int mainHH;
    public int mainMM;
    public int mainSS;

    public int extendHH;
    public int extendMM;
    public int extendSS;

    public Timer(String id) {
        this.id = id;
    }

    public void setTimer(char type, int hh, int mm, int ss) {
        if (type == 'm') {
            this.mainHH = hh;
            this.mainMM = mm;
            this.mainSS = ss;
        } else if (type == 'e') {
            this.extendHH = hh;
            this.extendMM = mm;
            this.extendSS = ss;
        }
    }

    public void setTimer(char type, String time) throws Exception {
        int hh = 0, mm = 0, ss = 0;
        byte format = 0;

        StringBuilder token = new StringBuilder();
        for (char t : time.toCharArray()) {
            if (t >= '0' && t <= '9') {
                token.append(t);
            } else if (t == ':') {
                if (format == 0) format = 1;
                else if (format != 1) throw new Exception("Mixed up format used!");

                hh = mm;
                mm = ss;
                ss = Integer.parseInt(token.toString());
                token.setLength(0);
            } else if (t == 'h') {
                if (format == 0) format = 2;
                else if (format != 2) throw new Exception("Mixed up format used!");

                hh = Integer.parseInt(token.toString());
                token.setLength(0);
            } else if (t == 'm') {
                if (format == 0) format = 2;
                else if (format != 2) throw new Exception("Mixed up format used!");

                mm = Integer.parseInt(token.toString());
                token.setLength(0);
            } else if (t == 's') {
                if (format == 0) format = 2;
                else if (format != 2) throw new Exception("Mixed up format used!");

                ss = Integer.parseInt(token.toString());
                token.setLength(0);
            } else if (t != ' ') {
                throw new Exception("Illegal character!");
            }
        }

        // Flush token
        if (format == 0 && !token.isEmpty()) {
            ss = Integer.parseInt(token.toString());
        } else if (format == 1 && !token.isEmpty()) {
            hh = mm;
            mm = ss;
            ss = Integer.parseInt(token.toString());
        } else if (format == 2 && !token.isEmpty()) {
            throw new Exception("Unfinished format!");
        }

        // Validate time
        if (mm > 60) throw new Exception("Minute is larger than 60!");
        if (ss > 60) throw new Exception("Second is larger than 60!");

        // Set timer
        setTimer(type, hh, mm, ss);
    }

    @Override
    public String toString() {
        return String.format("Timer [ id: %s | main: %02d:%02d:%02d | extend: %02d:%02d:%02d ]",
                id,
                mainHH,   mainMM,   mainSS,
                extendHH, extendMM, extendSS);
    }
}
