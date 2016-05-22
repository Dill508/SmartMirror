package djohnson.smartmirror.modules;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Dillon on 5/21/2016.
 *
 */
public class MapsBackground {

    public void getMapBackground(String API_KEY, String lat, String lon, final ImageView radarImage) {

        new DownloadImageTask(radarImage)
                .execute("https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=8&scale=2&size=600x500&maptype=roadmap\n" +
                        "&key=" + API_KEY);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
