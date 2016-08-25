package com.sb.uzcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by SB on 2016-08-21.
 */
public class LineTracerFragment extends Fragment implements View.OnClickListener, OnTouchListener{

    LinearLayout mLinearlayout;

    Button mLineShowButton = null;
    ImageView mLineUpImageView = null;
    ImageView mLineLeftImageView = null;
    ImageView mLineRightImageView = null;

    TextView mLineUpTextView = null;
    TextView mLineLeftTextView = null;
    TextView mLineRightTextView = null;

    public LineTracerFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLinearlayout = (LinearLayout)inflater.inflate(
                R.layout.fragment_linetracer, container, false);

        return mLinearlayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLineUpImageView = (ImageView) view.findViewById(R.id.line_status_up_iv);
        mLineLeftImageView = (ImageView) view.findViewById(R.id.line_status_left_iv);
        mLineRightImageView = (ImageView) view.findViewById(R.id.line_status_right_iv);

        mLineUpTextView = (TextView) view.findViewById(R.id.line_status_up_tv);
        mLineLeftTextView = (TextView) view.findViewById(R.id.line_status_left_tv);
        mLineRightTextView = (TextView) view.findViewById(R.id.line_status_right_tv);

        mLineShowButton = (Button) view.findViewById(R.id.line_show_bt);

        mLineUpImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
        mLineLeftImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
        mLineRightImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));

        mLineShowButton.setOnClickListener(this);
        mLineShowButton.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        if(mLineUpTextView.getVisibility() == View.INVISIBLE) {
            mLineUpTextView.setVisibility(View.VISIBLE);
            mLineLeftTextView.setVisibility(View.VISIBLE);
            mLineRightTextView.setVisibility(View.VISIBLE);
        } else{
            mLineUpTextView.setVisibility(View.INVISIBLE);
            mLineLeftTextView.setVisibility(View.INVISIBLE);
            mLineRightTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
