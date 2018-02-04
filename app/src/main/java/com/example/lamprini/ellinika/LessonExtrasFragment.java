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
 * {@link OnLessonExtrasFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonExtrasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonExtrasFragment extends Fragment {

    private static final String EXTRAS_TEXT = "extras";

    private String mExtras;

    private OnLessonExtrasFragmentInteractionListener mListener;

    public LessonExtrasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param extras Extras text.
     * @return A new instance of fragment LessonExtrasFragment.
     */
    public static LessonExtrasFragment newInstance(String extras) {

        LessonExtrasFragment fragment = new LessonExtrasFragment();
        Bundle args = new Bundle();
        args.putString(EXTRAS_TEXT, extras);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExtras = getArguments().getString(EXTRAS_TEXT);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_extras, container, false);
        TextView text = view.findViewById(R.id.lesson_extras_text);
        text.setText(mExtras);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLessonExtrasFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnLessonExtrasFragmentInteractionListener) {
            mListener = (OnLessonExtrasFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLessonExtrasFragmentInteractionListener");
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
    public interface OnLessonExtrasFragmentInteractionListener {
        void onLessonExtrasFragmentInteraction(Uri uri);
    }
}
