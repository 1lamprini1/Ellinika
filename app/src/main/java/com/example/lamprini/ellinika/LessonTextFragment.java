package com.example.lamprini.ellinika;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LessonTextFragment.OnLessonTextFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonTextFragment extends Fragment {

    private static final String TEXT_PARAM = "text";

    private String text;

    // This gives the parent activity access to the fragment.
    private OnLessonTextFragmentInteractionListener mListener;

    public LessonTextFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param text The text.
     * @return A new instance of fragment LessonTextFragment.
     */
    public static LessonTextFragment newInstance(String text) {

        LessonTextFragment fragment = new LessonTextFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_PARAM, text);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString(TEXT_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_text, container, false);
        TextView textView = view.findViewById(R.id.lesson_text);
        if (textView != null) textView.setText(text);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLessonTextFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof OnLessonTextFragmentInteractionListener) {
            mListener = (OnLessonTextFragmentInteractionListener) context;
        } else {

            throw new RuntimeException(context.toString()
                    + " must implement OnLessonTextFragmentInteractionListener");

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
    public interface OnLessonTextFragmentInteractionListener {
        void onLessonTextFragmentInteraction(Uri uri);
    }
}
