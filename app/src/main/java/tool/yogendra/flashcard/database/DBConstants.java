package tool.yogendra.flashcard.database;

public class DBConstants {
    public static final String DATABASE_NAME = "FlashCard";
    static final int DATABASE_VERSION = 1;
    public static final String SECTION_ID = "ID";
    public static final String SECTION_TABLE = "SECTION_TABLE";
    public static final String SECTION_NAME = "NAME";
    public static final String SECTION_COLOR = "COLOR";
    static final String CREATE_SECTION_TABLE = "CREATE TABLE " + SECTION_TABLE + " ("
            + SECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SECTION_NAME + " TEXT, "
            + SECTION_COLOR + " CURRENT_TIMESTAMP );";
}
