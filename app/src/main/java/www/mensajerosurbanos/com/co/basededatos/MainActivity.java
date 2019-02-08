package www.mensajerosurbanos.com.co.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edit_code, edit_description, edit_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_code = findViewById(R.id.edit_number);
        edit_description = findViewById(R.id.edit_name);
        edit_price = findViewById(R.id.edit_numberD);
    }

    public void Register (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String code = edit_code.getText().toString();
        String description = edit_description.getText().toString();
        String price = edit_price.getText().toString();

        if (!code.isEmpty() && !description.isEmpty() && !price.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("code", code);
            register.put("description", description);
            register.put("price", price);

            BaseDeDatos.insert("articulos", null, register);

            BaseDeDatos.close();

            edit_code.setText("");
            edit_description.setText("");
            edit_price.setText("");

            Toast.makeText(this, R.string.exito,Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, R.string.llenar_campo, Toast.LENGTH_SHORT).show();
        }
    }

    //metodo de consulta
    public void Search (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null , 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String code = edit_code.getText().toString();

        if (!code.isEmpty()){
            Cursor fila  = BaseDeDatos.rawQuery
                    ("select descripcion, precio from articulos where codigo=" + code, null);

            if (fila.moveToFirst()){
                edit_code.setText(fila.getString(0));
                edit_description.setText(fila.getString(1));
                edit_price.setText(fila.getString(2));
                BaseDeDatos.close();

            }else{
                Toast.makeText(this, R.string.no_exist,Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else{
            Toast.makeText(this, R.string.llenar_code,Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para eliminar
    public void Delete (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String code = edit_code.getText().toString();

        if(!code.isEmpty()){

            int quantity = BaseDeDatos.delete("articulos", "codigo=" + code, null);
            BaseDeDatos.close();

            edit_code.setText("");
            edit_description.setText("");
            edit_price.setText("");

            if(quantity == 1){
                Toast.makeText(this, R.string.exit_delete,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, R.string.articulo_null,Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, R.string.code_into,Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para modificar
    public void Update (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String code = edit_code.getText().toString();
        String description = edit_description.getText().toString();
        String price = edit_price.getText().toString();

        if(!code.isEmpty() && !description.isEmpty() && !price.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("codigo", code);
            register.put("description", description);
            register.put( "price", price);

            int quantity = BaseDeDatos.update("articulos", register, "codigo=" + code, null);
            BaseDeDatos.close();

            if (quantity == 1){
                Toast.makeText(this, R.string.update_exit,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, R.string.no_exist,Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this , R.string.llenar_campo,Toast.LENGTH_SHORT).show();
        }
    }
}
