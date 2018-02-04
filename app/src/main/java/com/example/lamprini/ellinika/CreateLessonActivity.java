package com.example.lamprini.ellinika;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lamprini.ellinika.CreateLessonExercisesFragment.OnCreateExercisesFragmentInteractionListener;
import com.example.lamprini.ellinika.CreateLessonExtrasFragment.OnCreateLessonExtrasFragmentInteractionListener;
import com.example.lamprini.ellinika.CreateLessonTextFragment.OnCreateTextFragmentInteractionListener;
import com.example.lamprini.ellinika.db.DatabaseAccessor;
import com.example.lamprini.ellinika.db.Lesson;
import com.example.lamprini.ellinika.db.QuestionExercise;

import java.util.Collections;

import static com.example.lamprini.ellinika.IntentConstants.GROUP_NAME;

public class CreateLessonActivity extends AppCompatActivity implements
        OnCreateTextFragmentInteractionListener,
        OnCreateExercisesFragmentInteractionListener,
        OnCreateLessonExtrasFragmentInteractionListener {

    private DatabaseAccessor db = new DatabaseAccessor(this);

    private Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);

    }

    public void nextCreateLessonFragment(View view) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("text");
        if (fragment == null) {

            String group = this.getIntent().getStringExtra(GROUP_NAME);

            EditText editTitle = findViewById(R.id.create_lesson_title_in);
            if (editTitle == null) return;
            String title = editTitle.getText().toString();
            if ("".equals(title)) {

                showError(R.string.error_create_lesson_empty_title);
                return;

            }

            hideError();

            lesson = new Lesson(group, title);

            // Title is forever.
            editTitle.setVisibility(View.GONE);
            TextView viewTitle = findViewById(R.id.create_lesson_title_out);
            viewTitle.setText(title);
            viewTitle.setVisibility(View.VISIBLE);

            Fragment textFragment = new CreateLessonTextFragment();
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
//            textFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.create_lesson_fragment_container, textFragment, "text")
                    .addToBackStack(null).commit();

        } else if (fragment.isVisible()) {

            EditText content = findViewById(R.id.create_lesson_text);
            if (content == null) return;

            lesson.setText(content.getText().toString());

            CreateLessonExercisesFragment exercisesFragment = new CreateLessonExercisesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.create_lesson_fragment_container, exercisesFragment, "exercises")
                    .addToBackStack(null).commit();

        } else {

            fragment = getSupportFragmentManager().findFragmentByTag("exercises");
            if (fragment != null && fragment.isVisible()) {

                EditText qText = findViewById(R.id.create_lesson_exercises_q);
                EditText aText = findViewById(R.id.create_lesson_exercises_a);
                if (qText == null || aText == null) return;

                String question = qText.getText().toString();
                String answer = aText.getText().toString();

                QuestionExercise exercise = new QuestionExercise(question);
                exercise.addSolution(answer);
                lesson.setExercises(Collections.singletonList(exercise));
                // TODO: also set > 1 solutions and have more than one exercise.

                CreateLessonExtrasFragment extrasFragment = new CreateLessonExtrasFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.create_lesson_fragment_container, extrasFragment, "extras")
                        .addToBackStack(null).commit();

            } else {

                EditText extrasText = findViewById(R.id.create_lesson_extras);
                if (extrasText == null) return;
                String extras = extrasText.getText().toString();
                lesson.setExtras(extras);

                try { db.createLesson(lesson); }
                catch (Exception e) { showError(R.string.create_lesson_error_text); }

                Intent intent = new Intent(this, GroupLessonsActivity.class);
                intent.putExtras(this.getIntent());
                startActivity(intent);

            }

        }

    }

    private void showError(int errorId) {

        TextView view = findViewById(R.id.create_lesson_error);
        view.setText(errorId);
        view.setVisibility(View.VISIBLE);

    }

    private void hideError() {
        TextView view = findViewById(R.id.create_lesson_error);
        view.setVisibility(View.GONE);
    }

    @Override
    public void onCreateTextFragmentInteraction(Uri uri) { }

    @Override
    public void onCreateExercisesFragmentInteraction(Uri uri) { }

    @Override
    public void onCreateExtrasFragmentInteraction(Uri uri) { }

}
