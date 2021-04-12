package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Colocviu1_2MainActivity extends AppCompatActivity {
    Button addButton;
    Button computeButton;
    EditText nextTermEditText;
    TextView allTermsTextViw;

    private IntentFilter intentFilter = new IntentFilter();

    String displayText = "";
    String oldDisplayText = "";
    int totalSum = 0;
    int []sumArray;
    int idx = 0;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.MAIN, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
            String str = intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA);
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

        }
    }

    GenericButtonListener buttonListener = new GenericButtonListener();
    private class GenericButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.add_button)
            {
                if (!nextTermEditText.getText().toString().equals(""))
                {
                    if (displayText.equals(""))
                    {
                        displayText = nextTermEditText.getText().toString();
                    }
                    else
                    {
                        displayText = displayText + " + " + nextTermEditText.getText().toString();
                    }
//                    totalSum += Integer.valueOf(nextTermEditText.getText().toString());

                    sumArray[idx++] = Integer.valueOf(nextTermEditText.getText().toString());
                    allTermsTextViw.setText(displayText);

                    nextTermEditText.setText("");
                }
            }
            if (v.getId() == R.id.compute_button)
            {
                if (oldDisplayText.equals(displayText))
                {
                    Toast.makeText(getApplicationContext(), "Sum is already calculated: " + totalSum, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_2SecondaryActivity.class);
                    intent.putExtra(Constants.VALUES_OF_SUM, sumArray);
                    intent.putExtra(Constants.NUM_VALUES, idx);
                    startActivityForResult(intent, Constants.REQUEST_CODE);
                }
                oldDisplayText = displayText;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(Constants.MAIN, "onActivityResult() method was invoked");
        if (requestCode == Constants.REQUEST_CODE)
        {
            Log.d(Constants.MAIN, "Request code found");
            if (intent != null)
            {
                Log.d(Constants.MAIN, "Message found");
                totalSum = intent.getIntExtra(Constants.SECOND_RETURN_KEY, -1);
                Toast.makeText(this, "The sum is: " + totalSum, Toast.LENGTH_LONG).show();
            }
        }
        if (totalSum >= 10)
        {
            Toast.makeText(getApplicationContext(), "Service started", Toast.LENGTH_LONG).show();
            Intent intentService = new Intent(getApplicationContext(), Colocviu1_2Service.class);
            intentService.putExtra(Constants.CALCULATED_SUM, totalSum);
            getApplicationContext().startService(intentService);
        }

    }
    void init() {
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(buttonListener);
        computeButton = findViewById(R.id.compute_button);
        computeButton.setOnClickListener(buttonListener);
        nextTermEditText = findViewById(R.id.next_term_edit_text);
        allTermsTextViw = findViewById(R.id.all_terms_text_view);

        sumArray = new int[50];
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.MAIN, "onCreate() method was invoked");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);
        init();
        intentFilter.addAction(Constants.ACTIONTYPE);
    }

    @Override
    protected void onResume() {
        Log.d(Constants.MAIN, "onResume() method was invoked");
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        Log.d(Constants.MAIN, "onPause() method was invoked");
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(Constants.MAIN, "onSAveInstanceState() method was invoked");
        savedInstanceState.putInt(Constants.CALCULATED_SUM, totalSum);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(Constants.MAIN, "onRestoreInstanceState() method was invoked");
        if (savedInstanceState.containsKey(Constants.CALCULATED_SUM)){
            totalSum = savedInstanceState.getInt(Constants.CALCULATED_SUM, -1);
            Log.d(Constants.MAIN, "Current sum: " + totalSum);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(Constants.MAIN, "onDestroy() method was invoked");
        Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
        stopService(intent);
        super.onDestroy();
    }

    }
