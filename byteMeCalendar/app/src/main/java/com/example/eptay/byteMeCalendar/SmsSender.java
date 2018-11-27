package com.example.eptay.byteMeCalendar;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "AC7114a1c8bac24c7a8add4c34d35887d6";
    public static final String AUTH_TOKEN =
            "a867af19b80bdc646a686406bb441081";
    public static final String myPhoneNumber = "+19562757975";
    public Message message;


    public SmsSender(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendMessage(String receivingPhoneNumber, String msg){
        Message message = Message
                .creator(new PhoneNumber("+14159352345"), // to
                        new PhoneNumber("+19562757975"), // from
                        "Where's Wallace?")
                .create();
        System.out.println(message.getSid());
    }

}
