package wait.list.manager.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wait.list.manager.R;

import wait.list.manager.activity.ManageCustomerActivity;
import wait.list.manager.adapter.CustomerAdapter;
import wait.list.manager.evenhandler.OkayButtonClickListener;
import wait.list.manager.evenhandler.YesAndNoButtonClickHandler;
import wait.list.manager.model.Customer;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class Utility {


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Activity activity, View v) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideKeyboard(Context context, View v) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showSingleButtonPopupWindow(Context activity, String message) {
        try {
            final Dialog dialogNetWorkError = new Dialog(activity, R.style.CustomDialogTheme);
            dialogNetWorkError.setContentView(R.layout.single_button_dialog);
            TextView txtmsg = (TextView) dialogNetWorkError.findViewById(R.id.idTvMessage);
            txtmsg.setText(message);
            TextView btnOk = (TextView) dialogNetWorkError.findViewById(R.id.idBtnDismiss);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogNetWorkError.dismiss();
                }
            });
            dialogNetWorkError.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void showSingleButtonAllocatePopupWindow(Context activity, String message, final OkayButtonClickListener yesAndNoButtonClickHandler) {
        try {
            final Dialog dialogInterface = new Dialog(activity, R.style.CustomDialogTheme);
            dialogInterface.setContentView(R.layout.allocate_button_dialog);
            TextView btnok = (TextView) dialogInterface.findViewById(R.id.idBtnOk);
            EditText editText = (EditText) dialogInterface.findViewById(R.id.idEtAllocate);
            btnok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    yesAndNoButtonClickHandler.onButtonYesClicked(editText.getText().toString());
                    dialogInterface.dismiss();
                }

            });
            TextView btncancel = (TextView) dialogInterface.findViewById(R.id.idBtnCancel);
            btncancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    yesAndNoButtonClickHandler.onButtonNoClicked();
                    dialogInterface.dismiss();
                }
            });
            dialogInterface.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showSingleButtonPopupWindowHtml(Context activity, String message) {
        try {
            final Dialog dialogNetWorkError = new Dialog(activity, R.style.CustomDialogTheme);
            dialogNetWorkError.setContentView(R.layout.single_button_dialog);
            TextView txtmsg = (TextView) dialogNetWorkError.findViewById(R.id.idTvMessage);
            txtmsg.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
            txtmsg.setText(Html.fromHtml(message));

            TextView btnOk = (TextView) dialogNetWorkError.findViewById(R.id.idBtnDismiss);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogNetWorkError.dismiss();
                }
            });
            dialogNetWorkError.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void rvereseArray(Integer arr[], int start, int end) {
        int temp;
        if (start >= end)
            return;
        temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
        rvereseArray(arr, start + 1, end - 1);
    }

    public static void showDoubleButtonPopupWindow(Activity activity, String message) {
        try {
            final Dialog dialogNetWorkError = new Dialog(activity, R.style.CustomDialogTheme);

            dialogNetWorkError.setContentView(R.layout.double_button_dialog);
            TextView txtmsg = (TextView) dialogNetWorkError.findViewById(R.id.idTvMessage);
            txtmsg.setText(message);

            TextView btnOk = (TextView) dialogNetWorkError.findViewById(R.id.idBtnYes);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogNetWorkError.dismiss();
                }
            });
            TextView btnCancel = (TextView) dialogNetWorkError.findViewById(R.id.idBtnNo);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogNetWorkError.dismiss();
                }
            });
            dialogNetWorkError.show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void showDoubleButtonPopupWindow(Activity activity, String message, final YesAndNoButtonClickHandler yesAndNoButtonClickHandler) {
        try {
            final Dialog dialogNetWorkError = new Dialog(activity, R.style.CustomDialogTheme);

            dialogNetWorkError.setContentView(R.layout.double_button_dialog);
            TextView txtmsg = (TextView) dialogNetWorkError.findViewById(R.id.idTvMessage);
            txtmsg.setText(message);

            TextView btnOk = (TextView) dialogNetWorkError.findViewById(R.id.idBtnYes);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yesAndNoButtonClickHandler.onButtonYesClicked();
                    dialogNetWorkError.dismiss();
                }
            });
            TextView btnCancel = (TextView) dialogNetWorkError.findViewById(R.id.idBtnNo);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yesAndNoButtonClickHandler.onButtonNoClicked();
                    dialogNetWorkError.dismiss();
                }
            });
            dialogNetWorkError.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String TAG = Utility.class.getSimpleName();



    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }



    public static Address getLocationFromAddress(Location location, Activity activity) {
        Geocoder coder = new Geocoder(activity);
        List<Address> addresses;
        try {
            addresses = coder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            if (addresses == null || addresses.size() <= 0) {
                return null;
            }
            Address address = addresses.get(0);
            return address;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static long checkDayDistanceBetween2Date(Date startDate) {
        Date endDate = Calendar.getInstance().getTime();

        long duration = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toDays(duration);
    }

    public static String formatDate(String date, String initDateFormat, String endDateFormat) throws ParseException {
        if (date == null)
            return null;
        Date initDate = new SimpleDateFormat(initDateFormat, Locale.getDefault()).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat, Locale.getDefault());
        String parsedDate = formatter.format(initDate);
        return parsedDate;
    }

    public static String formatDate(Date date, String endDateFormat) {
        if (date == null)
            return "no date";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat, Locale.getDefault());
            String parsedDate = formatter.format(date);
            return parsedDate;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public static long convertDateToUtc(String date, String fromFormat, String deviceTimeZone) {
        long time = 0;
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(fromFormat);
            SimpleDateFormat outFormatter = new SimpleDateFormat(fromFormat);
            outFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsed = sourceFormat.parse(date);
            float dstTime = calculateTimeZone(deviceTimeZone);
            return (long) (parsed.getTime() + dstTime);

        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }

    }

    public static long getUTCTime(int hours, int minutes, String deviceTimeZone) {
        long time = 0;
        //try {
        //deviceTimeZone = "America/New_York";
        long totalSeconds = (hours * 3600000) + (minutes * 60 * 1000);
        long deviceOffset = TimeZone.getTimeZone(deviceTimeZone).getOffset(totalSeconds);
        long dstOffset = (long) Utility.calculateTimeZone(deviceTimeZone);

        if (deviceOffset < 0) {
            deviceOffset = -(Math.abs(deviceOffset) + dstOffset);
        }

        if (deviceOffset > 0) {
            deviceOffset = (Math.abs(deviceOffset) + dstOffset);
        }

        long seconds = (totalSeconds - (deviceOffset));
        //Log.v(TAG, "Total seconds:" + totalSeconds + " mobileOffset:" + mobileOffset + " DeviceOffset:" + deviceOffset + " Seconds:" + seconds);
        return seconds;
        /*} catch (Exception e) {
            Log.i(TAG, "getUTCTime: ===="+e.toString());
            return time;
        }*/

    }

    public static long localToGMT(String date, String fromFormat, String deviceTimeZone) {
        long time = 0;
        try {
            // Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date gmt = sdf.parse(date);
            float dstTime = calculateTimeZone(deviceTimeZone);
            return (long) (gmt.getTime() + dstTime);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }

    }

    public static long localToGMT(int hours, int minutes, String deviceTimeZone) {
        long time = 0;
        try {
            // Date date = new Date();
            long totalSeconds = (hours * 3600000) + (minutes * 60 * 1000);
            float dstTime = calculateTimeZone(deviceTimeZone);
            return (long) (totalSeconds + dstTime);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }

    }

    public static float calculateTimeZone(String deviceTimeZone) {
        float ONE_HOUR_MILLIS = 60 * 60 * 1000;

        // Current timezone and date
        TimeZone timeZone = TimeZone.getTimeZone(deviceTimeZone);
        Date nowDate = new Date();
        float offsetFromUtc = timeZone.getOffset(nowDate.getTime()) / ONE_HOUR_MILLIS;

        // Daylight Saving time
        if (timeZone.useDaylightTime()) {
            // DST is used
            // I'm saving this is preferences for later use

            // save the offset value to use it later
            float dstOffset = timeZone.getDSTSavings() / ONE_HOUR_MILLIS;
            // DstOffsetValue = dstOffset
            // I'm saving this is preferences for later use
            // save that now we are in DST mode
            if (timeZone.inDaylightTime(nowDate)) {
                Log.e(Utility.class.getName(), "in Daylight Time");
                return -(ONE_HOUR_MILLIS * dstOffset);
            } else {
                Log.e(Utility.class.getName(), "not in Daylight Time");
                return 0;
            }
        } else
            return 0;
    }

    public static String convertUtcToLocal(String datetime, String format) {

        try {
            String dateStr = datetime;
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(dateStr);
            df.setTimeZone(TimeZone.getDefault());
            String formattedDate = df.format(date);

            return formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0000-00-00 00:00:00";
    }



    public static String appVersion(Context context) {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int appVersionCode(Context context) {
        int version = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    public static final String dateddMMyyyyFormat="dd/MM/yyyy";

    public static void hideKeyboard(View view, Activity activity) {
        InputMethodManager inputMethodManager =(InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static String getDateInFormat(Date date, String format){
        try{
            SimpleDateFormat dateFormator = new SimpleDateFormat(format);
            return dateFormator.format(date);

        }catch (Exception e){
            e.printStackTrace();
            return date.toString();
        }
    }

    public static void changeTextViewText(TextView textView, String s){
        textView.setText(s);
    }

    public static String getDueDate(Date myDate, String formate, String days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, +Integer.parseInt(days));
        Date newDate = calendar.getTime();
        return getDateInFormat(newDate,formate);
    }

    public static String getStartDate(Date myDate, String formate, String days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, -Integer.parseInt(days));
        Date newDate = calendar.getTime();
        return getDateInFormat(newDate,formate);
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}

