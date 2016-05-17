package com.arbalestx.barcodescanner;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScanningActivity extends AppCompatActivity {

    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listBarcode;
    private EditText editBarcode;
    private TextView textUndo;
    private View viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanning);

        editBarcode = (EditText) findViewById(R.id.editBarcode);
        listBarcode = (ListView) findViewById(android.R.id.list);
        viewContainer = findViewById(R.id.undobar);
        textUndo = (TextView) findViewById(R.id.textUndo);

        editBarcode.setOnKeyListener(new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                addItems();
                return true;
            }
                return false;
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listBarcode.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_pref) {
            Intent i = new Intent(this, PrefActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItems() {
        listItems.add(editBarcode.getText().toString());
        showUndoBar(viewContainer);
        adapter.notifyDataSetChanged();
        editBarcode.setText("");
    }

    public void showUndoBar(final View viewContainer) {
        textUndo.setText(editBarcode.getText().toString());
        viewContainer.setVisibility(View.VISIBLE);
        viewContainer.setAlpha(1);
        viewContainer.animate().alpha(0.4f).setDuration(3000).withEndAction(new Runnable() {
            @Override
            public void run() {
                viewContainer.setVisibility(View.GONE);
            }
        });
    }

    public void clickUndo(View view) {
        listItems.remove(listItems.size()-1);
        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        viewContainer.setVisibility(View.GONE);
    }
}
