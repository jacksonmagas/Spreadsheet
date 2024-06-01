package Model;

/**
 * Class representing a basic cell in a spreadsheet with additional information.
 */
public class CellFormat {
    // Fields declaration
    private int id;
    private String name;
    private String lname;
    private String gmail;
    private String yahoo;
    private String phone;

    // Constructor to initialize all fields
    public CellFormat(int id, String name, String lname, String gmail, String yahoo, String phone) {
        this.id = id;
        this.name = name;
        this.lname = lname;
        this.gmail = gmail;
        this.yahoo = yahoo;
        this.phone = phone;
    }

    // Getters for all fields
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLname() {
        return lname;
    }

    public String getGmail() {
        return gmail;
    }

    public String getYahoo() {
        return yahoo;
    }

    public String getPhone() {
        return phone;
    }
}
