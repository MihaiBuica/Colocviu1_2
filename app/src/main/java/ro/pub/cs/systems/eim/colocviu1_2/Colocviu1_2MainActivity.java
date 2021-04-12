package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Colocviu1_2MainActivity extends AppCompatActivity {
    Button addButton;
    Button computeButton;
    EditText nextTermEditText;
    TextView allTermsTextViw;

    String displayText = "";
    int totalSum = 0;

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
                    allTermsTextViw.setText(displayText);
                    nextTermEditText.setText("");
                }
            }
        }
    }
    void init() {
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(buttonListener);
        computeButton = findViewById(R.id.compute_button);
        nextTermEditText = findViewById(R.id.next_term_edit_text);
        allTermsTextViw = findViewById(R.id.all_terms_text_view);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);
        init();
    }
}
