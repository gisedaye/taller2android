package com.taller2.matchapp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by fedefarina on 05/06/16.
 */
public class ActivityIndicator {

    private ProgressDialog progressDialog;
    private boolean cancelable = false;
    private String temporalMessage;


    public ActivityIndicator(Context context) {
        progressDialog = new ProgressDialog(context);
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public void show() {
        if (progressDialog.isShowing())
            return;

        if (temporalMessage == null) {
            progressDialog.setMessage("Loading, please wait...");
        } else {
            progressDialog.setMessage(temporalMessage);
        }
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(cancelable);

        progressDialog.show();
    }

    public boolean isShowing() {
        return progressDialog.isShowing();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        progressDialog.setOnDismissListener(onDismissListener);
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        progressDialog.setOnCancelListener(onCancelListener);
    }

    public void hide() {
        temporalMessage = null;
        progressDialog.dismiss();
    }

    public void setTemporalMessage(String temporalMessage) {
        this.temporalMessage = temporalMessage;
    }

    public String getTemporalMessage() {
        return temporalMessage;
    }
}