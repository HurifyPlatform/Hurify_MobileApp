package com.example.mohit.blockchain.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohit.blockchain.R;
import com.example.mohit.blockchain.modal.ProjectInfoModel;


import java.util.List;

/**
 * Created by Arun on 15-03-2017.
 */

public class ProjectInfoAdapter extends RecyclerView.Adapter<ProjectInfoAdapter.MyProjectInfoHolder> {

    List<ProjectInfoModel> modelList;

    public ProjectInfoAdapter(List<ProjectInfoModel> modelList){
        this.modelList=modelList;
    }

    @Override
    public MyProjectInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new MyProjectInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(MyProjectInfoHolder holder, int position) {
        ProjectInfoModel model=modelList.get(position);
        holder.txtProjectName.setText(model.getProjectName());
        holder.txtDayValue.setText(model.getDayValue());
        holder.txtTechnology.setText(model.getTechnology());
        holder.txtCategory1.setText(model.getCategory1());
        holder.txtProjectAbstract.setText(model.getProjectAbstract());
        holder.txtPriceValue.setText(model.getPriceValue());
        holder.txtExpertLevel.setText(model.getExpertLevel());
        holder.txtEstTime.setText(model.getEstTime());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyProjectInfoHolder extends RecyclerView.ViewHolder{

        TextView txtProjectName,txtDayValue,txtTechnology,txtCategory1,txtProjectAbstract,txtPriceValue,txtExpertLevel,txtEstTime;
        public MyProjectInfoHolder(View itemView) {
            super(itemView);
            txtProjectName= (TextView) itemView.findViewById(R.id.project_name);
            txtDayValue= (TextView) itemView.findViewById(R.id.day_value);
            txtTechnology= (TextView) itemView.findViewById(R.id.technology);
            txtCategory1= (TextView) itemView.findViewById(R.id.category1);
            txtProjectAbstract= (TextView) itemView.findViewById(R.id.project_abstract);
            txtPriceValue= (TextView) itemView.findViewById(R.id.price_value);
            txtExpertLevel= (TextView) itemView.findViewById(R.id.expert_level);
            txtEstTime= (TextView) itemView.findViewById(R.id.estdays);


        }
    }
}
