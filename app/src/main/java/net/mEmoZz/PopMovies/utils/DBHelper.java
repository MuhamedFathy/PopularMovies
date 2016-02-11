package net.mEmoZz.PopMovies.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_BACKDROP;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_DATE;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_ID;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_MOV_ID;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_MOV_NAME;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_OVERVIEW;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_POSTER;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.COL_VOTE;
import static net.mEmoZz.PopMovies.utils.MoviesContract.MoviesEntry.TABLE_NAME;

/**
 * Copyright (C) 2015 Mohamed Fathy
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DB_NAME = "fav_movies.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String STREAM_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MOV_ID + " TEXT, " + COL_POSTER + " TEXT, "
                + COL_BACKDROP + " TEXT, " + COL_MOV_NAME + " TEXT, "
                + COL_DATE + " TEXT, " + COL_VOTE + " TEXT, "
                + COL_OVERVIEW + " TEXT" + ")";
        db.execSQL(STREAM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertRow(String poster, String backdrop, String movId, String movName,
                             String date, String vote, String overview) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_POSTER, poster);
        values.put(COL_BACKDROP, backdrop);
        values.put(COL_MOV_ID, movId);
        values.put(COL_MOV_NAME, movName);
        values.put(COL_DATE, date);
        values.put(COL_VOTE, vote);
        values.put(COL_OVERVIEW, overview);
        long num = db.insert(TABLE_NAME, null, values);
        if (num == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteRow(String movId) {
        db = getWritableDatabase();
        db.delete(TABLE_NAME, COL_MOV_ID + " =?", new String[]{movId});
    }

    public boolean ifExist(String movId) {
        db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_MOV_ID}, COL_MOV_ID + " =?",
                new String[]{movId}, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getData() {
        db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
