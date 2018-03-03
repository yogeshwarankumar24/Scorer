package com.scorer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.scorer.Activity.HomeActivity;
import com.scorer.ModalClass.Emailsender;
import com.scorer.ModalClass.MailModel;

/**
 * Created by Android on 11/17/2017.
 */

    public class SendMail extends AsyncTask<MailModel, Void,String> {

    private Exception exception;

    protected String doInBackground(MailModel... urls) {
        try {
            MailModel objMailmodel = new MailModel();
            objMailmodel = urls[0];
            Emailsender objemail = new Emailsender();
//            String[] toArr = {"yogeshwarankumar24@gmail.com"};
            String[] toArr = objMailmodel.getSendmailto().toArray(new String[objMailmodel.getSendmailto().size()]);
            objemail.setTo(toArr); // load array to setTo function
            objemail.setFrom("yogeshapptest@gmail.com"); // who is sending the email
            objemail.setSubject(objMailmodel.getMailSubject());
            objemail.setBody(objMailmodel.getMailBody());

            try {
                if (objMailmodel.getMailAttachment() != null && objMailmodel.getMailAttachment().length() > 1) {
                    objemail.addAttachment(objMailmodel.getMailAttachment());  // path to file you want to attach
                }
                if (objemail.send()) {
                    Log.e("SendMail:","Email was sent successfully.");
                    // success
//                    Toast.makeText(HomeActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("SendMail:","Email was not sent.");
                    // failure
//                    Toast.makeText(HomeActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("SendMail:","There was a problem sending the email.");
                // some other problem
//                Toast.makeText(HomeActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            }

            return "";
        } catch (Exception e) {
            this.exception = e;
            Log.e("SendMail:","There was a problem sending the email.");
            return "";
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}