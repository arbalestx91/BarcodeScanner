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
import java.util.List;

public class ScanningActivity extends AppCompatActivity {

    private ArrayList<String> listInventory = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listViewBarcode;
    private EditText editTextBarcode;
    private TextView textViewUndo;
    private View viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanning);

        editTextBarcode = (EditText) findViewById(R.id.editBarcode);
        listViewBarcode = (ListView) findViewById(android.R.id.list);
        viewContainer = findViewById(R.id.undobar);
        textViewUndo = (TextView) findViewById(R.id.textUndo);

        editTextBarcode.setOnKeyListener(new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                addItems();
                return true;
            }
            return false;
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listInventory);
        listViewBarcode.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exportCSV) {
            ArrayList<String> listItem = new ArrayList<>();
            ArrayList<Integer> listQty = new ArrayList<>();
            int index = 0;

            for(String invItem:listInventory) {
                if(listItem.contains(invItem)) {
                    index = listItem.indexOf(invItem);
                    listQty.set(index, listQty.get(index) + 1);
                }
                else {
                    listItem.add(invItem);
                    listQty.add(1);
                }
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public void addItems() {
        listInventory.add(editTextBarcode.getText().toString());
        showUndoBar(viewContainer);
        adapter.notifyDataSetChanged();
        editTextBarcode.setText("");
    }

    public void showUndoBar(final View viewContainer) {
        textViewUndo.setText(editTextBarcode.getText().toString());
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
        listInventory.remove(listInventory.size()-1);
        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        viewContainer.setVisibility(View.GONE);
    }
}
