package android.project.lend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HomeStatisticsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    ProductDataItem productDataItem;
    ArrayList<LendzDataItem> lendzDataItem;
    View view;
    long noOfDays = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_statistics_fragment, container, false);
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Statistics");

        Bundle args = getArguments();
        productDataItem = (ProductDataItem) args.getSerializable("selectedItem");
        lendzDataItem = (ArrayList<LendzDataItem>) args.getSerializable("selectedItemLendz");

        TextView lendzCount = view.findViewById(R.id.lendz_count);
        lendzCount.setText(lendzDataItem.size() + "");


        TextView lendzAverage = view.findViewById(R.id.lendz_average);
        String average = lendzDataItem.size() > 0 ? countLendzTimeAverage() : "0";
        lendzAverage.setText(average);

        TextView totalRevenue = view.findViewById(R.id.lendz_revenue);
        String total = countTotalRevenue();
        totalRevenue.setText(total);

        view.findViewById(R.id.home_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });

        return view;
    }

    private void openCalendar() {
        ArrayList<Calendar> dates = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        DatePickerDialog calendar = DatePickerDialog.newInstance(
                this,
                now
        );

        for (int i = 0; i < lendzDataItem.size(); i++) {
            Calendar startDay = Calendar.getInstance();
            Calendar endDay = Calendar.getInstance();
            try {
                startDay.setTime(dateFormat.parse(lendzDataItem.get(i).getStartDate()));
                endDay.setTime(dateFormat.parse(lendzDataItem.get(i).getDueDate()));

                while (startDay.compareTo(endDay) < 1) {
                    Calendar betweenDay = Calendar.getInstance();
                    betweenDay.setTime(startDay.getTime());
                    dates.add(betweenDay);
                    startDay.add(Calendar.DAY_OF_MONTH, 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Calendar[] disabled = new Calendar[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            disabled[i] = dates.get(i);
        }

        calendar.setDisabledDays(disabled);
        calendar.show(getActivity().getFragmentManager(), "home_calendar");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    private String countLendzTimeAverage() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start, end;
        long diff = 0;

        for (int i = 0; i < lendzDataItem.size(); i++) {
            try {

                String startDate = lendzDataItem.get(i).getStartDate();
                String endDate = lendzDataItem.get(i).getDueDate();
                start = sdf.parse(startDate);
                end =  sdf.parse(endDate);

                diff = end.getTime() - start.getTime();
                noOfDays += TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String lendzTimeAverage = noOfDays / lendzDataItem.size() + "";

        return lendzTimeAverage;
    }

    private String countTotalRevenue() {
        Double total;
        DecimalFormat df = new DecimalFormat("#.00");
        total = productDataItem.getPrice() * noOfDays * 0.95;
        return total == 0 ? "0" : df.format(total);
    }
}
