package com.example.mohit.blockchain.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohit.blockchain.R;
import com.example.mohit.blockchain.modal.ProjectAppliedInfoModel;
import com.example.mohit.blockchain.modal.ProjectAppliedProviderInfoModel;

import java.util.List;

/**
 * Created by Arun on 15-03-2017.
 */

public class ProjectAppliedProviderInfoAdapter extends RecyclerView.Adapter<ProjectAppliedProviderInfoAdapter.MyProjectAppliedProviderInfoHolder> {

    List<ProjectAppliedProviderInfoModel> modelList;

    public ProjectAppliedProviderInfoAdapter(List<ProjectAppliedProviderInfoModel> modelList){
        this.modelList=modelList;
    }

    @Override
    public MyProjectAppliedProviderInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_applied_project_provider,parent,false);
        return new MyProjectAppliedProviderInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(MyProjectAppliedProviderInfoHolder holder, int position) {
        ProjectAppliedProviderInfoModel model=modelList.get(position);
        holder.txtProjectName.setText(model.getProjectName());
        holder.txtDayValue.setText(model.getDayValue());
        holder.txtProjectAbstract.setText(model.getProAbstract());



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyProjectAppliedProviderInfoHolder extends RecyclerView.ViewHolder{

        TextView txtProjectName,txtDayValue,txtProjectAbstract;
        public MyProjectAppliedProviderInfoHolder(View itemView) {
            super(itemView);
            txtProjectName= (TextView) itemView.findViewById(R.id.project_name);
            txtDayValue= (TextView) itemView.findViewById(R.id.day_value);
            txtProjectAbstract= (TextView) itemView.findViewById(R.id.project_abstract);



        }
    }
}
