package viewcontrols.dung.test14;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;

    EditText edtId, edtName, edtPrice;
    Button btnAdd, btnUpdate, btnDelete;
    ListView lvList;

    ArrayAdapter<Product> adapter = null;
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = (EditText) findViewById(R.id.edtId);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPrice = (EditText) findViewById(R.id.edtPrice);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        lvList = (ListView) findViewById(R.id.lvList);

        openOrCreateDB();
        createTable();

        adapter = new CustomAdapter(getBaseContext(), R.layout.item_listview, products);
        lvList.setAdapter(adapter);
        readData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id", edtId.getText().toString());
                values.put("name", edtName.getText().toString());
                values.put("price", edtPrice.getText().toString());

                database.insert("sanpham", null, values);
                readData();
                Toast.makeText(getBaseContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                clean();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id", edtId.getText().toString());
                values.put("name", edtName.getText().toString());
                values.put("price", edtPrice.getText().toString());

                if (database.update("sanpham", values, "id=?", new String[]{edtId.getText().toString()}) != 0) {
                    Toast.makeText(getBaseContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Sửa Không Thành Công", Toast.LENGTH_SHORT).show();
                }
                readData();
                clean();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delete = edtId.getText().toString();
                database.delete("sanpham", "id=?", new String[]{delete});
                Toast.makeText(getBaseContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                readData();
                clean();
            }
        });

    }

    public void openOrCreateDB() {
        database = openOrCreateDatabase("QLSP", MODE_APPEND, null);
    }

    public void createTable() {
        String SqlSanpham = "create table if not exists sanpham (id TEXT PRIMARY KEY, name TEXT, price TEXT)";
        database.execSQL(SqlSanpham);
    }

    public void readData() {
        products.clear();
        Cursor cursor = database.query("sanpham", null, null, null, null, null, null, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            products.add(new Product(cursor.getString(0).toString(), cursor.getString(1).toString(), cursor.getString(2).toString()));
            cursor.moveToNext();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    public void clean() {
        edtId.setText("");
        edtName.setText("");
        edtPrice.setText("");
    }
}
