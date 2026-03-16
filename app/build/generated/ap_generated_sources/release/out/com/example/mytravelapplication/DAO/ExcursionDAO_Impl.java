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
import com.example.mytravelapplication.entities.Excursions;
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
public final class ExcursionDAO_Impl implements ExcursionDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Excursions> __insertionAdapterOfExcursions;

  private final EntityDeletionOrUpdateAdapter<Excursions> __deletionAdapterOfExcursions;

  private final EntityDeletionOrUpdateAdapter<Excursions> __updateAdapterOfExcursions;

  public ExcursionDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExcursions = new EntityInsertionAdapter<Excursions>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `excursions` (`excursionID`,`excursionName`,`excursionDate`,`vacationID`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Excursions entity) {
        statement.bindLong(1, entity.getExcursionID());
        if (entity.getExcursionName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getExcursionName());
        }
        if (entity.getExcursionDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getExcursionDate());
        }
        statement.bindLong(4, entity.getVacationID());
      }
    };
    this.__deletionAdapterOfExcursions = new EntityDeletionOrUpdateAdapter<Excursions>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `excursions` WHERE `excursionID` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Excursions entity) {
        statement.bindLong(1, entity.getExcursionID());
      }
    };
    this.__updateAdapterOfExcursions = new EntityDeletionOrUpdateAdapter<Excursions>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `excursions` SET `excursionID` = ?,`excursionName` = ?,`excursionDate` = ?,`vacationID` = ? WHERE `excursionID` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Excursions entity) {
        statement.bindLong(1, entity.getExcursionID());
        if (entity.getExcursionName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getExcursionName());
        }
        if (entity.getExcursionDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getExcursionDate());
        }
        statement.bindLong(4, entity.getVacationID());
        statement.bindLong(5, entity.getExcursionID());
      }
    };
  }

  @Override
  public void insert(final Excursions excursion) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExcursions.insert(excursion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Excursions excursion) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfExcursions.handle(excursion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Excursions excursion) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfExcursions.handle(excursion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Excursions> getAllExcursions() {
    final String _sql = "SELECT * FROM excursions ORDER BY excursionID ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfExcursionID = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionID");
      final int _cursorIndexOfExcursionName = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionName");
      final int _cursorIndexOfExcursionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionDate");
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final List<Excursions> _result = new ArrayList<Excursions>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Excursions _item;
        final String _tmpExcursionName;
        if (_cursor.isNull(_cursorIndexOfExcursionName)) {
          _tmpExcursionName = null;
        } else {
          _tmpExcursionName = _cursor.getString(_cursorIndexOfExcursionName);
        }
        final String _tmpExcursionDate;
        if (_cursor.isNull(_cursorIndexOfExcursionDate)) {
          _tmpExcursionDate = null;
        } else {
          _tmpExcursionDate = _cursor.getString(_cursorIndexOfExcursionDate);
        }
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        _item = new Excursions(_tmpExcursionName,_tmpExcursionDate,_tmpVacationID);
        final int _tmpExcursionID;
        _tmpExcursionID = _cursor.getInt(_cursorIndexOfExcursionID);
        _item.setExcursionID(_tmpExcursionID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Excursions getExcursionById(final int id) {
    final String _sql = "SELECT * FROM excursions WHERE excursionID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfExcursionID = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionID");
      final int _cursorIndexOfExcursionName = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionName");
      final int _cursorIndexOfExcursionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionDate");
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final Excursions _result;
      if (_cursor.moveToFirst()) {
        final String _tmpExcursionName;
        if (_cursor.isNull(_cursorIndexOfExcursionName)) {
          _tmpExcursionName = null;
        } else {
          _tmpExcursionName = _cursor.getString(_cursorIndexOfExcursionName);
        }
        final String _tmpExcursionDate;
        if (_cursor.isNull(_cursorIndexOfExcursionDate)) {
          _tmpExcursionDate = null;
        } else {
          _tmpExcursionDate = _cursor.getString(_cursorIndexOfExcursionDate);
        }
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        _result = new Excursions(_tmpExcursionName,_tmpExcursionDate,_tmpVacationID);
        final int _tmpExcursionID;
        _tmpExcursionID = _cursor.getInt(_cursorIndexOfExcursionID);
        _result.setExcursionID(_tmpExcursionID);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Excursions> getAssociatedExcursions(final int vacationID) {
    final String _sql = "SELECT * FROM excursions WHERE vacationID = ? ORDER BY excursionID ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, vacationID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfExcursionID = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionID");
      final int _cursorIndexOfExcursionName = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionName");
      final int _cursorIndexOfExcursionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionDate");
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final List<Excursions> _result = new ArrayList<Excursions>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Excursions _item;
        final String _tmpExcursionName;
        if (_cursor.isNull(_cursorIndexOfExcursionName)) {
          _tmpExcursionName = null;
        } else {
          _tmpExcursionName = _cursor.getString(_cursorIndexOfExcursionName);
        }
        final String _tmpExcursionDate;
        if (_cursor.isNull(_cursorIndexOfExcursionDate)) {
          _tmpExcursionDate = null;
        } else {
          _tmpExcursionDate = _cursor.getString(_cursorIndexOfExcursionDate);
        }
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        _item = new Excursions(_tmpExcursionName,_tmpExcursionDate,_tmpVacationID);
        final int _tmpExcursionID;
        _tmpExcursionID = _cursor.getInt(_cursorIndexOfExcursionID);
        _item.setExcursionID(_tmpExcursionID);
        _result.add(_item);
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
