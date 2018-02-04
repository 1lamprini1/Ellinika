package com.example.lamprini.ellinika;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lamprini.ellinika.db.DatabaseAccessor;

import java.util.List;

import static com.example.lamprini.ellinika.IntentConstants.GROUP_NAME;
import static com.example.lamprini.ellinika.IntentConstants.IS_TEACHER;
import static com.example.lamprini.ellinika.IntentConstants.LESSON_TITLE;
import static com.example.lamprini.ellinika.IntentConstants.USER_ID;
import static com.example.lamprini.ellinika.R.layout.list_lessons_item;

public class GroupLessonsActivity extends AppCompatActivity {

    // TODO : delete lesson (students: only locally; teachers: also from the server)

    // TODO : add dictionary fragment showing everything & w/ search.

    private DatabaseAccessor db = new DatabaseAccessor(this);

    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_lessons);

        groupName = this.getIntent().getStringExtra(GROUP_NAME);

        TextView groupView = findViewById(R.id.group_name);
        groupView.setText(groupName);

        List<String> lessons;
        try { lessons = db.getLessons(groupName); }
        catch (Exception e) { return; }

        final ListView listView = findViewById(R.id.lessons_list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, list_lessons_item, R.id.textView, lessons);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(GroupLessonsActivity.this, LessonActivity.class);

            final String item = (String) parent.getItemAtPosition(position);
            intent.putExtra(LESSON_TITLE, item);

            Bundle extras = getIntent().getExtras();
            if (extras != null) intent.putExtras(extras);

            boolean isTeacher;
            Integer userId = GroupLessonsActivity.this.getIntent().getIntExtra(USER_ID, -1);
            try {

                List<String> groups = db.teacherInGroups(userId);
                isTeacher = groups.contains(groupName);

            } catch (Exception e) { isTeacher = false; }
            intent.putExtra(IS_TEACHER, isTeacher);

            startActivity(intent);

            }

        });

    }

    public void createLesson(View view) {

        Integer userId = this.getIntent().getIntExtra(USER_ID, -1);
        boolean isTeacher;
        try {

            List<String> groups = db.teacherInGroups(userId);
            isTeacher = groups.contains(groupName);

        } catch (Exception e) { isTeacher = false; }

        if (userId < 0 || !isTeacher) {

            showError(R.id.create_lesson_error, R.string.create_lesson_error);
            return;

        }

        Intent intent = new Intent(this, CreateLessonActivity.class);
        intent.putExtras(this.getIntent());
        startActivity(intent);

    }

    private void showError(int textId, int error) {

        TextView view = findViewById(textId);
        if (view != null) {

            view.setText(error);
            view.setVisibility(View.VISIBLE);

        }

    }

    public void logout(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

}
