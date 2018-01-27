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
import static com.example.lamprini.ellinika.IntentConstants.LESSON_TITLE;
import static com.example.lamprini.ellinika.IntentConstants.USER_ID;
import static com.example.lamprini.ellinika.R.layout.list_lessons_item;

public class GroupLessonsActivity extends AppCompatActivity {

    // TODO: add dictionary fragment

    private DatabaseAccessor db = new DatabaseAccessor(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_lessons);

        String groupName = this.getIntent().getStringExtra(GROUP_NAME);

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

                final String item = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(GroupLessonsActivity.this, LessonActivity.class);
                intent.putExtra(LESSON_TITLE, item);
                startActivity(intent);

            }

        });

    }

    public void createLesson(View view) {

        Integer userId = this.getIntent().getIntExtra(USER_ID, -1);
        boolean teacher;
        try {

            List<String> groups = db.teacherInGroups(userId);
            teacher = groups.contains(String.valueOf(this.getIntent().getStringExtra(GROUP_NAME)));

        } catch (Exception e) { teacher = false; }

        if (userId < 0 || !teacher) {

            TextView errorText = findViewById(R.id.create_lesson_error);
            errorText.setText(R.string.create_lesson_error);
            return;

        }

        Intent intent = new Intent(this, CreateLessonActivity.class);
        intent.putExtras(this.getIntent());
        startActivity(intent);

    }

}
