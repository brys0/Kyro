package brys.org.dev.lib.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeConvertor {
    static final Pattern TIMESTAMP_PATTERN = Pattern.compile("^(\\d?\\d)(?::([0-5]?\\d))?(?::([0-5]?\\d))?$");

    public static long parseTimeString(String str) throws NumberFormatException {
        long millis;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;

        Matcher m = TIMESTAMP_PATTERN.matcher(str);

        if (!m.find()) {
            throw new IllegalStateException("Unable to match " + str);
        }

        int capturedGroups = 0;
        if (m.group(1) != null)
            capturedGroups++;
        if (m.group(2) != null)
            capturedGroups++;
        if (m.group(3) != null)
            capturedGroups++;

        switch (capturedGroups) {
            case 0:
                throw new IllegalStateException("Unable to match " + str);
            case 1:
                seconds = Integer.parseInt(m.group(1));
                break;
            case 2:
                minutes = Integer.parseInt(m.group(1));
                seconds = Integer.parseInt(m.group(2));
                break;
            case 3:
                hours = Integer.parseInt(m.group(1));
                minutes = Integer.parseInt(m.group(2));
                seconds = Integer.parseInt(m.group(3));
                break;
        }

        minutes = minutes + hours * 60;
        seconds = seconds + minutes * 60;
        millis = seconds * 1000;

        return millis;
    }
    public static String convertToTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }


}


