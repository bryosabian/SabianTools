package com.sabiantools.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sabiantools.R;
import com.sabiantools.modals.SabianModal;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.Jsoup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * Created by Brian Sabana on 12/01/2017.
 */


public class SabianUtilities {
    private static Context context;
    private static Activity mActivity;
    public final static String DEFAULT_DATE_FORMAT = "%1$tY-%1$tm-%1$tdT%1$tH:%1$tM:%1$tS";
    private static ProgressDialog pd;
    private static boolean isLogActive = false;

    public static void setIsLogActive(boolean isLogActive) {
        SabianUtilities.isLogActive = isLogActive;
    }

    public static void SetContext(Context context) {
        SabianUtilities.context = context;
    }

    public static void DisplayMessage(String message) {
        DisplayMessage(message, TYPE_MESSAGE.ERROR);
    }


    public static void DisplayMessage(String message, TYPE_MESSAGE type_message) {
        SabianToast toast = new SabianToast(context);
        toast.setText(message);
        toast.setIcon(type_message.getIcon());
        toast.setCustomType(type_message.getDrawableResource());
        toast.display(Toast.LENGTH_SHORT);
    }

    public static void DisplayMessage(Context context, String message, TYPE_MESSAGE type_message) {
        SabianToast toast = new SabianToast(context);
        toast.setText(message);
        toast.setIcon(type_message.getIcon());
        toast.setCustomType(type_message.getDrawableResource());
        toast.display(Toast.LENGTH_SHORT);
    }


    public static void DisplayModal(Context context, String title, String message) {
        DisplayModal(context, title, message, "Okay", null, null, null);
    }

    public static void DisplayModal(Context context, String title, String message, final View.OnClickListener onOkayClick, final View.OnClickListener onCancelClick) {
        final SabianModal modal = new SabianModal(context);
        modal.setTitle(title).setMessage(message);
        modal.setOkayButtonText("Okay");
        modal.setOnOkayClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modal.dismiss();
                if (onOkayClick != null)
                    onOkayClick.onClick(view);
            }
        });

        if (onCancelClick != null) {
            modal.setCancelButtonText("Cancel");
            modal.setOnCancelClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modal.dismiss();
                    onCancelClick.onClick(view);
                }
            });
        }
        modal.show();
    }

    public static void DisplayModal(Context context, String title, String message, String okText, String cancelText, final View.OnClickListener onOkayClick, final View.OnClickListener onCancelClick) {
        final SabianModal modal = new SabianModal(context);
        modal.setTitle(title).setMessage(message);
        modal.setOkayButtonText(okText);
        modal.setOnOkayClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modal.dismiss();
                if (onOkayClick != null)
                    onOkayClick.onClick(view);
            }
        });

        if (!(SabianUtilities.IsStringEmpty(cancelText) && onCancelClick == null)) {
            modal.setCancelButtonText(cancelText);
        }
        if (onCancelClick != null)
            modal.setOnCancelClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modal.dismiss();
                    onCancelClick.onClick(view);
                }
            });
        modal.show();
    }

    public static void WriteLog(String message) {
        if (isLogActive)
            Log.e("SABLog_" + GetCurrentTimestamp(), new Date().toString() + " : " + message);
    }

    public static String GetCurrentDateFormat() {
        Date date = new Date();
        String format = String.format("%te %tB %tY%s%tT%s", date, date, date, "T", date, "Z");
        return format;
    }

    public static String EscapeHtml(String string) {

        if (string == null || string.isEmpty())
            return string;

        Pattern tagPattern = Pattern.compile("<.+?>");

        Matcher mRemover = tagPattern.matcher(string);

        return mRemover.replaceAll("").trim();
    }

    public static String ParseHtml(String html) {

        return Jsoup.parse(html).text();
    }

    /**
     * Checks whether network is active that is wifi and or mobile data is on
     * Requires android.permission.ACCESS_NETWORK_STATE
     *
     * @return
     */
    public static boolean IsNetworkOn(Context context) {
        NetworkInfo generalNetworkInfo;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        generalNetworkInfo = manager.getActiveNetworkInfo();
        if (generalNetworkInfo != null && generalNetworkInfo.isConnected())
            return true;
        return false;
    }

    /**
     * Checks whether mobile network is on
     *
     * @return
     */
    public static boolean IsMobileNetworkOn(Context context) {
        NetworkInfo info;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return info.isConnected();
    }

    /**
     * Checks whether wifi is on
     *
     * @return
     */
    public static boolean IsWifiNetworkOn(Context context) {
        NetworkInfo info;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info.isConnected();
    }

    public static String GetEllipsis(String text, int length) {
        if (text.length() <= length)
            return text;

        return text.substring(0, length) + "...";
    }

    public static boolean IsStringEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static long GetCurrentTimestamp() {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());

        long mills = c.getTimeInMillis();

        return mills;
    }

    public static boolean IsJson(String json) {
        JsonParser parser = new JsonParser();
        try {
            parser.parse(json);

            return true;
        } catch (JsonSyntaxException ex) {
            SabianUtilities.WriteLog("JSON Validate Syntax Error " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            SabianUtilities.WriteLog("JSON Validate Error " + ex.getMessage());
            return false;
        }

    }

    /**
     * Gets distance between two geo coodinates in KM
     *
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return distance in KM
     */
    public static double getDistanceBetween(double latitude1, double longitude1, double latitude2, double longitude2) {

        /**
         * Distance of the earth
         */
        final int R = 6371;
        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    /**
     * Gets distance between two geo coodinates in Meters
     *
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return distance in Meters
     */
    public static double getDistanceBetweenInMeters(double latitude1, double longitude1, double latitude2, double longitude2) {
        double distance = getDistanceBetween(latitude1, longitude1, latitude2, longitude2);
        return distance * 1000;
    }

    public static Gson GetStandardGson() {
        return GetGson(Modifier.PRIVATE, Modifier.TRANSIENT);
    }

    public static Gson GetGson(int... excludeModifiers) {
        GsonBuilder builder = new GsonBuilder();
        //builder.setLenient();
        builder.excludeFieldsWithModifiers(excludeModifiers);
        return builder.create();
    }

    public static GsonBuilder getStandardGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.PRIVATE, Modifier.TRANSIENT);
        return builder;
    }

    public static String GetFormattedDateString(Date date) {
        String format = String.format(SabianUtilities.DEFAULT_DATE_FORMAT, date);
        return format;
    }

    public static Date GetFormattedDate(String dateString) {
        DateTime dt = DateTime.parse(dateString);
        return dt.toDate();
    }

    public static boolean setAutomaticHeightList(AbsListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;

            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            int totalDividersHeight = 0;

            if (listView.getClass() == GridView.class) {
                GridView gridView = (GridView) listView;

                totalItemsHeight = totalItemsHeight / 2;
            }

            // Get total height of all item dividers.
            if (listView.getClass() == ListView.class) {
                ListView lsView = (ListView) listView;
                totalDividersHeight = lsView.getDividerHeight() *
                        (numberOfItems - 1);

                //Add list vew offset
                totalItemsHeight += context.getResources().getDimensionPixelSize(R.dimen.automatic_list_offset);
            }

            float scale = context.getResources().getDisplayMetrics().density;

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();

            int height = totalItemsHeight + totalDividersHeight;


            int scaled_margin = (int) (scale * height + 0.5f);

            params.height = height;

            listView.setLayoutParams(params);

            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public static float dipToPixels(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public static float spToPixels(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    public static EmailValidator getEmailValidator() {
        return new EmailValidator();
    }

    public static Context getContext() {
        return context;
    }

    public static String noWhiteSpace(String text) {
        return text.trim().replaceAll("\\s+", "");
    }

    public enum TYPE_MESSAGE {
        ERROR("fa-exclamation-circle", R.drawable.sabian_toast_layout_danger),
        SUCCESS("fa-check-circle", R.drawable.sabian_toast_layout_success),
        INFORMATION("fa-info-circle", R.drawable.sabian_toast_layout_primary);

        private String icon;

        private @DrawableRes
        int drawableResource;

        TYPE_MESSAGE(String icon, @DrawableRes int drawableResource) {
            this.icon = icon;
            this.drawableResource = drawableResource;
        }

        public String getIcon() {
            return icon;
        }

        public int getDrawableResource() {
            return drawableResource;
        }
    }

    public static byte[] getBytesFromUri(Uri uri) {
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            byte[] inputData = getBytesFromStream(iStream);
            return inputData;
        } catch (FileNotFoundException ex) {
            WriteLog("Could not read input uri. " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } catch (IOException e) {
            WriteLog("Could not read input uri. " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getBytesFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static byte[] getImageBytesFromUri(Context context, Uri uri, boolean compress, int compressQuality) throws FileNotFoundException {
        InputStream instream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        final Bitmap bm = BitmapFactory.decodeStream(instream);
        bm.compress(Bitmap.CompressFormat.JPEG, compressQuality, ostream);
        byte[] b = ostream.toByteArray();
        return b;
    }

    public static Bitmap getRoundBitmapImage(Uri uri) {
        try {
            byte[] myByteArray = getBytesFromUri(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap source = BitmapFactory.decodeByteArray(
                    myByteArray, 0,
                    myByteArray.length);

            //Bitmap scaled = Bitmap.createScaledBitmap(b, 256, 256, true);
            //photoholder.setImageBitmap(scaled);

            //Bitmap source = b;
            int size = Math.min(source.getWidth(), source.getHeight()); //Gets the radius
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squareBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squareBitmap != source) {
                source.recycle();
            }
            Bitmap circleBitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(circleBitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squareBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float radius = size / 2f;
            canvas.drawCircle(radius, radius, radius, paint);
            squareBitmap.recycle();

            return circleBitmap;
        } catch (Exception e) {
            WriteLog("Could not round image " + e.getMessage());
            return null;
        }
    }

    public static Bitmap getUriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getImageToBase64(Bitmap bm, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        byte[] b = stream.toByteArray();
        String encoded = Base64.encodeToString(b, Base64.DEFAULT);
        return encoded;
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }

    public static void showLoadingDialog(String title) {
        hideLoadingDialog();
        if (mActivity != null) {
            pd = new ProgressDialog(mActivity, title);
        } else {
            pd = new ProgressDialog(context, title);
        }
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();
    }

    public static void showLoadingDialog(String title, String message) {
        hideLoadingDialog();
        try {
            if (mActivity != null) {
                pd = new ProgressDialog(mActivity, title + ". " + message);
            } else {
                pd = new ProgressDialog(context, title + ". " + message);
            }
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(false);
            pd.show();
        } catch (Exception e) {
            SabianUtilities.WriteLog("Progress dialog error " + e.getMessage());
        }
    }

    public static void hideLoadingDialog() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
                pd = null;
                SabianUtilities.WriteLog("Progress bar hidden bro");
            }
        } catch (Exception e) {
            SabianUtilities.WriteLog("Progress dialog error " + e.getMessage());
        }
    }

    public static DateTime getLocalDateTimeNow(Context context) {
        return LocalDateTime.now().toDateTime();
    }

    public static String getDateTimeNowString(Context context, @Nullable String format) {
        if (format == null)
            format = "yyyy-MM-dd HH:mm:s";
        return getLocalDateTimeNow(context).toString(format);
    }

    public static void updateLoadingDialog(@Nullable String title, String message) {
        if (pd == null) {
            showLoadingDialog(title, message);
            return;
        }
        //pd.setMessage(message);
        if (title != null)
            pd.setTitle(title + "." + message);
    }

    public static class EmailValidator {

        private Pattern pattern;

        private Matcher matcher;

        private static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        public EmailValidator() {
            pattern = Pattern.compile(EMAIL_PATTERN);
        }

        /**
         * Validate hex with regular expression
         *
         * @param hex hex for validation
         * @return true valid hex, false invalid hex
         */
        public boolean validate(final String hex) {

            matcher = pattern.matcher(hex);

            return matcher.matches();

        }
    }

    public static void setActivity(Activity mActivity) {
        SabianUtilities.mActivity = mActivity;
        context = mActivity;
    }

    public static double roundOf(double number, int roundToDecimalPlaces, boolean up) {
        BigDecimal bd = new BigDecimal(number).setScale(roundToDecimalPlaces, (up) ? RoundingMode.UP : RoundingMode.DOWN);
        return bd.doubleValue();

    }

    public static BigDecimal roundOf(BigDecimal number, int roundToDecimalPlaces, boolean up) {
        BigDecimal bd = number.setScale(roundToDecimalPlaces, (up) ? RoundingMode.UP : RoundingMode.DOWN);
        return bd;

    }

    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public static void clearFile(File file) throws FileNotFoundException {
        if (file.exists()) {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        }
    }

    public static String getCurrentIsoDateString() {
        return LocalDateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public static String getCurrentDateString() {
        return DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
