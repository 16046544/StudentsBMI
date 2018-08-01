package sg.edu.rp.studentsbmi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentsAdapter extends ArrayAdapter<Students> {


    private ArrayList<Students> alStudents;
    private Context context;
    TextView tvName, tvBMI, tvClass;

    public StudentsAdapter(Context context, int resource, ArrayList<Students> objects){
        super(context, resource, objects);
        alStudents = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        tvClass = (TextView) rowView.findViewById(R.id.textViewClass);
        tvName = (TextView) rowView.findViewById(R.id.textViewName);
        tvBMI = (TextView) rowView.findViewById(R.id.textViewBMI);

        Students student = alStudents.get(position);

        tvClass.setText(student.getClassroom().toString());
        tvName.setText(student.getFirstname()+" "+student.getLastname());
        tvBMI.setText(student.getBmi().toString());

        return rowView;
    }
}
