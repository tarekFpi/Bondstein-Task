package com.example.dondstein_task.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dondstein_task.Model.Datum;
import com.example.dondstein_task.Model.Stores_showModel;
import com.example.dondstein_task.R;

import java.util.List;

public class StoresShowAdapter extends RecyclerView.Adapter<StoresShowAdapter.MyviewHolder> {

    private static int listposition=-1;

    private  static  onItemClickLisiner clickLisiner;

     private List<Stores_showModel> stores_showModelList;
    private Context context;

   // Datum[]stores_showModelList;

     public StoresShowAdapter(List<Stores_showModel> stores_showModelList, Context context) {
        this.stores_showModelList = stores_showModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater=LayoutInflater.from(context);

        View view= layoutInflater.inflate(R.layout.storage_layout,parent,false);

        return new MyviewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

        Stores_showModel item_position=stores_showModelList.get(position);

       // Datum item_position=stores_showModelList[position];

        myviewHolder.textView_id.setText("Id :"+item_position.getId());
        myviewHolder.textView_name.setText("Name :"+item_position.getName());
        myviewHolder.textView_address.setText("Address :"+item_position.getAddress());

        setAnimiton(myviewHolder.itemView,position);
    }

    void setAnimiton(View viewAnimition,int position) {

        if (position > listposition) {

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(1000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }

    @Override
    public int getItemCount() {
        return stores_showModelList.size();
    }

  public   class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_id,textView_name,textView_address;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_id=(TextView)itemView.findViewById(R.id.text_storesId);

            textView_name=itemView.findViewById(R.id.text_storesName);

            textView_address=itemView.findViewById(R.id.text_storesDetails);

            itemView.setOnClickListener(this);
        }

      @Override
      public void onClick(View view) {

          int position = getLayoutPosition();
          clickLisiner.OnClick_Lisiner(position);
      }
  }

    public interface  onItemClickLisiner{
        void OnClick_Lisiner(int position);
    }

    public  void setOnItemClick(onItemClickLisiner clickLisiner){
        this. clickLisiner=clickLisiner;
    }

}
