package com.example.mohit.blockchain.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohit.blockchain.R;
import com.example.mohit.blockchain.modal.ProjectAppliedInfoModel;
import com.example.mohit.blockchain.modal.ProjectInfoModel;

import java.util.List;

/**
 * Created by Arun on 15-03-2017.
 */

public class ProjectAppliedInfoAdapter extends RecyclerView.Adapter<ProjectAppliedInfoAdapter.MyProjectAppliedInfoHolder> {

    List<ProjectAppliedInfoModel> modelList;

    public ProjectAppliedInfoAdapter(List<ProjectAppliedInfoModel> modelList){
        this.modelList=modelList;
    }

    @Override
    public MyProjectAppliedInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_applied_project,parent,false);
        return new MyProjectAppliedInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(MyProjectAppliedInfoHolder holder, int position) {
        ProjectAppliedInfoModel model=modelList.get(position);
        holder.txtProjectName.setText(model.getProjectName());
        holder.txtDayValue.setText(model.getDayValue());
        holder.txtBidValue.setText(model.getBidValue());
        holder.txtExpertLevel.setText(model.getExpertLevel());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyProjectAppliedInfoHolder extends RecyclerView.ViewHolder{

        TextView txtProjectName,txtDayValue,txtExpertLevel,txtBidValue;
        public MyProjectAppliedInfoHolder(View itemView) {
            super(itemView);
            txtProjectName= (TextView) itemView.findViewById(R.id.project_name);
            txtDayValue= (TextView) itemView.findViewById(R.id.day_value);
            txtBidValue= (TextView) itemView.findViewById(R.id.bid);
            txtExpertLevel= (TextView) itemView.findViewById(R.id.expert_level);



        }
    }
}
