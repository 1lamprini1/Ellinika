package com.example.lamprini.ellinika;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.lamprini.ellinika.db.DatabaseAccessor;

import static com.example.lamprini.ellinika.IntentConstants.DO_CREATE_GROUP;
import static com.example.lamprini.ellinika.IntentConstants.IS_TEACHER;
import static com.example.lamprini.ellinika.IntentConstants.USER_ID;
import static com.example.lamprini.ellinika.R.id.create_group_error;
import static com.example.lamprini.ellinika.R.id.set_name_group;

public class NewGroupActivity extends AppCompatActivity {

    private DatabaseAccessor db = new DatabaseAccessor(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

    }

    public void createOrJoinGroup(View view) {

        boolean create = getIntent().getBooleanExtra(DO_CREATE_GROUP, false);

        Integer id = getIntent().getIntExtra(USER_ID, -1);

        String name = ((EditText) findViewById(set_name_group)).getText().toString();

        EditText error = findViewById(create_group_error);

        if (id < 0 || "".equals(name)) {

            error.setText(R.string.set_name_group_error);
            return;

        }

        if (create) {

            if (isGroup(name)) {

                error.setText(R.string.create_group_exists_error);
                return;

            }

            try {

                db.createGroup(id, name);
                Intent intent = new Intent(this, GroupsRolesActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);

            } catch (Exception e) { error.setText(R.string.create_group_error); }

        } else {

            if (!isGroup(name)) {

                error.setText(R.string.join_group_not_exist_error);
                return;

            }

            boolean teacher = this.getIntent().getBooleanExtra(IS_TEACHER, false);

            if (teacher) {

                try {

                    db.joinGroupAsTeacher(id, name);
                    toGroupsRoles();

                } catch (Exception e) { error.setText(R.string.join_group_error); }

            } else {

                try {

                    db.joinGroupAsStudent(id, name);
                    toGroupsRoles();

                } catch (Exception e) { error.setText(R.string.join_group_error); }

            }

        }

    }

    private void toGroupsRoles() {

        Intent intent = new Intent(this, GroupsRolesActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);

    }

    public void cancel(View view) { toGroupsRoles(); }

    private boolean isGroup(String name) {

        try { return db.getGroup(name); }
        catch (Exception e) { return false; }

    }

}
