package com.example.lamprini.ellinika;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lamprini.ellinika.db.DatabaseAccessor;
import com.example.lamprini.ellinika.db.User;

import static com.example.lamprini.ellinika.IntentConstants.USER_ID;
import static com.example.lamprini.ellinika.IntentConstants.USER_NAME;

public class LoginActivity extends AppCompatActivity {

    private DatabaseAccessor db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseAccessor(this);

    }

    @Override
    public void onBackPressed() { }

    public void login(View view) {

        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String pass = ((EditText) findViewById(R.id.pass)).getText().toString();
        TextView error = findViewById(R.id.error);

        User user = isUser(name);
        if (user == null) showError(error, R.string.error_no_such_user);
        else if (!pass.equals(user.getPass())) showError(error, R.string.error_wrong_password);
        else toGroupsRoles(user);

    }

    public void signup(View view) {

        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String pass = ((EditText) findViewById(R.id.pass)).getText().toString();
        TextView error = findViewById(R.id.error);

        User user = isUser(name);
        if (user != null) showError(error, R.string.error_user_already);
        else {

            try { user = db.createUser(name, pass); }
            catch (Exception e) {

                showError(error, R.string.error_creating);
                return;

            }

            if (user == null) return;
            toGroupsRoles(user);

        }

    }

    private void toGroupsRoles(User user) {
        Intent intent = new Intent(this, GroupsRolesActivity.class);
        intent.putExtra(USER_ID, user.getUid());
        intent.putExtra(USER_NAME, user.getName());
        startActivity(intent);
    }

    private User isUser(String name) {

        User user;
        try { user = db.getUser(name); }
        catch (Exception e) { return null; }

        return user;

    }

    private void showError(TextView view, int errorId) {

        view.setText(errorId);
        view.setVisibility(View.VISIBLE);

    }

}
