package com.example.atvavl2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_contatos;
    private Spinner spinner;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            btn_contatos = findViewById(R.id.btn_contatos);
            spinner = (Spinner) findViewById(R.id.spinner);

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            btn_contatos.setOnClickListener(this);
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btn_contatos){
            try {
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                ContentResolver contentResolver =MainActivity.this.getContentResolver();
                cursor = contentResolver.query(uri, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
                if(cursor.getCount() > 0) {
                    List<String> contacts = new ArrayList<String>();
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        contacts.add(nome);
                        String contatoId = cursor.getString((cursor.getColumnIndex(ContactsContract.Contacts._ID)));
                        Cursor numero = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contatoId, null, null);
                        numero.moveToNext();
                    }
                    ArrayAdapter<String> dataAdapterNomes = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, contacts);
                    dataAdapterNomes.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapterNomes);
                    cursor.close();
                }
            }catch (Exception e){
                e.getMessage();
                e.printStackTrace();
            }
        }
    }



}


