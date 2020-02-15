package com.example.userregistrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.userregistrationapp.Adapter.PersonAdapter;
import com.example.userregistrationapp.model.About;
import com.example.userregistrationapp.model.Person;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;

public class Firstpage extends AppCompatActivity {

    ListView listView;
    List<Person> lstPerson;
    PersonAdapter adapter;
    TextView txtCount;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        firebaseAuth = FirebaseAuth.getInstance();


            //get all data
            lstPerson = Person.listAll(Person.class);
            About about = new About();

        txtCount = (TextView) findViewById(R.id.txtCount);
        //Count data in database
        long count = Person.count(Person.class);
        txtCount.setText(count + " items");

        listView = (ListView) findViewById(R.id.listView);
        adapter = new PersonAdapter(this, lstPerson);
        listView.setAdapter(adapter);
        //Edit data
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Share person id to EditActivity.
                Person person = adapter.getItem(position);
                Intent intent = new Intent(Firstpage.this, EditActivity.class);
                intent.putExtra("action", "editPerson");
                intent.putExtra("id", person.getId());
                startActivity(intent);
                finish();
            }
        });
        //Show label when list is empty
        listView.setEmptyView(findViewById(R.id.empty_element));
        //Set context menu for listview
        registerForContextMenu(listView);
    }

    //Show Option menu: Add new, Delete all, About
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //add new data
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_new:
                Intent intent = new Intent(Firstpage.this, EditActivity.class);
                intent.putExtra("action", "addNewPerson");
                startActivity(intent);
               // finish();
                break;
            case R.id.action_delete_all:
                Person.deleteAll(Person.class);
                UpdateData();
                break;

            case R.id.action_About:
                Intent intent1= new Intent(Firstpage.this,About.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, Menu.FIRST, 0, "Delete");
    }
    //delete data
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case Menu.FIRST:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Person person = adapter.getItem(info.position);
                person.delete();
                UpdateData();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    //Update list
    public void UpdateData() {
        lstPerson = Person.listAll(Person.class);
        adapter = new PersonAdapter(this, lstPerson);
        listView.setAdapter(adapter);
        long count = Person.count(Person.class);
        txtCount.setText(count + " items");
    }
}

