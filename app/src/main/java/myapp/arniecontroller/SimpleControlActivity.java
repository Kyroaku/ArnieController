package myapp.arniecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static myapp.arniecontroller.R.id.seekBar;

/**
 * Created by Marcin Dziedzic on 2017-10-18.
 */

public class SimpleControlActivity extends Activity {


    SeekBar mBarAngle1;
    SeekBar mBarAngle2;
    SeekBar mBarAngle3;
    SeekBar mBarAngle4;
    TextView mTextAngle1;
    TextView mTextAngle2;
    TextView mTextAngle3;
    TextView mTextAngle4;

    Spinner mSpinnerMoveSequences;
    ListView mListMoves;
    Button mButtonStartSeq, mButtonAddSeq, mButtonDelSeq, mButtonAddMove, mButtonDelMove;

    List<MoveSequence> mMoveSequences;
    Thread mThreadMoveSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_control);

        /* Create callback for all seek bars. */
        SeekBar.OnSeekBarChangeListener seekBarCallback = new SeekBarCallback();
        /* Create callback for all buttons. */
        Button.OnClickListener buttonCallback = new ButtonCallback();
        /* Create callback for all lists. */
        AdapterView.OnItemSelectedListener itemSelectedCallback = new ItemSelectedCallback();
        /* Create callback for all lists. */
        AdapterView.OnItemClickListener itemClickCallback = new ItemClickCallback();

        /* Create first sequence with first move. */
        mMoveSequences = new ArrayList<>();
        mMoveSequences.add(new MoveSequence());
        mMoveSequences.get(0).Add();
        mMoveSequences.get(0).Select(0);

        /* Set move sequences spinner. */
        mSpinnerMoveSequences = (Spinner)findViewById(R.id.spinnerMoveSequences);
        ArrayAdapter<MoveSequence> sequencesAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, mMoveSequences
        );
        sequencesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMoveSequences.setAdapter(sequencesAdapter);
        mSpinnerMoveSequences.setOnItemSelectedListener(itemSelectedCallback);

        /* Set moves list. */
        mListMoves = (ListView)findViewById(R.id.listMoves);
        ArrayAdapter<MoveSequence.Element> movesAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, mMoveSequences.get(0).GetList()
        );
        mListMoves.setAdapter(movesAdapter);
        mListMoves.setOnItemClickListener(itemClickCallback);

        /* Set callback to each button. */
        mButtonStartSeq = (Button)findViewById(R.id.buttonStartSequence);
        mButtonStartSeq.setOnClickListener(buttonCallback);

        mButtonAddSeq = (Button)findViewById(R.id.buttonAddSequence);
        mButtonAddSeq.setOnClickListener(buttonCallback);

        mButtonDelSeq = (Button)findViewById(R.id.buttonDelSequence);
        mButtonDelSeq.setOnClickListener(buttonCallback);

        mButtonAddMove = (Button)findViewById(R.id.buttonAddMove);
        mButtonAddMove.setOnClickListener(buttonCallback);

        mButtonDelMove = (Button)findViewById(R.id.buttonDelMove);
        mButtonDelMove.setOnClickListener(buttonCallback);

        /* Set callback to each seek bar. */
        mBarAngle1 = (SeekBar) findViewById(seekBar);
        mBarAngle1.setMax(180);
        mBarAngle1.setOnSeekBarChangeListener(seekBarCallback);

        mBarAngle2 = (SeekBar) findViewById(R.id.seekBar2);
        mBarAngle2.setMax(180);
        mBarAngle2.setOnSeekBarChangeListener(seekBarCallback);

        mBarAngle3 = (SeekBar) findViewById(R.id.seekBar3);
        mBarAngle3.setMax(180);
        mBarAngle3.setOnSeekBarChangeListener(seekBarCallback);

        mBarAngle4 = (SeekBar) findViewById(R.id.seekBar4);
        mBarAngle4.setMax(180);
        mBarAngle4.setOnSeekBarChangeListener(seekBarCallback);

        /* Init text views. */
        mTextAngle1 = (TextView) findViewById(R.id.textView);
        mTextAngle1.setText("0");

        mTextAngle2 = (TextView) findViewById(R.id.textView2);
        mTextAngle2.setText("0");

        mTextAngle3 = (TextView) findViewById(R.id.textView3);
        mTextAngle3.setText("0");

        mTextAngle4 = (TextView) findViewById(R.id.textView4);
        mTextAngle4.setText("0");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Wifi.Connect(Settings.ServerIp, Settings.ServerPort);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Wifi.Close();
    }

    /**
     * Callback class for all seek bars in simple control.
     */
    private class SeekBarCallback implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            /* Send angles. */
            final int a1 = mBarAngle1.getProgress();
            final int a2 = mBarAngle2.getProgress();
            final int a3 = mBarAngle3.getProgress();
            final int a4 = mBarAngle4.getProgress();
            final FrameAngles frame = new FrameAngles(a1, a2, a3, a4);

            if(fromUser) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Wifi.Send(frame.ToByteArray());
                    }
                }).start();
            }

            /* Update UI. */
            MoveSequence seq = (MoveSequence) mSpinnerMoveSequences.getSelectedItem();

            switch (seekBar.getId()) {
                case R.id.seekBar:
                    mTextAngle1.setText(String.format(Locale.getDefault(), "%d", progress));
                    if(seq.GetSelectedItem() != null)
                        seq.GetSelectedItem().SetAngle(0, (short) progress);
                break;

                case R.id.seekBar2:
                    mTextAngle2.setText(String.format(Locale.getDefault(), "%d", progress));
                    if(seq.GetSelectedItem() != null)
                        seq.GetSelectedItem().SetAngle(1, (short) progress);
                break;

                case R.id.seekBar3:
                    mTextAngle3.setText(String.format(Locale.getDefault(), "%d", progress));
                    if(seq.GetSelectedItem() != null)
                        seq.GetSelectedItem().SetAngle(2, (short) progress);

                    break;

                case R.id.seekBar4:
                    mTextAngle4.setText(String.format(Locale.getDefault(), "%d", progress));
                    if(seq.GetSelectedItem() != null)
                        seq.GetSelectedItem().SetAngle(3, (short) progress);
                break;
            }

            mListMoves.setAdapter(mListMoves.getAdapter());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    /**
     * Callback class for all buttons in simple control.
     */
    private class ButtonCallback implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
             /* Get selected sequence. */
            MoveSequence seq = (MoveSequence)mSpinnerMoveSequences.getSelectedItem();
            switch(v.getId()) {
                case R.id.buttonStartSequence: {
                    /* Break, if move sequence thread is already working. */
                    if(mThreadMoveSequence != null && mThreadMoveSequence.isAlive())
                        break;

                    final MoveSequence sequence = (MoveSequence)mSpinnerMoveSequences.getSelectedItem();
                    mThreadMoveSequence = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for(MoveSequence.Element e : sequence.GetList()) {
                                FrameAngles frame = new FrameAngles(e.Angle(0), e.Angle(1), e.Angle(2), e.Angle(3));
                                Wifi.Send(frame.ToByteArray());
                                //e.SetAngle(e.Angle(2),(short) move2);Wifi.Send(tab);
                                //Wifi.Send(new FrameAngles(e.Angle(0), e.Angle(1), e.Angle(2)));
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mButtonStartSeq.setEnabled(true);
                                }
                            });
                        }
                    });
                    mThreadMoveSequence.start();
                    mButtonStartSeq.setEnabled(false);
                } break;

                case R.id.buttonAddSequence:
                    mMoveSequences.add(new MoveSequence());
                    break;

                case R.id.buttonDelSequence:
                    if(mSpinnerMoveSequences.getSelectedItemId() >= 0)
                        mMoveSequences.remove(mSpinnerMoveSequences.getSelectedItemId());
                    mSpinnerMoveSequences.setAdapter(mSpinnerMoveSequences.getAdapter());
                    //hjyhjhjh
                    break;

                case R.id.buttonAddMove:
                    /* Add move to selected sequence. */
                    seq.Add(
                            (short)mBarAngle1.getProgress(),
                            (short)mBarAngle2.getProgress(),
                            (short)mBarAngle3.getProgress(),
                            (short)mBarAngle4.getProgress()
                    );
                    seq.Select(seq.GetList().size()-1);
                    /* Tricky update of list view. */
                    mListMoves.setAdapter(mListMoves.getAdapter());
                    break;

                case R.id.buttonDelMove:
                    seq.Remove();
                    mListMoves.setAdapter(mListMoves.getAdapter());
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Callback class for all lists in simple control.
     */
    private class ItemSelectedCallback implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch(parent.getId()) {
                case R.id.spinnerMoveSequences: {
                    /* Get selected move sequence. */
                    MoveSequence e = (MoveSequence) mSpinnerMoveSequences.getAdapter().getItem(position);
                    /* Update list view with moves from selected sequence. */
                    mListMoves.setAdapter(new ArrayAdapter<MoveSequence.Element>(
                            getApplicationContext(), android.R.layout.simple_list_item_1, e.GetList()
                    ));
                } break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /**
     * Callback class for all lists in simple control.
     */
    private class ItemClickCallback implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(parent.getId()) {
                case R.id.listMoves: {
                    /* Get selected move sequence. */
                    MoveSequence seq = (MoveSequence) mSpinnerMoveSequences.getSelectedItem();
                    /* Select clicked item. */
                    seq.Select(position);
                    /* Send selected move. */
                    final FrameAngles frame = new FrameAngles(
                            seq.GetSelectedItem().Angle(0),
                            seq.GetSelectedItem().Angle(1),
                            seq.GetSelectedItem().Angle(2),
                            seq.GetSelectedItem().Angle(3)
                            
                    );
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Wifi.Send(frame.ToByteArray());
                        }
                    }).start();
                    /* Update values. */
                    mBarAngle1.setProgress(seq.GetSelectedItem().Angle(0));
                    mBarAngle2.setProgress(seq.GetSelectedItem().Angle(1));
                    mBarAngle3.setProgress(seq.GetSelectedItem().Angle(2));
                    mBarAngle4.setProgress(seq.GetSelectedItem().Angle(3));
                    /* Tricky update of list view. */
                    mListMoves.setAdapter(mListMoves.getAdapter());
                } break;
            }
        }
    }
}
