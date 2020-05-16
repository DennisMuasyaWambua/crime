package com.example.criminalintent2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.criminalintent2.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.cols.TITLE));
        Long date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeDbSchema.CrimeTable.cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setmTitle(title);
        crime.setmDate(new Date(date));
        crime.setmSolved(isSolved!=0);
        crime.setSuspect(suspect);
        return crime;
    }
}
