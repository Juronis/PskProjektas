package lt.macrosoft.jaxrs;

/**
 * Created by Arnas on 2016-05-26.
 */
public enum Error {
    MEMBER_NOT_ENOUGH_CREDIT(10, "Neužtenka kreditų vasarnamiui."),
    MEMBER_COULND_NOT_EXTRACT_FROM_HEADER(11, "Nepavyko gauti naudotojo objekto iš Autorizacijos header'io"),
    RESERVATION_DATE_UNAVAILABLE(20, "Ši savaitė jau užimta."),
    DB_RESERVATION_PERSIST(21, "Nepavyko išsaugoti įrašo duomenų bazėje"),
    DB_APPROVAL_NOT_FOUND(22, "APPROVALS lentoje nerastas įrašas. Ar buvo išsiųstas pakvietimas?"),
    DB_APPROVAL_LIST_NOT_FOUND(23, "APPROVALS lentoje nerastas įrašų sąrašas. Ar buvo išsiųsti pakvietimai?"),
    DB_SUMMERHOUSE_PERSIST(24, "Nepavyko išsaugoti įrašo duomenų bazėje"),
    DB_DELETE(25, "Nepavyko ištrinti įrašo duomenų bazėje nes jo ir nebuvo"),
    RESERVATION_RESERVATIONS_NOT_FOUND(26, "Nerastos rezervacijos duotame laiko tarpe");
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
