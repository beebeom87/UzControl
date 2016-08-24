package com.sb.uzcontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by SB on 2016-08-21.
 */
public class MoveControlFragment extends Fragment{

    private Context mContext;

    OnMoveControlListener mCallback;

    LinearLayout mLinearLayout;

    Button mUpBtn;
    Button mLeftBtn;
    Button mDownBtn;
    Button mRightBtn;

    SeekBar mPowerSeekBar;

    TextView mDistanceTv;
    TextView mPowerTv;

    private boolean mReverse;
    private boolean mReverseLR;
    private boolean mSynchronizeMotors;
    private boolean mRegulateSpeed;

    private int mPower = 80;

    // Container Activity must implement this interface
    public interface OnMoveControlListener {
        public void onMoveSendMsg(byte[] msg);
    }

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
        mUpBtn = (Button)view.findViewById(R.id.move_up_bt);
        mLeftBtn = (Button)view.findViewById(R.id.move_left_bt);
        mDownBtn = (Button)view.findViewById(R.id.move_down_bt);
        mRightBtn = (Button)view.findViewById(R.id.move_right_bt);
        mPowerSeekBar = (SeekBar)view.findViewById(R.id.power_seekbar);
        mPowerTv = (TextView)view.findViewById(R.id.power_tv);
        mDistanceTv = (TextView)view.findViewById(R.id.distance_tv);

        mUpBtn.setOnTouchListener(new DirectionButtonOnTouchListener(1, 1));
        mLeftBtn.setOnTouchListener(new DirectionButtonOnTouchListener(-0.6, 0.6));
        mDownBtn.setOnTouchListener(new DirectionButtonOnTouchListener(-1, -1));
        mRightBtn.setOnTouchListener(new DirectionButtonOnTouchListener(0.6, -0.6));

        mPowerTv.setText(mPower + "");

        mPowerSeekBar.setProgress(mPower);
        mPowerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPower = progress;
                mPowerTv.setText(mPower + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
        readPreferences();

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMoveControlListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private class DirectionButtonOnTouchListener implements View.OnTouchListener {

        private double lmod;
        private double rmod;

        public DirectionButtonOnTouchListener(double l, double r) {
            lmod = l;
            rmod = r;
            //ImageButton buttonUp = (ImageButton) findViewById(R.id.button_up);
        }
        //
        // 버튼을 누르면 구분없이 l,r 지정된 값으로 모터 값 지정.
        //
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Log.i("GPC", "onTouch event: " + Integer.toString(event.getAction()));
            int action = event.getAction();
            //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
            if (action == MotionEvent.ACTION_DOWN) {
                byte power = (byte) mPower;
                if (mReverse) {
                    power *= -1;
                }
                byte l = (byte) (power*lmod);
                byte r = (byte) (power*rmod);
                if (!mReverseLR) {
                    motors(l, r, mRegulateSpeed, mSynchronizeMotors);
                } else {
                    motors(r, l, mRegulateSpeed, mSynchronizeMotors);
                }
            } else if ((action == MotionEvent.ACTION_UP) || (action == MotionEvent.ACTION_CANCEL)) {
                motors((byte) 0, (byte) 0, mRegulateSpeed, mSynchronizeMotors);
            }
            return true;
        }
    }

    private void motors(byte l, byte r, boolean speedReg, boolean motorSync) {
        speedReg = false; motorSync = false;

        byte[] data = { 0x0c, 0x00, (byte) 0x80, 0x04, 0x02, 0x32, 0x07, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00,
                0x0c, 0x00, (byte) 0x80, 0x04, 0x01, 0x32, 0x07, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00 };

        //Log.i("GPC", "motors: " + Byte.toString(l) + ", " + Byte.toString(r));
        //
        // 왼쪽 오른쪽에 대한 모터 설정값.
        //
        data[5] = l;
        data[19] = r;
        if (speedReg) {
            data[7] |= 0x01;
            data[21] |= 0x01;
        }
        if (motorSync) {
            data[7] |= 0x02;
            data[21] |= 0x02;
        }
        mCallback.onMoveSendMsg(data);
    }

    private void readPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

            mReverse = prefs.getBoolean("PREF_SWAP_FWDREV", false);
            mRegulateSpeed = prefs.getBoolean("PREF_REG_SPEED", false);
            mReverseLR = prefs.getBoolean("PREF_SWAP_LEFTRIGHT", false);
            mSynchronizeMotors = prefs.getBoolean("PREF_REG_SYNC", false);
            if (!mRegulateSpeed) {
                mSynchronizeMotors = false;
            }
    }

    public void updateDistance(String msg) {
        mDistanceTv.setText("Distance :" + msg);
    }
}
