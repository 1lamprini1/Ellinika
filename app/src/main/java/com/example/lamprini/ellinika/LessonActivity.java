package com.example.lamprini.ellinika;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lamprini.ellinika.LessonExercisesFragment.OnLessonExercisesFragmentInteractionListener;
import com.example.lamprini.ellinika.LessonExtrasFragment.OnLessonExtrasFragmentInteractionListener;
import com.example.lamprini.ellinika.LessonTextFragment.OnLessonTextFragmentInteractionListener;
import com.example.lamprini.ellinika.db.DatabaseAccessor;
import com.example.lamprini.ellinika.db.Lesson;
import com.example.lamprini.ellinika.db.QuestionExercise;

import java.util.List;

import static com.example.lamprini.ellinika.IntentConstants.GROUP_NAME;
import static com.example.lamprini.ellinika.IntentConstants.IS_TEACHER;
import static com.example.lamprini.ellinika.IntentConstants.LESSON_TITLE;

public class LessonActivity extends AppCompatActivity implements
        OnLessonTextFragmentInteractionListener,
        OnLessonExercisesFragmentInteractionListener,
        OnLessonExtrasFragmentInteractionListener {

    private DatabaseAccessor db = new DatabaseAccessor(this);

    private Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // TODO: dictionary showing lesson words & w/ search over everything

        if (findViewById(R.id.lesson_fragment_container) == null) return;
        if (savedInstanceState != null) return;

        Intent intent = getIntent();
        String group = intent.getStringExtra(GROUP_NAME);
        String title = intent.getStringExtra(LESSON_TITLE);

        try { lesson = db.getLesson(group, title); }
        catch (Exception e) { return; }

        Fragment fragment = LessonTextFragment.newInstance(lesson.getText());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lesson_fragment_container, fragment, "text").commit();

        boolean isTeacher = intent.getBooleanExtra(IS_TEACHER, false);

        Button modifyButton = findViewById(R.id.modify_lesson_button);
        modifyButton.setVisibility(isTeacher? View.VISIBLE : View.INVISIBLE);

    }

    public void nextLessonFragment(View view) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("text");
        if (fragment != null && fragment.isVisible()) {

            List<QuestionExercise> exercises = lesson.getExercises();
            LessonExercisesFragment exercisesFragment = LessonExercisesFragment.newInstance(exercises);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lesson_fragment_container, exercisesFragment, "exercises")
                    .commit();

        } else {

            fragment = getSupportFragmentManager().findFragmentByTag("exercises");
            if (fragment != null && fragment.isVisible()) {

                LessonExtrasFragment extrasFragment = LessonExtrasFragment.newInstance(lesson.getExtras());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.lesson_fragment_container, extrasFragment, "extras")
                        .commit();
            }

            else {

                Intent intent = new Intent(this, GroupLessonsActivity.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {

                    extras.remove(LESSON_TITLE);
                    intent.putExtras(extras);

                }

                startActivity(intent);

            }

        }

    }

    @Override
    public void onLessonExercisesFragmentInteraction(Uri uri) { }

    @Override
    public void onLessonTextFragmentInteraction(Uri uri) { }

    @Override
    public void onLessonExtrasFragmentInteraction(Uri uri) { }

}
