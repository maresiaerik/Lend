package android.project.lend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
        view = inflater.inflate(R.layout.fragment_home_statistics, container, false);

        Bundle args = getArguments();
        productDataItem = (ProductDataItem) args.getSerializable("selectedItem");
        lendzDataItem = (ArrayList<LendzDataItem>) args.getSerializable("selectedItemLendz");

        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText(productDataItem.getName().length() > 10 ? productDataItem.getName().substring(0, 10) + "..." : productDataItem.getName());

        TextView lendzCount = view.findViewById(R.id.lendz_count);
        lendzCount.setText(lendzDataItem.size() + "");

        TextView popularMonth = view.findViewById(R.id.lendz_month);
        popularMonth.setText(getMostPopularMonth());

        TextView lendzAverage = view.findViewById(R.id.lendz_average);
        String average = lendzDataItem.size() > 0 ? countLendzTimeAverage() : "0";
        lendzAverage.setText(average);

        TextView totalRevenue = view.findViewById(R.id.lendz_revenue);
        String total = countTotalRevenue();
        totalRevenue.setText(total);

        RatingBar rating = view.findViewById(R.id.lendz_rating);
        rating.setRating(productDataItem.getRating() == null ? 0 : productDataItem.getRating());

        TextView totalDays = view.findViewById(R.id.lendz_total_borrow);
        totalDays.setText(noOfDays + "");

        view.findViewById(R.id.home_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });

        return view;
    }

    private String getAverageRating() {
        Integer total = 0;
        Integer count = 0;
        for (int i = 0; i < lendzDataItem.size(); i++) {
            if (lendzDataItem.get(i).getRating() != null) {
                total += lendzDataItem.get(i).getRating();
                count++;
            }
        }
        DecimalFormat df = new DecimalFormat("#.0");
        if (total == 0) {
            return "0";
        } else {
            return df.format(total / count);
        }

    }

    private void openCalendar() {
        ArrayList<Calendar> dates = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        DatePickerDialog calendar = DatePickerDialog.newInstance(this, now);

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
                end = sdf.parse(endDate);

                diff = end.getTime() - start.getTime();
                noOfDays += TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String lendzTimeAverage = noOfDays / lendzDataItem.size() + "";

        return lendzTimeAverage;
    }

    private String getMostPopularMonth() {
        Month jan = new Month(1, 0, "January"), feb = new Month(2, 0, "February"), mar = new Month(3, 0, "March"), apr = new Month(4, 0, "April"), may = new Month(5, 0, "May"), jun = new Month(6, 0, "June"), jul = new Month(7, 0, "July"), aug = new Month(8, 0, "August"), sep = new Month(9, 0, "September"), oct = new Month(10, 0, "October"), nov = new Month(11, 0, "November"), dec = new Month(12, 0, "December");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Month> monthArray = new ArrayList<>();
        Month[] months = {jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec};
        monthArray.addAll(Arrays.asList(months));
        for (int i = 0; i < lendzDataItem.size(); i++) {
            try {
                Date date = sdf.parse(lendzDataItem.get(i).getStartDate());
                Calendar current = Calendar.getInstance();
                current.setTime(date);
                int cMonth = current.get(Calendar.MONTH);
                for (int j = 0; j < monthArray.size(); j++) {
                    if (cMonth == j) {
                        monthArray.get(j).count++;
                    }
                }
            } catch (Exception e) {
                Log.d("Exception", e + "");
            }
        }

        Collections.sort(monthArray, new Comparator<HomeStatisticsFragment.Month>() {
            @Override
            public int compare(HomeStatisticsFragment.Month o1, HomeStatisticsFragment.Month o2) {
                return o1.getCount() - o2.getCount();
            }
        });
        if(lendzDataItem.size() <= 0){
            return "Not lent yet";
        }else{
            return monthArray.get(monthArray.size() - 1).name;
        }
    }

    private String countTotalRevenue() {
        Double total;
        DecimalFormat df = new DecimalFormat("#.00");
        total = productDataItem.getPrice() * noOfDays * 0.95;
        return total == 0 ? "0" : df.format(total);
    }

    private class Month {
        Integer monthIndex, count;
        String name;

        public Month(Integer monthIndex, Integer count, String name) {
            this.monthIndex = monthIndex;
            this.count = count;
            this.name = name;
        }

        Integer getCount() {
            return this.count;
        }
    }
}
