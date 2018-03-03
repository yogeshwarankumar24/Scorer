package com.scorer;

import android.graphics.drawable.Drawable;
import android.util.AndroidException;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by impowersolutionspvtltd on 30/05/17.
 */

public class Validation {
    
    public static boolean Name(EditText objName)
    {
        if (objName.getText().toString() == "")
        {
            objName.setError("Enter Name");
            objName.requestFocus();
            return false;
        }
        if (objName.getText().length() <= 2)
        {
            objName.setError("Enter Valid Name");
            objName.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean Text(EditText objName, String Value)
    {
        if (objName.getText().toString() == "")
        {
            objName.setError(Value);
            objName.requestFocus();
            return false;
        }
        if (objName.getText().length() <= 1)
        {
            objName.setError(Value);
            objName.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean Number(EditText objName, String Value)
    {
        if (objName.getText().toString() == "")
        {
            objName.setError(Value);
            objName.requestFocus();
            return false;
        }
        if (objName.getText().length() <= 0)
        {
            objName.setError(Value);
            objName.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean Year(EditText objName, String Value)
    {
        if (objName.getText().toString() == "")
        {
            objName.setError(Value);
            objName.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean Month(EditText objName, String Value)
    {
        if (objName.getText().toString() == "")
        {
            objName.setError(Value);
            objName.requestFocus();
            return false;
        }
        if (Integer.parseInt(objName.getText().toString()) >= 12)
        {
            objName.setError(Value);
            objName.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean Password(EditText objPassword1, EditText objPassword2)
    {
        if (objPassword1.getText().toString() == "" || objPassword2.getText().toString() == "")
        {
            objPassword1.setError("Enter Password");
            objPassword1.requestFocus();
            return false;
        }
        if (objPassword1.getText().toString() == "" || objPassword2.getText().toString() == "")
        {
            objPassword2.setError("Enter Password");
            objPassword2.requestFocus();
            return false;
        }
        if (objPassword1.getText().length() <= 6 || objPassword2.getText().length() <= 6)
        {
            objPassword1.setError("Minimum Lenght 7");
            objPassword2.setError("Minimum Lenght 7");
            objPassword2.requestFocus();
            return false;
        }
        String pass1 = objPassword1.getText().toString();
        String pass2 = objPassword2.getText().toString();
        if (!pass1.equals(pass2))
        {
            objPassword1.setError("Password and Confirm Password are Mismatched");
            objPassword2.setError("Password and Confirm Password are Mismatched");
            objPassword2.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean Email(EditText objEmail)
    {
        if (objEmail.getText().toString() == "")
        {
            objEmail.setError("Enter Email");
            objEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(objEmail.getText().toString()).matches())
        {
            objEmail.setError("Enter Valid Email");
            objEmail.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean Mobile(EditText objMobile)
    {
        if (objMobile.getText().toString() == "")
        {
            objMobile.setError("Enter Mobile Number");
            objMobile.requestFocus();
            return false;
        }
        if (!Patterns.PHONE.matcher(objMobile.getText().toString()).matches() || objMobile.getText().length() < 9)
        {
            objMobile.setError("Enter Valid Mobile Number");
            objMobile.requestFocus();
            return false;
        }
        return true;
    }
    public static String[] GetLinks(String text) {
        List<String> links = new ArrayList<String>();
        Matcher m = Patterns.WEB_URL.matcher(text);
        while (m.find()) {
            String url = m.group();
            Log.d("", "URL extracted: " + url);
            links.add(url);
        }
        return links.toArray(new String[links.size()]);
    }
    public static String[] GetNumber(String text) {
        List<String> links = new ArrayList<String>();
        Matcher m = Patterns.PHONE.matcher(text);
        while (m.find()) {
            String url = m.group();
            Log.d("", "URL extracted: " + url);
            links.add(url);
        }
        return links.toArray(new String[links.size()]);
    }
    public static String[] GetEmail(String text) {
        List<String> links = new ArrayList<String>();
        Matcher m = Patterns.EMAIL_ADDRESS.matcher(text);
        while (m.find()) {
            String url = m.group();
            Log.d("", "URL extracted: " + url);
            links.add(url);
        }
        return links.toArray(new String[links.size()]);
    }
    public static String[] GetEmail2(String text) {
        List<String> links = new ArrayList<String>();
        String[] separated = text.split("\n");
        for (int i = 0; i < separated.length; i++) {
            if(separated[i].contains("@"))
                links.add(separated[i]);
        }
        return links.toArray(new String[links.size()]);
    }

    public static String[] GetName(String text) {
        List<String> links = new ArrayList<String>();
        Matcher m = Patterns.DOMAIN_NAME.matcher(text);
        while (m.find()) {
            String url = m.group();
            Log.d("", "URL extracted: " + url);
            links.add(url);
        }
        return links.toArray(new String[links.size()]);
    }
}
