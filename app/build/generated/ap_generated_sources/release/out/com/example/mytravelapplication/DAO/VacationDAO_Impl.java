package com.example.mytravelapplication.DAO;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.mytravelapplication.entities.Vacations;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VacationDAO_Impl implements VacationDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Vacations> __insertionAdapterOfVacations;

  private final EntityDeletionOrUpdateAdapter<Vacations> __deletionAdapterOfVacations;

  private final EntityDeletionOrUpdateAdapter<Vacations> __updateAdapterOfVacations;

  public VacationDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVacations = new EntityInsertionAdapter<Vacations>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `vacations` (`vacationID`,`vacationName`,`hotel`,`startDate`,`endDate`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Vacations entity) {
        statement.bindLong(1, entity.getVacationID());
        if (entity.getVacationName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getVacationName());
        }
        if (entity.getHotel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getHotel());
        }
        if (entity.getStartDate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStartDate());
        }
        if (entity.getEndDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEndDate());
        }
      }
    };
    this.__deletionAdapterOfVacations = new EntityDeletionOrUpdateAdapter<Vacations>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vacations` WHERE `vacationID` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Vacations entity) {
        statement.bindLong(1, entity.getVacationID());
      }
    };
    this.__updateAdapterOfVacations = new EntityDeletionOrUpdateAdapter<Vacations>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `vacations` SET `vacationID` = ?,`vacationName` = ?,`hotel` = ?,`startDate` = ?,`endDate` = ? WHERE `vacationID` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Vacations entity) {
        statement.bindLong(1, entity.getVacationID());
        if (entity.getVacationName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getVacationName());
        }
        if (entity.getHotel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getHotel());
        }
        if (entity.getStartDate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStartDate());
        }
        if (entity.getEndDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEndDate());
        }
        statement.bindLong(6, entity.getVacationID());
      }
    };
  }

  @Override
  public void insert(final Vacations vacation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfVacations.insert(vacation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Vacations vacation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfVacations.handle(vacation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Vacations vacation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfVacations.handle(vacation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Vacations> getAllVacations() {
    final String _sql = "SELECT * FROM vacations ORDER BY vacationID ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final int _cursorIndexOfVacationName = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationName");
      final int _cursorIndexOfHotel = CursorUtil.getColumnIndexOrThrow(_cursor, "hotel");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final List<Vacations> _result = new ArrayList<Vacations>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Vacations _item;
        final String _tmpVacationName;
        if (_cursor.isNull(_cursorIndexOfVacationName)) {
          _tmpVacationName = null;
        } else {
          _tmpVacationName = _cursor.getString(_cursorIndexOfVacationName);
        }
        final String _tmpHotel;
        if (_cursor.isNull(_cursorIndexOfHotel)) {
          _tmpHotel = null;
        } else {
          _tmpHotel = _cursor.getString(_cursorIndexOfHotel);
        }
        final String _tmpStartDate;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmpStartDate = null;
        } else {
          _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
        }
        final String _tmpEndDate;
        if (_cursor.isNull(_cursorIndexOfEndDate)) {
          _tmpEndDate = null;
        } else {
          _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
        }
        _item = new Vacations(_tmpVacationName,_tmpHotel,_tmpStartDate,_tmpEndDate);
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        _item.setVacationID(_tmpVacationID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Vacations getVacationById(final int id) {
    final String _sql = "SELECT * FROM vacations WHERE vacationID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final int _cursorIndexOfVacationName = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationName");
      final int _cursorIndexOfHotel = CursorUtil.getColumnIndexOrThrow(_cursor, "hotel");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final Vacations _result;
      if (_cursor.moveToFirst()) {
        final String _tmpVacationName;
        if (_cursor.isNull(_cursorIndexOfVacationName)) {
          _tmpVacationName = null;
        } else {
          _tmpVacationName = _cursor.getString(_cursorIndexOfVacationName);
        }
        final String _tmpHotel;
        if (_cursor.isNull(_cursorIndexOfHotel)) {
          _tmpHotel = null;
        } else {
          _tmpHotel = _cursor.getString(_cursorIndexOfHotel);
        }
        final String _tmpStartDate;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmpStartDate = null;
        } else {
          _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
        }
        final String _tmpEndDate;
        if (_cursor.isNull(_cursorIndexOfEndDate)) {
          _tmpEndDate = null;
        } else {
          _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
        }
        _result = new Vacations(_tmpVacationName,_tmpHotel,_tmpStartDate,_tmpEndDate);
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        _result.setVacationID(_tmpVacationID);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
