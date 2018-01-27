package com.example.lamprini.ellinika;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.lamprini.ellinika.db.DatabaseAccessor;
import com.example.lamprini.ellinika.db.User;

import static com.example.lamprini.ellinika.IntentConstants.USER_ID;

public class LoginActivity extends AppCompatActivity {

    private DatabaseAccessor db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseAccessor(this);

    }

    public void login(View view) {

        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String pass = ((EditText) findViewById(R.id.pass)).getText().toString();
        EditText error = findViewById(R.id.error);

        User user = isUser(name);
        if (user == null) {

            error.setText(R.string.error_no_such_user);

        } else if (!pass.equals(user.getPass())) {

            error.setText(R.string.error_wrong_password);

        } else {

            Intent intent = new Intent(this, GroupsRolesActivity.class);
            intent.putExtra(USER_ID, user.getUid());
            startActivity(intent);

        }

    }

    public void signup(View view) {

        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String pass = ((EditText) findViewById(R.id.pass)).getText().toString();
        EditText error = findViewById(R.id.error);

        User user = isUser(name);
        if (user != null) {

            error.setText(R.string.error_user_already);

        } else {

            try { user = db.createUser(name, pass); }
            catch (Exception e) {

                error.setText(R.string.error_creating);
                return;

            }

            if (user == null) return;

            Intent intent = new Intent(this, GroupsRolesActivity.class);
            intent.putExtra(USER_ID, user.getUid());
            startActivity(intent);

        }

    }

    // TODO: get user id from database, "" if wrong password, and null if not there.
    private User isUser(String name) {

        User user;
        try { user = db.getUser(name); }
        catch (Exception e) { return null; }

        return user;

    }

}
