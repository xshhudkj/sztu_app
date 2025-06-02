package me.wcy.music.storage.db.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import me.wcy.music.storage.db.entity.SongEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlaylistDao_Impl implements PlaylistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SongEntity> __insertionAdapterOfSongEntity;

  private final EntityDeletionOrUpdateAdapter<SongEntity> __deletionAdapterOfSongEntity;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public PlaylistDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSongEntity = new EntityInsertionAdapter<SongEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `play_list` (`type`,`song_id`,`title`,`artist`,`artist_id`,`album`,`album_id`,`album_cover`,`duration`,`uri`,`path`,`file_name`,`file_size`,`unique_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SongEntity entity) {
        statement.bindLong(1, entity.getType());
        statement.bindLong(2, entity.getSongId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getArtist());
        statement.bindLong(5, entity.getArtistId());
        statement.bindString(6, entity.getAlbum());
        statement.bindLong(7, entity.getAlbumId());
        statement.bindString(8, entity.getAlbumCover());
        statement.bindLong(9, entity.getDuration());
        statement.bindString(10, entity.getUri());
        statement.bindString(11, entity.getPath());
        statement.bindString(12, entity.getFileName());
        statement.bindLong(13, entity.getFileSize());
        statement.bindString(14, entity.getUniqueId());
      }
    };
    this.__deletionAdapterOfSongEntity = new EntityDeletionOrUpdateAdapter<SongEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `play_list` WHERE `unique_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SongEntity entity) {
        statement.bindString(1, entity.getUniqueId());
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM play_list";
        return _query;
      }
    };
  }

  @Override
  public void insert(final SongEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSongEntity.insert(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<SongEntity> list) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSongEntity.insert(list);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final SongEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSongEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clear() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfClear.release(_stmt);
    }
  }

  @Override
  public List<SongEntity> queryAll() {
    final String _sql = "SELECT * FROM play_list";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "song_id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfArtistId = CursorUtil.getColumnIndexOrThrow(_cursor, "artist_id");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfAlbumId = CursorUtil.getColumnIndexOrThrow(_cursor, "album_id");
      final int _cursorIndexOfAlbumCover = CursorUtil.getColumnIndexOrThrow(_cursor, "album_cover");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "file_name");
      final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
      final int _cursorIndexOfUniqueId = CursorUtil.getColumnIndexOrThrow(_cursor, "unique_id");
      final List<SongEntity> _result = new ArrayList<SongEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final SongEntity _item;
        final int _tmpType;
        _tmpType = _cursor.getInt(_cursorIndexOfType);
        final long _tmpSongId;
        _tmpSongId = _cursor.getLong(_cursorIndexOfSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        final long _tmpArtistId;
        _tmpArtistId = _cursor.getLong(_cursorIndexOfArtistId);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        final long _tmpAlbumId;
        _tmpAlbumId = _cursor.getLong(_cursorIndexOfAlbumId);
        final String _tmpAlbumCover;
        _tmpAlbumCover = _cursor.getString(_cursorIndexOfAlbumCover);
        final long _tmpDuration;
        _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
        final String _tmpUri;
        _tmpUri = _cursor.getString(_cursorIndexOfUri);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpFileSize;
        _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
        _item = new SongEntity(_tmpType,_tmpSongId,_tmpTitle,_tmpArtist,_tmpArtistId,_tmpAlbum,_tmpAlbumId,_tmpAlbumCover,_tmpDuration,_tmpUri,_tmpPath,_tmpFileName,_tmpFileSize);
        final String _tmpUniqueId;
        _tmpUniqueId = _cursor.getString(_cursorIndexOfUniqueId);
        _item.setUniqueId(_tmpUniqueId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public SongEntity queryByUniqueId(final String uniqueId) {
    final String _sql = "SELECT * FROM play_list WHERE unique_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uniqueId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "song_id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfArtistId = CursorUtil.getColumnIndexOrThrow(_cursor, "artist_id");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfAlbumId = CursorUtil.getColumnIndexOrThrow(_cursor, "album_id");
      final int _cursorIndexOfAlbumCover = CursorUtil.getColumnIndexOrThrow(_cursor, "album_cover");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "file_name");
      final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
      final int _cursorIndexOfUniqueId = CursorUtil.getColumnIndexOrThrow(_cursor, "unique_id");
      final SongEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpType;
        _tmpType = _cursor.getInt(_cursorIndexOfType);
        final long _tmpSongId;
        _tmpSongId = _cursor.getLong(_cursorIndexOfSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        final long _tmpArtistId;
        _tmpArtistId = _cursor.getLong(_cursorIndexOfArtistId);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        final long _tmpAlbumId;
        _tmpAlbumId = _cursor.getLong(_cursorIndexOfAlbumId);
        final String _tmpAlbumCover;
        _tmpAlbumCover = _cursor.getString(_cursorIndexOfAlbumCover);
        final long _tmpDuration;
        _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
        final String _tmpUri;
        _tmpUri = _cursor.getString(_cursorIndexOfUri);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpFileSize;
        _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
        _result = new SongEntity(_tmpType,_tmpSongId,_tmpTitle,_tmpArtist,_tmpArtistId,_tmpAlbum,_tmpAlbumId,_tmpAlbumCover,_tmpDuration,_tmpUri,_tmpPath,_tmpFileName,_tmpFileSize);
        final String _tmpUniqueId;
        _tmpUniqueId = _cursor.getString(_cursorIndexOfUniqueId);
        _result.setUniqueId(_tmpUniqueId);
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
