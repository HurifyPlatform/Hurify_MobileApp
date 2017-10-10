package com.example.admin.blockchain.helper;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.blockchain.ActivityProjectFullDescWork;
import com.example.admin.blockchain.NegotiationFormClient;
import com.example.admin.blockchain.R;
import com.example.admin.blockchain.modal.ProjectAppliedInfoModel;


import java.util.List;

/**
 * Created by Arun on 15-03-2017.
 */

public class ProjectAppliedInfoAdapter extends RecyclerView.Adapter<ProjectAppliedInfoAdapter.MyProjectAppliedInfoHolder> {

    List<ProjectAppliedInfoModel> modelList;



    public ProjectAppliedInfoAdapter(List<ProjectAppliedInfoModel> modelList) {
        this.modelList=modelList;
    }

    @Override
    public MyProjectAppliedInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProjectAppliedInfoModel model=modelList.get(viewType);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_applied_project,parent,false);
        Log.d("s",viewType+"");
        return new MyProjectAppliedInfoHolder(view,model.getStatus(),model.getSelect(),model.getProjectName());
    }

    @Override
    public void onBindViewHolder(MyProjectAppliedInfoHolder holder, int position) {
        ProjectAppliedInfoModel model=modelList.get(position);

            holder.txtProjectName.setText(model.getProjectName());
            holder.txtDayValue.setText(model.getDayValue());
            holder.txtBidValue.setText(model.getBidValue());
            holder.txtExpertLevel.setText(model.getExpertLevel());
            holder.txtSelect.setText(model.getSelect());



    }

    @Override
    public int getItemViewType(int position) {
        //Implement your logic here
        //ProjectAppliedInfoModel model=modelList.get(position);
        Log.d("pos",position+"");
        return position;
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyProjectAppliedInfoHolder extends RecyclerView.ViewHolder{

        TextView txtProjectName,txtDayValue,txtExpertLevel,txtBidValue,txtSelect;
        public MyProjectAppliedInfoHolder(View itemView,String Status,String Type, final String Applier_Email) {
            super(itemView);
            txtProjectName= (TextView) itemView.findViewById(R.id.project_name);
            txtDayValue= (TextView) itemView.findViewById(R.id.day_value);
            txtBidValue= (TextView) itemView.findViewById(R.id.bid);
            txtExpertLevel= (TextView) itemView.findViewById(R.id.expert_level);
            txtSelect= (TextView) itemView.findViewById(R.id.select);

            Log.d("status",Status);
            Log.d("Type",Type);

            if(Type.equals("Select") && Status.equals("DevEvaluation"))
            {
                Log.d("runif","running");
                txtSelect.setVisibility(View.INVISIBLE);
            }

            txtSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),NegotiationFormClient.class);
                    intent.putExtra("applier_email",Applier_Email);
                    v.getContext().startActivity(intent);

                }
            });


        }
    }
}
