package com.example.lamprini.ellinika;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lamprini.ellinika.db.DatabaseAccessor;

import java.util.List;

import static com.example.lamprini.ellinika.IntentConstants.DO_CREATE_GROUP;
import static com.example.lamprini.ellinika.IntentConstants.GROUP_NAME;
import static com.example.lamprini.ellinika.IntentConstants.IS_TEACHER;
import static com.example.lamprini.ellinika.IntentConstants.USER_ID;
import static com.example.lamprini.ellinika.R.layout.list_groups_item;

public class GroupsRolesActivity extends Activity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_roles);

        Integer id = this.getIntent().getIntExtra(USER_ID, -1);

        TextView view = findViewById(R.id.uid);
        view.setText(String.format("%d", id));

        DatabaseAccessor db = new DatabaseAccessor(this);
        List<String> groups;
        try { groups = db.getGroups(id); }
        catch (Exception e) { return; }

        final ListView listview = findViewById(R.id.groups_list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, list_groups_item, R.id.textView, groups);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                final String item = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(GroupsRolesActivity.this, GroupLessonsActivity.class);
                intent.putExtras(GroupsRolesActivity.this.getIntent());
                intent.putExtra(GROUP_NAME, item);
                startActivity(intent);

            }

        });

    }

    public void createGroup(View view) {

        Intent intent = new Intent(this, NewGroupActivity.class);
        intent.putExtra(DO_CREATE_GROUP, true);
        intent.putExtras(this.getIntent());
        startActivity(intent);

    }

    public void joinGroupAsStudent(View view) {

        Intent intent = new Intent(this, NewGroupActivity.class);
        intent.putExtra(DO_CREATE_GROUP, false);
        intent.putExtra(IS_TEACHER, false);
        intent.putExtras(this.getIntent());
        startActivity(intent);

    }

    public void joinGroupAsTeacher(View view) {

        Intent intent = new Intent(this, NewGroupActivity.class);
        intent.putExtra(DO_CREATE_GROUP, false);
        intent.putExtra(IS_TEACHER, true);
        intent.putExtras(this.getIntent());
        startActivity(intent);

    }

}
