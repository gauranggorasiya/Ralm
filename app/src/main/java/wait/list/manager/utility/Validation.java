package wait.list.manager.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import wait.list.manager.activity.FilterActivity;


public class Validation {

    public static boolean chkRequired(Activity context, EditText editText, View containerView, String fieldName, String requiredValidationMessage, String lengthValidationMessage, int minLen, int maxLen) {
        String text = editText.getText().toString().trim();
        if (text.length() < 1) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, fieldName + " " + requiredValidationMessage);
            editText.requestFocus();
            return false;
        }
        if(!Pattern.matches("[a-z,A-Z\\s]+",text)){
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, fieldName + " " + "is not Valid");
            editText.requestFocus();
            return false;
        }
        if (text.length() < minLen || text.length() > maxLen) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, fieldName + String.format(lengthValidationMessage, minLen, maxLen));
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidMobile(Activity context, EditText editText, View containerView, String fieldName, String requiredValidationMessage, String lengthValidationMessage, int minLen, int maxLen) {
        String phone = editText.getText().toString().trim();
        if (phone.length() < 1) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, fieldName + " " + requiredValidationMessage);
            editText.requestFocus();
            return false;
        }

      //  if(!Pattern.matches("[6-9][0-9]+", phone)) {
        if (phone.length() < minLen || phone.length() > maxLen || !Pattern.matches("[6-9][0-9]+", phone)) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, String.format(lengthValidationMessage, minLen, maxLen));
            editText.requestFocus();
            return false;
        } else {
            return true;
        }

    }

/*    public static boolean isValidMobile(Activity context, EditText editText, String s, String phone, String Error) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 10 || phone.length() > 14) {
//            if(phone.length() != 10) {
                check = false;
                Utility.showSingleButtonPopupWindow(context, Error);
                editText.requestFocus();
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }*/

    public static boolean isValidNumber(Activity context, EditText editText, View containerView, String fieldName, String requiredValidationMessage, String lengthValidationMessage, int minLen, int maxLen) {
        String number = editText.getText().toString().trim();
        if (number.length() < 1) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, fieldName + " " + requiredValidationMessage);
            editText.requestFocus();
            return false;
        }
        int person = 200;
        int result = (int) Integer.parseInt(String.valueOf(number));
        int finalresult = Math.max(result,person);
        if (finalresult > 200) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, "Please Enter " + fieldName + " " + String.format(lengthValidationMessage, maxLen));
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }


/*    public static boolean isValidNumber(Activity context, EditText idEtGST, String numofperson, String Error) {
        boolean check=false;
        int dummy = 200;
        String number = idEtGST.getText().toString();
        int result = (int) Integer.parseInt(String.valueOf(number));
        int fresult = Math.max(result,dummy);

        Log.d("hjghj", String.valueOf(fresult));
        if(fresult > 200 ) {

               // check = false;
                Utility.showSingleButtonPopupWindow(context, Error);
            idEtGST.requestFocus();
            } else {
                check = true;
            }
        return check;
    }*/

    public static boolean isTableNumber(Activity context, EditText allocatetable, View containerView, String fieldName, String requiredValidationMessage, String lengthValidationMessage, int minLen, int maxLen) {

        Log.d("Allocate Table Edittext",allocatetable.getText().toString().trim());
        String allocate = allocatetable.getText().toString().trim();
        if (allocate.length() < 1) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, fieldName + " " + requiredValidationMessage);
            allocatetable.requestFocus();
            return false;
        }
        int table = 15;
        int result = (int) Integer.parseInt(String.valueOf(allocate));
        int finalresult = Math.max(result,table);
        if (finalresult > 15) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, "Please Enter " + fieldName + " " + String.format(lengthValidationMessage, maxLen));
            allocatetable.requestFocus();
            return false;
        } else {
            return true;
        }
    }


    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

    public static boolean compareFields(Activity context, EditText editText1, EditText editText2, String errorMessage) {

        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();
        if (text1.equals(text2)) {
            return true;
        } else {
            Utility.showSingleButtonPopupWindow(context, errorMessage);
            editText2.requestFocus();
            return false;
        }
    }

    public static boolean chkEmailAddress(Activity context, EditText editText, View containerView, String errorMessage, String lengthValidationMessage, String fieldName, int minLen, int maxLen) {
        String emaiText = editText.getText().toString().trim();

        if (emaiText.length() < 1) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, errorMessage);
            editText.requestFocus();
            return false;
        } else if (emaiText.length() < minLen || emaiText.length() > maxLen) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, fieldName + String.format(lengthValidationMessage, minLen, maxLen));
            editText.requestFocus();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emaiText).matches()) {
            containerView.requestFocus();
            Utility.showSingleButtonPopupWindow(context, errorMessage);
            editText.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    public static boolean isThisDateValid(Activity context, EditText editText, View containerView, String dateFormat1, String dateFormatError) {
        String text = editText.getText().toString().trim();
        if (checkDateFormat(dateFormat1, text))
            return true;
        else {
            Utility.showSingleButtonPopupWindow(context, dateFormatError);
            containerView.requestFocus();
            return false;
        }

    }

/*    public static boolean isthisDateValid(FilterActivity filterActivity, EditText editText, View containerView, String ddmmyyyy, String string) {
        String text = editText.getText().toString().trim();
        String text2 = editText.getText().toString().trim();
        if (checkDateFormat(ddmmyyyy, text) < checkDateFormat(ddmmyyyy, text2))
            return true;
        else {
            Utility.showSingleButtonPopupWindow(filterActivity, string);
            containerView.requestFocus();
            return false;
        }

    }*/

    public static boolean isThisStartDateValid(FilterActivity filterActivity, EditText idEtStartDate, View containerView, String ddmmyyyy, String string) {
        String text = idEtStartDate.getText().toString().trim();
        if (checkDateFormat(ddmmyyyy, text))
            return true;
        else {
            Utility.showSingleButtonPopupWindow(filterActivity, string);
            containerView.requestFocus();
            return false;
        }
    }

    public static boolean isThisEndDateValid(FilterActivity filterActivity, EditText idEtEndDate, View containerView, String ddmmyyyy, String string) {
        String text = idEtEndDate.getText().toString().trim();
        if (checkDateFormat(ddmmyyyy, text))
            return true;
        else {
            Utility.showSingleButtonPopupWindow(filterActivity, string);
            containerView.requestFocus();
            return false;
        }
    }

    private static boolean checkDateFormat(String dateFormat, String dateToCheck) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setLenient(false);
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToCheck);
            System.out.println(date);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
