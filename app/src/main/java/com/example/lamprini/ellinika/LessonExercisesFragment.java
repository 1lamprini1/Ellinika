package com.example.lamprini.ellinika;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lamprini.ellinika.db.QuestionExercise;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLessonExercisesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonExercisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonExercisesFragment extends Fragment {

    private static final String EXERCISES = "exercise";

    private List<QuestionExercise> mExercises;

    private OnLessonExercisesFragmentInteractionListener mListener;

    public LessonExercisesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param exercises A list of exercises.
     * @return A new instance of fragment LessonExercisesFragment.
     */
    public static LessonExercisesFragment newInstance(List<QuestionExercise> exercises) {

        LessonExercisesFragment fragment = new LessonExercisesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXERCISES, (ArrayList<? extends Parcelable>) exercises);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mExercises = getArguments().getParcelableArrayList(EXERCISES);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_exercises, container, false);
        TextView question = view.findViewById(R.id.lesson_exercises_text);
        question.setText(mExercises.get(0).getText());
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

        if (mListener != null) {
            mListener.onLessonExercisesFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnLessonExercisesFragmentInteractionListener) {
            mListener = (OnLessonExercisesFragmentInteractionListener) context;
        } else {

            throw new RuntimeException(context.toString()
                    + " must implement OnLessonExercisesFragmentInteractionListener");

        }

    }

    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLessonExercisesFragmentInteractionListener {
        void onLessonExercisesFragmentInteraction(Uri uri);
    }
}
