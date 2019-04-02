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
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter;

import java.io.Serializable;
import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;


public class DetailedItemView extends Fragment implements DatePickerDialog.OnDateSetListener {


    View view;
    ProductDataItem item;
    LinearLayout l;
    Dialog calendarView;
    DatePickerDialog startCalendar;
    DatePickerDialog endCalendar;

    Calendar now = Calendar.getInstance();
    Button borrowBtn;
    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;
    String startTag = "DatepickerdialogStart", endtag = "DatepickerdialogEnd";
    Integer givenStartYear, givenStartMonth, givenStartDayOfMonth;
    Button startDate;
    Button endDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");




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

        startDate = view.findViewById(R.id.detailed_item_start_date_button);
        endDate = view.findViewById(R.id.detailed_item_end_date_button);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openStartCalendar();
            }
        });


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openEndCalendar();

            }
        });

        //Setting Borrow Button Listener
        borrowBtn = view.findViewById(R.id.detailed_item_borrow_button);
        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        startCalendar.setDisabledDays(setDates());

        startCalendar.show(getActivity().getFragmentManager(),startTag);

    }

    private Calendar[] setDates() {

        Calendar a = now.getInstance(), b = now.getInstance(), c = now.getInstance(), d = now.getInstance(), e = now.getInstance(), f  = now.getInstance();
        try{
            a.setTime(dateFormat.parse("04/04/2019"));
            b.setTime(dateFormat.parse("05/04/2019"));
            c.setTime(dateFormat.parse("06/04/2019"));
            d.setTime(dateFormat.parse("12/04/2019"));
            e.setTime(dateFormat.parse("18/04/2019"));
            f.setTime(dateFormat.parse("23/05/2019"));
        }
        catch (ParseException fff) {
            Log.d("FAIL", fff + " ");
        }

        Calendar[] disabled = {
            a,b,c,d,e,f
        };

        return disabled;
    }

    private void openEndCalendar() {
        endCalendar = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        Calendar givenDate = now.getInstance();

        try {
            givenDate.setTime(dateFormat.parse(givenStartDayOfMonth + "/" + givenStartMonth + "/" + givenStartYear));
        }
        catch (ParseException e) {
            Log.d("INSIDEOPENENDCALENDAR", e + " ");
        }



        endCalendar.setMinDate(givenDate);


        Calendar[] startDay = {
            givenDate
        };

        Calendar[] disabled = setDates();


        if(disabled.length > 0) {

            Calendar[] maxSelectableDay = {
                    getNextDisabledDate(disabled)
            };

            if(maxSelectableDay[0] == null) {
                endCalendar.setDisabledDays(disabled);
            }
            else {
                endCalendar.setMaxDate(maxSelectableDay[0]);
            }
        }
        else {
            endCalendar.setDisabledDays(disabled);

        }


        endCalendar.setDisabledDays(disabled);
        endCalendar.setHighlightedDays(startDay);

        endCalendar.show(getActivity().getFragmentManager(), endtag);

    }

    private Calendar getNextDisabledDate(Calendar[] d) {

        List<Calendar> disabled = Arrays.asList(d);

        ArrayList<Calendar> newDisabled = new ArrayList<>();

        Calendar startDate = now.getInstance();


        try {
            startDate.setTime(dateFormat.parse(givenStartDayOfMonth + "/" + givenStartMonth + "/" + givenStartYear));
        }
        catch (ParseException e) {
            Log.d("INSIDEGETNEXTDISABLEDDATE", e + " ");
        }

        for (int i = 0; i < disabled.size() ; i++) {

            if(disabled.get(i).after(startDate)) {
                newDisabled.add(disabled.get(i));
            }
        }

        Log.d("LENGTHOFARRAY", newDisabled.size() + " ");


        if(newDisabled.size() < 1) {
            //return something empty
            Calendar[] r = new Calendar[1];
            r[0] = null;
            return r[0];
        }
        Calendar[] nextDate = new Calendar[newDisabled.size()];

        for (int i = 0; i < newDisabled.size(); i++) {
            Log.d("INDEXTEST", i + " ");

            nextDate[i] = newDisabled.get(i);
        }

        Arrays.sort(nextDate);


        return nextDate[0];

    }


    @Override
    public void onDateSet(DatePickerDialog v, int year, int monthOfYear, int dayOfMonth) {

        Log.d("HELLOOOO", dayOfMonth + " ");

        if(v.getTag() == startTag) {
            givenStartYear = year;
            givenStartMonth = monthOfYear + 1;
            givenStartDayOfMonth = dayOfMonth;

            String d = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

            startDate.setText(d);

        }
        else if(v.getTag() == endtag){

            String d = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            endDate.setText(d);

        }

    }
}



