package com.sb.uzcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by SB on 2016-08-21.
 */
public class MoveControlFragment extends Fragment{

    LinearLayout mLinearLayout;

    Button mTestBtn;

    public MoveControlFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container == null) {
            return null;
        } else {
            mLinearLayout = (LinearLayout)inflater.inflate(R.layout.fragment_movecontrol, container, false);
            return mLinearLayout;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTestBtn = (Button)view.findViewById(R.id.test_button);

        mTestBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }
}
