package sg.edu.rp.studentsbmi;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvStudents;
    ArrayList<Students> alStudents;
    ArrayAdapter<Students> aaStudents;
    ImageButton btnSearch;
    EditText etNameSearch;
    FloatingActionButton fbAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStudents = (ListView) findViewById(R.id.lvStudents);
        alStudents = new ArrayList<Students>();

        aaStudents = new StudentsAdapter(this, R.layout.row, alStudents);
        lvStudents.setAdapter(aaStudents);

        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        etNameSearch = (EditText) findViewById(R.id.etNameSearch);
        fbAdd = (FloatingActionButton) findViewById(R.id.fbAdd);

        etNameSearch.clearFocus();

        FloatingActionButton fab = findViewById(R.id.fbAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivity(a);
            }
        });


        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Students selectedStudent = alStudents.get(position);

                Intent i = new Intent(MainActivity.this, ViewStudentActivity.class);
                int strId = alStudents.get(position).getId();
                String classroom = alStudents.get(position).getClassroom();
                String firstname = alStudents.get(position).getFirstname();
                String lastname = alStudents.get(position).getLastname();
                String height = alStudents.get(position).getHeight().toString();
                String weight = alStudents.get(position).getWeight().toString();
                String bmi = alStudents.get(position).getBmi().toString();
                i.putExtra("id", strId);
                i.putExtra("classroom", classroom);
                i.putExtra("firstname", firstname);
                i.putExtra("lastname", lastname);
                i.putExtra("height", height);
                i.putExtra("weight", weight);
                i.putExtra("bmi", bmi);

                startActivity(i);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etNameSearch.getText().toString().trim().length() !=0) {
                    alStudents.clear();
                    String url = "http://10.0.2.2/student_bmi/getStudentsByName.php";
                    HttpRequest request = new HttpRequest(url);
                    request.setOnHttpResponseListener(mHttpResponseListener);
                    request.setMethod("POST");
                    request.addData("search", etNameSearch.getText().toString());
                    request.execute();
                    if (alStudents.isEmpty()){
                        Students blank = new Students(0,0.0,0.0,0.0,"no","result","");
                        alStudents.add(0,blank);
                        Toast.makeText(MainActivity.this, "No Results", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        etNameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etNameSearch.getText().toString().trim().length() !=0) {
                    alStudents.clear();
                    String url = "http://10.0.2.2/student_bmi/getStudentsByName.php";
                    HttpRequest request = new HttpRequest(url);
                    request.setOnHttpResponseListener(mHttpResponseListener);
                    request.setMethod("POST");
                    request.addData("search", etNameSearch.getText().toString());
                    request.execute();
                    if (alStudents.isEmpty()){
                        Students blank = new Students(0,0.0,0.0,0.0,"no","result","");
                        alStudents.add(0,blank);
                        Toast.makeText(MainActivity.this, "No Results", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }


    protected void onResume() {
        super.onResume();

        alStudents.clear();

        String url = "http://10.0.2.2/student_bmi/getStudents.php";
        HttpRequest request = new HttpRequest(url);

        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("GET");
        request.execute();

    }

    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response) {

                    // process response here
                    try {
                        Log.i("JSON results:", response);
                        JSONArray jsonArray = new JSONArray(response);
                        alStudents.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = jsonArray.getJSONObject(i);


                            int id = jsonObj.getInt("id");
                            Double height = jsonObj.getDouble("height");
                            Double weight = jsonObj.getDouble("weight");
                            Double bmi = jsonObj.getDouble("bmi");
                            String firstname = jsonObj.getString("firstname");
                            String lastname = jsonObj.getString("lastname");
                            String classroom = jsonObj.getString("class");


                            Students students = new Students(id, height, weight, bmi, firstname, lastname, classroom);
                            alStudents.add(students);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    aaStudents.notifyDataSetChanged();
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            alStudents.clear();


            // Code for step 1 start
            String url = "http://10.0.2.2/student_bmi/getStudents.php";
            HttpRequest request = new HttpRequest(url);

            request.setOnHttpResponseListener(mHttpResponseListener);
            request.setMethod("GET");
            request.execute();
        }
        return super.onOptionsItemSelected(item);
    }
}
