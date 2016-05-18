package djohnson.smartmirror.modules;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Dillon on 5/17/2016.
 *
 */
public class WeatherRadar {

    public void getWeatherRadar(ImageView weatherRadar) {

        new DownloadImageTask(weatherRadar)
                .execute("http://maps.aerisapi.com/cRwmrNPqZ5AaW2bOC2S5h_eoDHeXKbIVHvqXGX03BldMjXq3IYtgPODY5oRYQo/flat,radar,admin/600x400/41.533,-90.655,8/current.jpg");
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
