package com.example.bookclubapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclubapp.AdminMeetingDetails;
import com.example.bookclubapp.R;
import com.example.bookclubapp.models.Meeting;

import java.util.List;
import java.util.Objects;

public class MeetingListRecyclerViewHelper extends RecyclerView.Adapter<MeetingListRecyclerViewHelper.MeetingListViewHolder> {

    private List<Meeting> meetingListItems;
    private Context context;

    public MeetingListRecyclerViewHelper(List<Meeting> meetingListItems, Context context){
        this.meetingListItems = meetingListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MeetingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_list, parent, false);
        return new MeetingListViewHolder(v);
    }

    public class MeetingListViewHolder extends RecyclerView.ViewHolder{

        // ________________________________________________________________________
        // объявляем view из meeting_list
        // ________________________________________________________________________

        public TextView meeting_book, meeting_time, meeting_place, meeting_price, meeting_passed, meeting_record;
        private LinearLayout itemLayout;
        public MeetingListViewHolder(@NonNull View itemView) {
            super(itemView);

            // ________________________________________________________________________
            // находим view из meeting_list
            // ________________________________________________________________________

            meeting_book = itemView.findViewById(R.id.meeting_book);
            meeting_time = itemView.findViewById(R.id.meeting_time);
            meeting_place = itemView.findViewById(R.id.meeting_place);
            meeting_price = itemView.findViewById(R.id.meeting_price);
            meeting_passed = itemView.findViewById(R.id.meeting_passed);
            meeting_record = itemView.findViewById(R.id.meeting_record);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    @Override
    public int getItemCount() {
        return meetingListItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, int position) {
        Meeting meeting = this.meetingListItems.get(position);

        // ________________________________________________________________________
        // ОТОБРАЖЕНИЕ карточки (сетим текст)
        // ________________________________________________________________________

        holder.meeting_book.setText(meeting.getBook());
        String dateInput = meeting.getDate().toString() + " в 16:00";
        holder.meeting_time.setText(dateInput);
        holder.meeting_place.setText(meeting.getPlace());
        String priceInfo = String.valueOf(meeting.getPrice()) + " p";
        holder.meeting_price.setText(priceInfo);


        if (Objects.equals(meeting.getIs_passed(), "1")){
            holder.meeting_passed.setText("Встреча прошла");
        } else {
            holder.meeting_passed.setVisibility(View.GONE);
        }
        int n = meeting.getUsers().size();
        int mode = n % 10;
        String count;
        if (n == 0){
            holder.meeting_record.setVisibility(View.GONE);
        }
        if (mode == 1){
            count = "На встречу записан " + n + " человек";
        } else if (mode == 2 || mode == 3 || mode == 4){
            count = "На встречу записано " + n + " человека";
        } else {
            count = "На встречу записано " + n + " человек";
        }
        holder.meeting_record.setText(count);

        // ________________________________________________________________________
        // переход на карточку по клику и проброс данных
        // ________________________________________________________________________


        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "You clicked: " + meeting.getMeeting_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminMeetingDetails.class);
                String dateInput = meeting.getDate().toString() + " в 16:00";

                intent.putExtra("meetingId", String.valueOf(meeting.getMeeting_id()));
                intent.putExtra("name", meeting.getBook());
                intent.putExtra("time", meeting.getTime());
                intent.putExtra("date", dateInput);
                intent.putExtra("place", meeting.getPlace());
                String priceInfo = String.valueOf(meeting.getPrice()) + " p";
                intent.putExtra("price", priceInfo);
                intent.putExtra("users", String.valueOf(meeting.getUserList()));
                context.startActivity(intent);


            }


        });

    }
}





