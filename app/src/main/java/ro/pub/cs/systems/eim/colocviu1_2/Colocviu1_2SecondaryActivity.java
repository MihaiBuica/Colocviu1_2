package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_2_secondary);
        Intent intent = getIntent();
        Log.d(Constants.MAIN, "SecondActivity() method was invoked");

        if (intent != null && intent.getExtras().containsKey(Constants.VALUES_OF_SUM)) {
            int []array = intent.getIntArrayExtra(Constants.VALUES_OF_SUM);
            int n = intent.getIntExtra(Constants.NUM_VALUES, 0);
            int sum = 0;
            for (int i = 0; i < n; i++)
            {
                sum += array[i];
            }

            Intent intentRes = new Intent();
            intentRes.putExtra(Constants.SECOND_RETURN_KEY, sum);
            setResult(RESULT_OK, intentRes);
            finish();
        }

    }
}
