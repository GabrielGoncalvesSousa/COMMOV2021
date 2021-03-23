package gabriel.estg.cleancity.database.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import gabriel.estg.cleancity.database.entities.Note;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Note> __insertionAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public NoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `note_table` (`id`,`assunto`,`rua`,`cidade`,`codigo_postal`,`data`,`observacoes`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
        if (value.getAssunto() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAssunto());
        }
        if (value.getRua() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRua());
        }
        if (value.getCidade() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCidade());
        }
        if (value.getCodigo_postal() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCodigo_postal());
        }
        if (value.getData() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getData());
        }
        if (value.getObservacoes() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getObservacoes());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM note_table";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Note note, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNote.insert(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> p0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, p0);
  }

  @Override
  public Flow<List<Note>> getAlphabetizedWords() {
    final String _sql = "SELECT * FROM note_table ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"note_table"}, new Callable<List<Note>>() {
      @Override
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAssunto = CursorUtil.getColumnIndexOrThrow(_cursor, "assunto");
          final int _cursorIndexOfRua = CursorUtil.getColumnIndexOrThrow(_cursor, "rua");
          final int _cursorIndexOfCidade = CursorUtil.getColumnIndexOrThrow(_cursor, "cidade");
          final int _cursorIndexOfCodigoPostal = CursorUtil.getColumnIndexOrThrow(_cursor, "codigo_postal");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfObservacoes = CursorUtil.getColumnIndexOrThrow(_cursor, "observacoes");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Note _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpAssunto;
            _tmpAssunto = _cursor.getString(_cursorIndexOfAssunto);
            final String _tmpRua;
            _tmpRua = _cursor.getString(_cursorIndexOfRua);
            final String _tmpCidade;
            _tmpCidade = _cursor.getString(_cursorIndexOfCidade);
            final String _tmpCodigo_postal;
            _tmpCodigo_postal = _cursor.getString(_cursorIndexOfCodigoPostal);
            final String _tmpData;
            _tmpData = _cursor.getString(_cursorIndexOfData);
            final String _tmpObservacoes;
            _tmpObservacoes = _cursor.getString(_cursorIndexOfObservacoes);
            _item = new Note(_tmpId,_tmpAssunto,_tmpRua,_tmpCidade,_tmpCodigo_postal,_tmpData,_tmpObservacoes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
