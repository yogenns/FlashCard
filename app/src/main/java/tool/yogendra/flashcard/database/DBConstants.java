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
            + SECTION_COLOR + " TEXT );";

    public static final String FLASH_CARD_ID = "ID";
    public static final String FLASH_CARD_TABLE = "FLASH_CARD_TABLE";
    public static final String FLASH_CARD_FRONT = "FRONT";
    public static final String FLASH_CARD_BACK = "BACK";
    public static final String FLASH_CARD_SECTION_ID = "SECTION_ID";
    static final String CREATE_CARD_TABLE = "CREATE TABLE " + FLASH_CARD_TABLE + " ("
            + FLASH_CARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FLASH_CARD_FRONT + " TEXT, "
            + FLASH_CARD_BACK + " TEXT, "
            + FLASH_CARD_SECTION_ID + " INTEGER, "
            + "FOREIGN KEY (" + FLASH_CARD_SECTION_ID + ") REFERENCES " + SECTION_TABLE + " (" + SECTION_ID + ") "
            + ");";
}
