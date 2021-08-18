package com.blucor.tcthecontractor.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.blucor.tcthecontractor.R;

public class FeedbackActivity extends AppCompatActivity {

    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final EditText nameField = (EditText) findViewById(R.id.EditText_Enter_your_name);
        String name = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.EditText_Enter_your_Email);
        String email = emailField.getText().toString();

        final EditText feedbackField = (EditText) findViewById(R.id.urexp);
        String feedback = feedbackField.getText().toString();

        final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
        boolean bRequiresResponse = responseCheckbox.isChecked();

        Submit=(Button)findViewById(R.id.ButtonSendFeedback);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedbackActivity.this,"Thanks! Your Feedback is submitted.", Toast.LENGTH_LONG).show();
            }
        });

    }
}