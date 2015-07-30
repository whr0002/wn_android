package com.map.woodlands.woodlandsmap.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by Jimmy on 7/30/2015.
 */
public class AsyncTransition extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Activity mContext;
    private Intent mIntent;
    public AsyncTransition(Activity context, Intent intent){
        mContext = context;
        mIntent = intent;
    }



    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(mContext, "", "Loading ...", true);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mContext.startActivityForResult(mIntent, 0);
    }

    @Override
    protected Void doInBackground(Void... params) {

        progressDialog.dismiss();
        return null;
    }
}
