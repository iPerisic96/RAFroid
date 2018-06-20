package android.example.com.rafdroid;


import android.app.Dialog;
import android.content.Context;
import android.example.com.rafdroid.Model.Exam;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DummyCardAdapter extends RecyclerView.Adapter<DummyCardAdapter.ViewHolder> {

    private static ArrayList<Exam> mData;
    private Context context;
    Dialog myDialog;

    public DummyCardAdapter(ArrayList<Exam> mData, Context context){
        this.mData = new ArrayList<>();
        for(Exam e: mData){
            if(e.getType().equalsIgnoreCase("curriculum")){
                this.mData.add(e);
            }
        }
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_klk, parent, false);
        ViewHolder vHolder = new ViewHolder(v);
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
//        holder.getExamDate().setText(mData.get(position).get() + " " + mData.get(position).getLastName());
//        holder.getItemContact().setText(mData.get(position).getPhone());
//        myDialog = new Dialog(context);
//        myDialog.setContentView(R.layout.dialog_contact);
//        holder.getLayout().setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(mContext, "Test click " + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
//                TextView dialogName = (TextView) myDialog.findViewById(R.id.dialog_name);
//                TextView dialogPhone = (TextView) myDialog.findViewById(R.id.dialog_phone);
//                ImageView dialogImage = (ImageView) myDialog.findViewById(R.id.dialog_image);
//                dialogName.setText(mData.get(holder.getAdapterPosition()).getFirstName() + " " + mData.get(holder.getAdapterPosition()).getLastName());
//                dialogPhone.setText(mData.get(holder.getAdapterPosition()).getPhone());
////        dialogImage.setImageDrawable(mData.get(vHolder.getAdapterPosition()).getImage());
//                dialogImage.setImageResource(R.drawable.ic_person);
//
//                myDialog.show();

//            }
//        });

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
