package com.example.eptay.byteMeCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IsValidPhone {
    private String phoneN;
    private boolean valid;
    @Before
    public void setup() {
        valid = false;
    }

    @Test //Test with valid values
    public void testAddEvent1() {
        String phone = "9564550732";
        if(phone.length() == 10 & android.text.TextUtils.isDigitsOnly(phone)) {
            valid = true;
        }

        assertTrue(valid);
    }

    public void testAddEvent2() {
        String phone = "95640732";
        android.text.TextUtils.isDigitsOnly(phone);
        if(phone.length() == 10 & android.text.TextUtils.isDigitsOnly(phone)){
            valid = true;
        }

        assertTrue(valid);
    }

    public void testAddEvent3() {
        String phone = "956455073002";
        android.text.TextUtils.isDigitsOnly(phone);
        if(phone.length() == 10 & android.text.TextUtils.isDigitsOnly(phone)){
            valid = true;
        }

        assertTrue(valid);
    }

    public void testAddEvent4() {
        String phone = "9564550a32";
        android.text.TextUtils.isDigitsOnly(phone);
        if(phone.length() == 10 & android.text.TextUtils.isDigitsOnly(phone)){
            valid = true;
        }

        assertTrue(valid);
    }

}
