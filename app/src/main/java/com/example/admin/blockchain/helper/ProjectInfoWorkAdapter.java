package com.example.admin.blockchain.helper;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.blockchain.ActivityProfileWorkEdit;
import com.example.admin.blockchain.ActivityProjectFullDesc;
import com.example.admin.blockchain.ActivityProjectFullDescWork;
import com.example.admin.blockchain.modal.ProjectInfoWorkModel;
import com.example.admin.blockchain.R;

import java.util.List;

/**
 * Created by Arun on 15-03-2017.
 */

public class ProjectInfoWorkAdapter extends RecyclerView.Adapter<ProjectInfoWorkAdapter.MyProjectInfoHolder> {

    List<ProjectInfoWorkModel> modelList;
    String some;



    public ProjectInfoWorkAdapter(List<ProjectInfoWorkModel> modelList) {
        this.modelList=modelList;
    }

    @Override
    public MyProjectInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProjectInfoWorkModel model=modelList.get(viewType);
        Log.d("modal",model.getProjectName());
        Log.d("modal","oncreate");
       // some=model.getId()+"";
        if(model.getStatus().equals("Submitted"))
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_work,parent,false);
            return new MyProjectInfoHolder(view,model.getId()+"");
        }else{
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_work1,parent,false);
            view.getId();
            Log.d("viewId",view.getId()+"");
            return new MyProjectInfoHolder(view,model.getId()+"");
        }

    }

    @Override
    public void onBindViewHolder(MyProjectInfoHolder holder, int position) {
        ProjectInfoWorkModel model=modelList.get(position);
        Log.d("modal","onbind");
        if(model.getStatus().equals("Submitted")) {
            holder.txtProjectName.setText(model.getProjectName());
            holder.txtDayValue.setText(model.getDayValue());
            holder.txtTechnology.setText(model.getTechnology());
            holder.txtCategory1.setText(model.getCategory1());
            holder.txtProjectAbstract.setText(model.getProjectAbstract());
            holder.txtPriceValue.setText(model.getPriceValue());
            holder.txtExpertLevel.setText(model.getExpertLevel());
            holder.txtEstTime.setText(model.getEstTime());
            holder.txtApplicationCount.setText(model.getApplicationCount());
            holder.txtStatus.setText(model.getStatus());
            holder.txtVerify.setText(model.getVerify());
        }else {

            holder.txtProjectName.setText(model.getProjectName());
            holder.txtDayValue.setText(model.getDayValue());
            holder.txtTechnology.setText(model.getTechnology());
            holder.txtCategory1.setText(model.getCategory1());
            holder.txtProjectAbstract.setText(model.getProjectAbstract());
            holder.txtPriceValue.setText(model.getPriceValue());
            holder.txtExpertLevel.setText(model.getExpertLevel());
            holder.txtEstTime.setText(model.getEstTime());
            holder.txtApplicationCount.setText(model.getApplicationCount());
            holder.txtStatus.setText(model.getStatus());

        }

    }

    @Override
    public int getItemViewType(int position) {
        ProjectInfoWorkModel model=modelList.get(position);
        //Log.d("modalstatus",model.getStatus());

        Log.d("x","getItemview");
        if(model.getStatus().equals("Submitted"))
        {
          return position;
        }else{
            return position;
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyProjectInfoHolder extends RecyclerView.ViewHolder{

        TextView txtProjectName,txtDayValue,txtTechnology,txtCategory1,txtProjectAbstract,txtPriceValue,txtExpertLevel,txtEstTime,txtApplicationCount,txtStatus,txtVerify;
        public MyProjectInfoHolder(View itemView, final String id) {
            super(itemView);

            Log.d("modal","MyProjectInfoHolder");
            Log.d("viewId",itemView.getId()+"");
            switch (itemView.getId())
            {
                case R.id.item_list_work :
                          Log.d("hii","list work");
                    txtProjectName= (TextView) itemView.findViewById(R.id.project_name);
                    txtDayValue= (TextView) itemView.findViewById(R.id.day_value);
                    txtTechnology= (TextView) itemView.findViewById(R.id.technology);
                    txtCategory1= (TextView) itemView.findViewById(R.id.category1);
                    txtProjectAbstract= (TextView) itemView.findViewById(R.id.project_abstract);
                    txtPriceValue= (TextView) itemView.findViewById(R.id.price_value);
                    txtExpertLevel= (TextView) itemView.findViewById(R.id.expert_level);
                    txtEstTime= (TextView) itemView.findViewById(R.id.estdays);
                    txtApplicationCount= (TextView) itemView.findViewById(R.id.application);
                    txtStatus= (TextView) itemView.findViewById(R.id.txtStatus);
                    txtVerify=(TextView) itemView.findViewById(R.id.txtVerify);
                    txtVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(),ActivityProfileWorkEdit.class);
                            v.getContext().startActivity(intent);

                        }
                    });
                    txtProjectName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(),ActivityProjectFullDescWork.class);
                            intent.putExtra("id",id);
                            v.getContext().startActivity(intent);

                        }
                    });
                    break;

                case R.id.item_list_work1 :
                    Log.d("hii","list work1");
                    txtProjectName= (TextView) itemView.findViewById(R.id.project_name);
                    txtDayValue= (TextView) itemView.findViewById(R.id.day_value);
                    txtTechnology= (TextView) itemView.findViewById(R.id.technology);
                    txtCategory1= (TextView) itemView.findViewById(R.id.category1);
                    txtProjectAbstract= (TextView) itemView.findViewById(R.id.project_abstract);
                    txtPriceValue= (TextView) itemView.findViewById(R.id.price_value);
                    txtExpertLevel= (TextView) itemView.findViewById(R.id.expert_level);
                    txtEstTime= (TextView) itemView.findViewById(R.id.estdays);
                    txtApplicationCount= (TextView) itemView.findViewById(R.id.application);
                    txtStatus= (TextView) itemView.findViewById(R.id.txtStatus);
                    txtProjectName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(),ActivityProjectFullDescWork.class);
                            intent.putExtra("id",id);
                            v.getContext().startActivity(intent);

                        }
                    });
                    break;

            }
          /*  txtProjectName= (TextView) itemView.findViewById(R.id.project_name);
            txtDayValue= (TextView) itemView.findViewById(R.id.day_value);
            txtTechnology= (TextView) itemView.findViewById(R.id.technology);
            txtCategory1= (TextView) itemView.findViewById(R.id.category1);
            txtProjectAbstract= (TextView) itemView.findViewById(R.id.project_abstract);
            txtPriceValue= (TextView) itemView.findViewById(R.id.price_value);
            txtExpertLevel= (TextView) itemView.findViewById(R.id.expert_level);
            txtEstTime= (TextView) itemView.findViewById(R.id.estdays);
            txtApplicationCount= (TextView) itemView.findViewById(R.id.application);*/
            //txtStatus= (TextView) itemView.findViewById(R.id.txtStatus);


        }
    }
}
