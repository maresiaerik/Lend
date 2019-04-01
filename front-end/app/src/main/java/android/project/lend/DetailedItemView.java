package android.project.lend;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DetailedItemView extends Fragment implements DatePickerDialog.OnDateSetListener{



    View view;
    ProductDataItem item;
    LinearLayout l;
    Dialog calendarView;
    DatePickerDialog startCalendar;
    DatePickerDialog endCalendar;
    SimpleDateFormat startDate;
    Calendar now = Calendar.getInstance();
    Button borrowBtn;
    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;


    public interface onConfirmationListener {
        public void onConfirmationOpened(ArrayList<ProductDataItem> addedItems);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detailed_item_fragment, container, false);
        TextView heading = view.findViewById(R.id.page_title);
        TextView categoryName = view.findViewById(R.id.detailed_category_name);

        Bundle args = getArguments();
        item = (ProductDataItem) args.getSerializable("selectedItem");


        heading.setText(item.getName());
        categoryName.setText(item.getCategory());
        TextView description = view.findViewById(R.id.detailed_item_description);
        description.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin id mi cursus, porttitor turpis a, finibus ligula. Curabitur a malesuada dui. In finibus augue felis, id venenatis nibh fringilla id. Donec rutrum bibendum est pharetra volutpat. Donec sit amet dolor blandit, viverra ante ut, elementum neque.");
        ViewPager imageContainer = view.findViewById(R.id.detailed_image_carousel);
        ImageCarousel adapter = new ImageCarousel(getContext(), item);
        imageContainer.setAdapter(adapter);

        final Button startDate = view.findViewById(R.id.detailed_item_start_date_button);
        Button endDate = view.findViewById(R.id.detailed_item_end_date_button);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openStartCalendar();
            }
        });


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openEndCalendar();

            }
        });

        //Setting Borrow Button Listener
        borrowBtn = view.findViewById(R.id.detailed_item_borrow_button);
        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemData", item);
                */

                Intent confirmIntent = new Intent(getContext(), ExploreConfirmActivity.class);


                ArrayList<ProductDataItem> addedItem = new ArrayList<>();
                addedItem.add(item);

                onConfirmationListener listener = (onConfirmationListener) new ExploreConfirmActivity();

                listener.onConfirmationOpened(addedItem);

                startActivity(confirmIntent);
            }
        });


        return view;
    }

    private void openStartCalendar() {



        startCalendar = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        startCalendar.setMinDate(now);


        //setDates();

        startCalendar.show(getActivity().getFragmentManager(),"DatepickerdialogStartDate");

    }

    private void openEndCalendar() {
        endCalendar = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );



        endCalendar.setMinDate(now);





        startCalendar.show(getActivity().getFragmentManager(),"DatepickerdialogEndDate");

    }


    private void setUpStartDateCalendar() {
        TextView title = calendarView.findViewById(R.id.calendar_dialog_title);
        title.setText("Set start date");
    }

    private void setUpEndDateCalendar() {
        TextView title = calendarView.findViewById(R.id.calendar_dialog_title);
        title.setText("Set end date");
    }

    public void setDates() {


        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            cal.setTime(sdf.parse("Tue May 14 16:02:37 GMT 2019"));// all done

            SimpleDateFormat aaa = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            cal2.setTime(aaa.parse("Wed May 15 16:02:37 GMT 2019"));// all done

            SimpleDateFormat bbb = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            cal3.setTime(bbb.parse("Thu May 16 16:02:37 GMT 2019"));// all done
        } catch (ParseException p) {
            Log.d("CALENDAR_PARSE_ERROR", p + "");
        }


        Calendar[] a = {
                cal,
                cal2,
                cal3
        };
       //dpd.setDisabledDays(a);
    }


    @Override
    public void onDateSet(DatePickerDialog v, int year, int monthOfYear, int dayOfMonth) {


        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        TextView startDayText = view.findViewById(R.id.startday_text);
        startDayText.setText(date);
    }

    private class OpenCalendar {

        public OpenCalendar() {

        }
    }


}



