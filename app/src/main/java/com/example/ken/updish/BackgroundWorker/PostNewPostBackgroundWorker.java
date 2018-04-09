package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Fragment.PostFragment;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.ConnectionHelper;
import com.example.ken.updish.Utility.SharedResources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tanthinh on 4/5/18.
 */

public class PostNewPostBackgroundWorker extends AsyncTask<JSONObject, Void, String> {

    Activity context;
    ProgressDialog loadingDialog;

    public PostNewPostBackgroundWorker(Activity con)
    {
        this.context = con;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(
                context, "Uploading your dish", "Please wait....",true, false);
    }

    @Override
    protected String doInBackground(JSONObject... param) {

        JSONObject objSend = param[0];

        try
        {
            // Connection
            String urlWithId = SharedResources.getInstance().getStringValue(this.context,"postnew");
            ConnectionHelper connection = new ConnectionHelper(context,urlWithId,"POST");

            String post_data =
                    URLEncoder.encode("data", "UTF-8" ) + "=" +
                            URLEncoder.encode(objSend.toString(), "UTF-8" );
//            Log.e("Objsending", objSend.toString(), null);

            String result = connection.connect(post_data, ConnectionHelper.SEND_REQUEST_WITH_PARAMETERS);
            return result;
        }catch(UnsupportedEncodingException uee)
        {
            uee.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        loadingDialog.dismiss(); //Dismiss dialog

        try
        {
            JSONObject jsonobj = new JSONObject(result);
            String success = jsonobj.getString("success");

            if(success.equals("true"))
            {
                MainActivity mainActivity = (MainActivity)context;

                //Reset the Pictures
                mainActivity.getPostFragment().getBitmapArrayGrid().clear();
                mainActivity.getPostFragment().getBitmapArrayNormal().clear();

                //Reset ShareResources
                SharedResources sr = SharedResources.getInstance();
                sr.addStringValue(context, "GoogleMapName", "N/A");
                sr.addStringValue(context, "GoogleMapLocation", "N/A");
                sr.addStringValue(context,"selectedLong","N/A");
                sr.addStringValue(context,"selectedLat","N/A");

                PostFragment pf = new PostFragment();
                mainActivity.getBottomNavigationView().setSelectedItemId(R.id.navigation_main);
//                mainActivity.getPostFragment();
            }else
            {
                Toast.makeText(context, "Cannot post a new dish. Please try again later", Toast.LENGTH_LONG);
            }
        }catch(JSONException jsonex)
        {
            Log.e("Ex json postex pnpost", jsonex.getMessage(), null);
        }

    }

}