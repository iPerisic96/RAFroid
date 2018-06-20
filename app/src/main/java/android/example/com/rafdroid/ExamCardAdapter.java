package android.example.com.rafdroid;

import android.app.Dialog;
import android.content.Context;
import android.example.com.rafdroid.Model.Class;
import android.example.com.rafdroid.Model.Exam;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ExamCardAdapter  extends RecyclerView.Adapter<ExamCardAdapter.ViewHolder> {

    private static ArrayList<Exam> mData;
    private Context context;
    Dialog myDialog;

    public ExamCardAdapter(ArrayList<Exam> mData, Context context){
        this.mData = new ArrayList<>();
        for(Exam e: mData){
            if(e.getType().equalsIgnoreCase("exam")){
                this.mData.add(e);
            }
        }
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_klk, parent, false);
        ExamCardAdapter.ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    public static ArrayList<Exam> getmData() {
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        //todo NAPUNI GA OVDE PODACIMA
//        final int pos = position;
//        //Glide.with(context).asBitmap().load(mData.get(position).getImgUrl()).into(holder.getImage());
        holder.getExamDate().setText(mData.get(position).getDayName()+" "+mData.get(position).getStart_time().getDay() +"." +mData.get(position).getStart_time().getMonth() + ".");
        holder.getExamName().setText(mData.get(position).getName());
        holder.getExamTime().setText("Ucionica: " + mData.get(position).getClassroom().getName() + ", " + mData.get(position).getStart_time().getHours() + ":" + mData.get(position).getStart_time().getMinutes() +
                "0-" +  mData.get(position).getEnd_time().getHours() + ":00h");
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.dialog_class);
        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tv_className = (TextView) myDialog.findViewById(R.id.class_name);
                TextView tv_classType = (TextView) myDialog.findViewById(R.id.class_type);
                TextView tv_classTime = (TextView) myDialog.findViewById(R.id.class_time); //ucionica, vreme (U2, 12:15-14:00h)
                TextView tv_classGroup = (TextView) myDialog.findViewById(R.id.class_group);
                TextView tv_classProf = (TextView) myDialog.findViewById(R.id.class_prof);

                tv_className.setText(mData.get(holder.getAdapterPosition()).getName());
                tv_classType.setText(mData.get(holder.getAdapterPosition()).getType());
                tv_classTime.setText("Ucionica: " +mData.get(holder.getAdapterPosition()).getClassroom().getName() + ", " + mData.get(holder.getAdapterPosition()).getStart_time().getHours() + ":" + mData.get(holder.getAdapterPosition()).getStart_time().getMinutes() +
                        "0-" +  mData.get(holder.getAdapterPosition()).getEnd_time().getHours() + ":00h");
                tv_classGroup.setText(mData.get(holder.getAdapterPosition()).getDayName()+" "+mData.get(holder.getAdapterPosition()).getStart_time().getDay() +"." +mData.get(holder.getAdapterPosition()).getStart_time().getMonth() + ".");
                tv_classProf.setText(mData.get(holder.getAdapterPosition()).getProfessor().getName());

                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
//
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView examName;
        private TextView examTime;
        private TextView examDate;
        private RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.examName);
            examTime = itemView.findViewById(R.id.examTime);
            examDate = itemView.findViewById(R.id.examDate);
            layout = itemView.findViewById(R.id.cardRelativeLayout);
        }

        public TextView getExamName() {
            return examName;
        }

        public TextView getExamTime() {
            return examTime;
        }

        public TextView getExamDate() {
            return examDate;
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public void setExamName(TextView examName) {
            this.examName = examName;
        }

        public void setExamTime(TextView examTime) {
            this.examTime = examTime;
        }

        public void setExamDate(TextView examDate) {
            this.examDate = examDate;
        }

        public void setLayout(RelativeLayout layout) {
            this.layout = layout;
        }
    }
}
