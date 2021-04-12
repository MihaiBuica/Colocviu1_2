package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    String displayText = "";
    int totalSum = 0;
    int []sumArray;
    int idx = 0;

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
                    totalSum += Integer.valueOf(nextTermEditText.getText().toString());

                    sumArray[idx++] = Integer.valueOf(nextTermEditText.getText().toString());
                    allTermsTextViw.setText(displayText);

                    nextTermEditText.setText("");
                }
            }
            if (v.getId() == R.id.compute_button)
            {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_2SecondaryActivity.class);
                intent.putExtra(Constants.VALUES_OF_SUM, sumArray);
                intent.putExtra(Constants.NUM_VALUES, idx);
                startActivityForResult(intent, Constants.REQUEST_CODE);
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
                int sumResult = intent.getIntExtra(Constants.SECOND_RETURN_KEY, -1);
                Toast.makeText(this, "The sum is: " + sumResult, Toast.LENGTH_LONG).show();
            }
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);
        init();
    }
}
