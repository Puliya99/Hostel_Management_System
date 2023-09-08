package lk.ijse.hostelManagementSystem.util.regex;

import java.util.regex.Pattern;

public class RegExFactory {
    private static RegExFactory regExFactory;

    private final Pattern namePattern;
    private final Pattern registrationIdPattern;
    private final Pattern emailPattern;
    private final Pattern cityPattern;
    private final Pattern mobilePattern;
    private final Pattern doublePattern;
    private final Pattern passwordPattern;
    private final Pattern roomPattern;
    private final Pattern integerPattern;


    private RegExFactory() {
        namePattern = Pattern.compile("^[a-zA-Z]{4,60}$");
        registrationIdPattern = Pattern.compile("^[I][T][0-1]{1,}$");
        emailPattern = Pattern.compile("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])([a-zA-Z]+)$");
        cityPattern = Pattern.compile("[a-zA-Z]{4,}$");
        doublePattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");
        mobilePattern = Pattern.compile("^\\+?\\d{10}$");
        passwordPattern = Pattern.compile("[aA-zZ0-9@]{8,20}$");
        roomPattern = Pattern.compile("^[R][M][-][0-9]{1,}$");
        integerPattern = Pattern.compile("^\\d+$");
    }

    public static RegExFactory getInstance() {
        return regExFactory == null ? regExFactory = new RegExFactory() : regExFactory;
    }

    public Pattern getPattern(RegExType regExType) throws RuntimeException {
        switch (regExType) {
            case NAME:
                return namePattern;
            case STUDENT_ID:
                return registrationIdPattern;
            case PASSWORD:
                return passwordPattern;
            case MOBILE:
                return mobilePattern;
            case DOUBLE:
                return doublePattern;
            case CITY:
                return cityPattern;
            case ROOM_ID:
                return roomPattern;
            case INTEGER:
                return integerPattern;
            default:
                throw new RuntimeException("Pattern not found");
        }
    }

}
