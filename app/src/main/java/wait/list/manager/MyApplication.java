package wait.list.manager;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import wait.list.manager.utility.AppConstant;


public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();

    private static MyApplication mInstance;

    private static Typeface typefaceFA;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        typefaceFA=Typeface.createFromAsset( getAssets(), "fa.ttf" );

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name(AppConstant.DATABASE_NAME).deleteRealmIfMigrationNeeded() .build();
        Realm.setDefaultConfiguration(config);
    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public static synchronized Typeface getTypefaceFa() {
        return typefaceFA;
    }
    public static void markAsIconContainer(View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child,typeface);
            }
        } else if (v instanceof TextView) {
            TextView textView=((TextView) v);
            Log.e("MyApplication",textView.getText().toString() );
            textView.setTypeface(typeface);
        }
    }


}
