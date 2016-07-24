package com.dikshay.mobilecomputing.assignments.ifthisthenthat;

import android.os.AsyncTask;

/**
 * Created by User847 on 4/24/2016.
 */
abstract class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
/*
        Mail m = new Mail("from@gmail.com", "my password");

public SendEmailAsyncTask() {
        if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "SendEmailAsyncTask()");
        String[] toArr = { "to mail@gmail.com"};
        m.setTo(toArr);
        m.setFrom("from mail@gmail.com");
        m.setSubject("Email from Android");
        m.setBody("body.");
        }

@Override
protected Boolean doInBackground(Void... params) {
        if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
        try {
        m.send();
        return true;
        } catch (AuthenticationFailedException e) {
        Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
        e.printStackTrace();
        return false;
        } catch (MessagingException e) {
        Log.e(SendEmailAsyncTask.class.getName(), m.getTo(null) + "failed");
        e.printStackTrace();
        return false;
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
*/
        }
