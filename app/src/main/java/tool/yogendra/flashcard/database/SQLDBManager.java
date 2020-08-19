package tool.yogendra.flashcard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.yogendra.flashcard.utils.Constants;

public class SQLDBManager extends SQLiteOpenHelper {

    private static final String TAG = "SQLDBManager";

    public SQLDBManager(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "SECTION TABLE [" + DBConstants.CREATE_SECTION_TABLE + "]");
        Log.i(TAG, "FLASH CARD TABLE [" + DBConstants.CREATE_CARD_TABLE + "]");

        sqLiteDatabase.execSQL(DBConstants.CREATE_SECTION_TABLE);
        sqLiteDatabase.execSQL(DBConstants.CREATE_CARD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //SECTION Table, Insert, Read All, Delete
    public int insertSection(ContentValues contentValues) {
        long id = this.getWritableDatabase().insert(DBConstants.SECTION_TABLE, "", contentValues);
        return (int) id;
    }

    public List<SectionParams> getAllSectionsDataList() {
        List<SectionParams> paramsList = new ArrayList<>();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DBConstants.SECTION_TABLE);
        Cursor cursor = sqLiteQueryBuilder.query(this.getWritableDatabase(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            SectionParams param = new SectionParams(
                    cursor.getInt(cursor.getColumnIndex(DBConstants.SECTION_ID)),
                    cursor.getString(cursor.getColumnIndex(DBConstants.SECTION_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBConstants.SECTION_COLOR))
            );
            paramsList.add(param);
        }
        return paramsList;
    }

    public void deleteSection(int sectionId) {
        this.getWritableDatabase().delete(DBConstants.SECTION_TABLE, DBConstants.SECTION_ID + "=?", new String[]{String.valueOf(sectionId)});
    }

    //FLASH CARD Table, Insert, Read All, Delete
    public int insertFlashCard(ContentValues contentValues) {
        long id = this.getWritableDatabase().insert(DBConstants.FLASH_CARD_TABLE, "", contentValues);
        return (int) id;
    }

    public List<FlashCardParams> getAllFlashCardsDataList() {
        List<FlashCardParams> paramsList = new ArrayList<>();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DBConstants.FLASH_CARD_TABLE);
        Cursor cursor = sqLiteQueryBuilder.query(this.getWritableDatabase(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            //Get Section Details
            SQLiteQueryBuilder sqLiteQueryBuilderSection = new SQLiteQueryBuilder();
            sqLiteQueryBuilderSection.setTables(DBConstants.SECTION_TABLE);
            Cursor sectionCursor = sqLiteQueryBuilderSection.query(this.getWritableDatabase(), null,
                    DBConstants.SECTION_ID + "=" + cursor.getInt(cursor.getColumnIndex(DBConstants.FLASH_CARD_SECTION_ID)), null, null, null, null);
            String sectionName = "";
            String sectionColor = "";
            if (sectionCursor.moveToNext()) {
                sectionName = sectionCursor.getString(sectionCursor.getColumnIndex(DBConstants.SECTION_NAME));
                sectionColor = sectionCursor.getString(sectionCursor.getColumnIndex(DBConstants.SECTION_COLOR));
            }
            sectionCursor.close();
            FlashCardParams param = new FlashCardParams(
                    cursor.getInt(cursor.getColumnIndex(DBConstants.FLASH_CARD_ID)),
                    cursor.getString(cursor.getColumnIndex(DBConstants.FLASH_CARD_FRONT)),
                    cursor.getString(cursor.getColumnIndex(DBConstants.FLASH_CARD_BACK)),
                    sectionColor, sectionName
            );
            paramsList.add(param);
        }
        return paramsList;
    }

    public void deleteFlashCard(int flashCardId) {
        this.getWritableDatabase().delete(DBConstants.FLASH_CARD_TABLE, DBConstants.FLASH_CARD_ID + "=?", new String[]{String.valueOf(flashCardId)});
    }

    public Map<String, Integer> getAllAccountsSummary() {
        Map<String, Integer> records = new HashMap<>();
        int numOfSections = 0;
        int numOfFlashCards = 0;
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DBConstants.SECTION_TABLE);
        Cursor cursor = sqLiteQueryBuilder.query(this.getWritableDatabase(), new String[]{"COUNT (*)"},
                null, null, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    numOfSections = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        sqLiteQueryBuilder.setTables(DBConstants.FLASH_CARD_TABLE);
        cursor = sqLiteQueryBuilder.query(this.getWritableDatabase(), new String[]{"COUNT (*)"},
                null, null, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    numOfFlashCards = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }
        records.put(Constants.SUMMARY_FLASH_CARD_COUNT_KEY, numOfFlashCards);
        records.put(Constants.SUMMARY_SECTION_COUNT_KEY, numOfSections);
        return records;
    }
}
