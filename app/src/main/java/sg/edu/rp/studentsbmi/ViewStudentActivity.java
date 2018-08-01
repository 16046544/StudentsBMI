package sg.edu.rp.studentsbmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class ViewStudentActivity extends AppCompatActivity {

    private EditText etClass,etFirstName, etLastName, etHeight, etWeight, etBMI;
    private Button btnUpdate, btnDelete;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etBMI = findViewById(R.id.etBMI);
        etClass = findViewById(R.id.etClass);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        id = getIntent().getExtras().getInt("id");
        final String idStr = String.valueOf(id);
        String classroom = getIntent().getExtras().getString("classroom");
        String firstname = getIntent().getExtras().getString("firstname");
        String lastname = getIntent().getExtras().getString("lastname");
        String height = getIntent().getExtras().getString("height");
        String weight = getIntent().getExtras().getString("weight");
        String bmi = getIntent().getExtras().getString("bmi");

        etClass.setText(classroom);
        etFirstName.setText(firstname);
        etLastName.setText(lastname);
        etHeight.setText(height);
        etWeight.setText(weight);
        etBMI.setText(bmi);

        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etWeight!=null){
                    String heightStr = etHeight.getText().toString();
                    String weightStr = etWeight.getText().toString();
                    float heightValue = Float.parseFloat(heightStr) / 100;
                    float weightValue = Float.parseFloat(weightStr);
                    float bmi = weightValue / (heightValue * heightValue);
                    etBMI.setText(String.valueOf(bmi));
                }
            }
        });

        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etHeight!=null){
                    String heightStr = etHeight.getText().toString();
                    String weightStr = etWeight.getText().toString();
                    float heightValue = Float.parseFloat(heightStr) / 100;
                    float weightValue = Float.parseFloat(weightStr);
                    float bmi = weightValue / (heightValue * heightValue);
                    etBMI.setText(String.valueOf(bmi));
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2/student_bmi/updateStudent.php";
                HttpRequest request = new HttpRequest(url);

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("id",idStr);
                request.addData("firstname", etFirstName.getText().toString());
                request.addData("lastname", etLastName.getText().toString());
                request.addData("height", etHeight.getText().toString());
                request.addData("weight", etWeight.getText().toString());
                request.addData("bmi", etBMI.getText().toString());
                request.addData("classroom", etClass.getText().toString());

                request.execute();
                finish();
            }

            private HttpRequest.OnHttpResponseListener mHttpResponseListener =
                    new HttpRequest.OnHttpResponseListener() {
                        @Override
                        public void onResponse(String response) {

                            // process response here
                            try {
                                Log.i("JSON results:", response);
                                JSONObject jsonObj = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2/student_bmi/deleteStudent.php";
                HttpRequest request = new HttpRequest(url);

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("id",idStr);

                request.execute();
                finish();
            }

            private HttpRequest.OnHttpResponseListener mHttpResponseListener =
                    new HttpRequest.OnHttpResponseListener() {
                        @Override
                        public void onResponse(String response) {

                            // process response here
                            try {
                                Log.i("JSON results:", response);
                                JSONObject jsonObj = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Code for step 1 start
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

    }
}
