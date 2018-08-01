package sg.edu.rp.studentsbmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etClass, etFirstName, etLastName, etHeight, etWeight, etBMI;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etBMI = findViewById(R.id.etBMI);
        etClass = findViewById(R.id.etClass);
        btnSubmit = findViewById(R.id.btnSubmit);

        etWeight.setText("0");
        etHeight.setText("0");
        String heightStr = etHeight.getText().toString();
        String weightStr = etWeight.getText().toString();
        float heightValue = Float.parseFloat(heightStr) / 100;
        float weightValue = Float.parseFloat(weightStr);
        float bmi = weightValue / (heightValue * heightValue);
        etBMI.setText(String.valueOf(bmi));


        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWeight!=null){

                    try{
                        String heightStr = etHeight.getText().toString();
                        String weightStr = etWeight.getText().toString();
                        float heightValue = Float.parseFloat(heightStr) / 100;
                        float weightValue = Float.parseFloat(weightStr);

                        if (heightValue != 0 && weightValue != 0){
                            float bmi = weightValue / (heightValue * heightValue);
                            etBMI.setText(String.valueOf(bmi));
                        } else{
                            etBMI.setText("");
                        }

                    } catch (Exception ex){
                        etBMI.setText("");
                    }

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
                if (etHeight!=null){
                    try{
                        String heightStr = etHeight.getText().toString();
                        String weightStr = etWeight.getText().toString();

                        float heightValue = Float.parseFloat(heightStr) / 100;
                        float weightValue = Float.parseFloat(weightStr);

                        if (heightValue != 0 && weightValue != 0){
                            float bmi = weightValue / (heightValue * heightValue);
                            etBMI.setText(String.valueOf(bmi));
                        } else{
                            etBMI.setText("");
                        }

                    } catch (Exception ex){
                        etBMI.setText("");
                    }
                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfEmpty()) {
                    String url = "http://10.0.2.2/student_bmi/addStudent.php";
                    HttpRequest request = new HttpRequest(url);

                    request.setOnHttpResponseListener(mHttpResponseListener);
                    request.setMethod("POST");
                    request.addData("firstname", etFirstName.getText().toString());
                    request.addData("lastname", etLastName.getText().toString());
                    request.addData("height", etHeight.getText().toString());
                    request.addData("weight", etWeight.getText().toString());
                    request.addData("bmi", etBMI.getText().toString());
                    request.addData("classroom", etClass.getText().toString());
                    request.execute();

                    finish();
                } else {
                    Toast.makeText(AddStudentActivity.this, "TextFields cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }

            private HttpRequest.OnHttpResponseListener mHttpResponseListener =
                    new HttpRequest.OnHttpResponseListener() {
                        @Override
                        public void onResponse(String response) {

                            // process response here
                            try {
                                Log.i("JSON results:", response);
                                JSONObject jsonObj = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
        });


    }

    private Boolean checkIfEmpty() {
        Boolean notEmpty = false;
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etBMI = findViewById(R.id.etBMI);
        etClass = findViewById(R.id.etClass);
        if (!TextUtils.isEmpty(etFirstName.getText().toString()) && !TextUtils.isEmpty(etLastName.getText().toString()) && !TextUtils.isEmpty(etHeight.getText().toString()) && !TextUtils.isEmpty(etWeight.getText().toString()) && !TextUtils.isEmpty(etBMI.getText().toString()) && !TextUtils.isEmpty(etClass.getText().toString())) {
            notEmpty = true;
        } else {
            notEmpty = false;
        }
        return notEmpty;
    }
}
