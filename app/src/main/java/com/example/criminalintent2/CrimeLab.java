package com.example.criminalintent2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.criminalintent2.database.CrimeBaseHelper;
import com.example.criminalintent2.database.CrimeCursorWrapper;
import com.example.criminalintent2.database.CrimeDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public List<Crime> getmCrimes(){
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){
        CrimeCursorWrapper cursor = queryCrimes(CrimeDbSchema.CrimeTable.cols.UUID+"=?", new String[]{id.toString()});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }
    public File getPhotoFile(Crime crime){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir,crime.getPhotoFileName());
    }
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.cols.UUID, crime.getmId().toString());
        values.put(CrimeDbSchema.CrimeTable.cols.TITLE, crime.getmTitle());
        values.put(CrimeDbSchema.CrimeTable.cols.DATE, crime.getmDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeDbSchema.CrimeTable.cols.SUSPECT,crime.getSuspect());
        return values;
    }
    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeDbSchema.CrimeTable.Name,null,values);
    }
    public void updateCrime(Crime crime){
        String uuidString = crime.getmId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeDbSchema.CrimeTable.Name,values, CrimeDbSchema.CrimeTable.cols.UUID+"=?", new String[]{uuidString});
    }
    private CrimeCursorWrapper queryCrimes (String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.Name,null,whereClause,whereArgs,null,null,null
                //columns -null selects all columns
                //first null = group by
                //second null = having
                //third null = order by
        );
        return new CrimeCursorWrapper(cursor);
    }
}
