package ads.pdm.avaliacaoddm0923;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Feito por Herick Victor Rodrigues | SC301018X
    private Button btnInserir;
    private ListView lista;
    SQLiteDatabase db;
    AdapterTextos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInserir = findViewById(R.id.btnInserir);
        lista = findViewById(R.id.lista);
        btnInserir.setOnClickListener(new btnListener());
        listaListener listaListener = new listaListener();
        lista.setOnItemClickListener(listaListener);
        lista.setOnItemLongClickListener(listaListener);

        db = openOrCreateDatabase("textos", MODE_PRIVATE, null);
        String query;
        query = "create table if not exists textos(texto VARCHAR, cor int)";
        db.execSQL(query);
        Cursor cursor = db.rawQuery("select _rowid_ _id, texto, cor from textos", null);
        adapter = new AdapterTextos(this, cursor);
        lista.setAdapter(adapter);
    }

    private class btnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), InserirActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String texto = intent.getStringExtra("texto");
                int cor = intent.getIntExtra("cor", 0);
                String query = "insert into textos(texto, cor) values('";
                query += texto + "'";
                query += ", " + cor + ")";
                db.execSQL(query);
                Cursor cursor = db.rawQuery("select _rowid_ _id, texto, cor from textos", null);
                adapter.changeCursor(cursor);
            }
        }
    }

    private class listaListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor cursor = (Cursor) adapter.getItem(i);
            String texto = cursor.getString(cursor.getColumnIndexOrThrow("texto"));
            int intCor = cursor.getInt(cursor.getColumnIndexOrThrow("cor"));
            String cor = "";
            switch (intCor) {
                case Color.RED:
                    cor = "vermelho";
                    break;
                case Color.GREEN:
                    cor = "verde";
                    break;
                case Color.BLUE:
                    cor = "azul";
                    break;
            }

            Toast.makeText(MainActivity.this, texto + "\n" + cor, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor cursor = (Cursor) adapter.getItem(i);
            String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            String query = "delete from textos where _rowid_ = " + id;
            db.execSQL(query);
            cursor = db.rawQuery("select _rowid_ _id, texto, cor from textos", null);
            adapter.changeCursor(cursor);
            Toast.makeText(MainActivity.this, "Texto apagado com sucesso!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}