package djohnson.smartmirror.modules;

import android.text.Html;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Dillon on 5/14/2016.
 *
 */
public class Date {
    public static String getDate() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.US);
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.US);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
        Calendar now = Calendar.getInstance();
        //int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        String date = monthFormat.format(now.getTime()) + " " + dayFormat.format(now.getTime()) + ", " + yearFormat.format(now.getTime());
        return date;
        //return Html.fromHtml(monthFormat.format(now.getTime()) + " " + dayFormat.format(now.getTime()) + ", " + yearFormat.format(now.getTime()));
    }
}
