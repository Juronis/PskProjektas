package lt.macrosoft.jaxrs;

/**
 * Created by Arnas on 2016-05-26.
 */
public enum Error {
    MEMBER_NOT_ENOUGH_CREDIT(10, "Neužtenka kreditų vasarnamiui."),
    RESERVATION_DATE_UNAVAILABLE(20, "Ši savaitė jau užimta."),
    DB_RESERVATION_PERSIST(21, "Nepavyko išsaugoti įrašo duomenų bazėje");
    private final int code;
    private final String message;

    private Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
