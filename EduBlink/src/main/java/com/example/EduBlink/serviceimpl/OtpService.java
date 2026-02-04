package com.example.EduBlink.serviceimpl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.fromNumber}")
    private String fromNumber;

    public void sendOtp(String phone, String otp) {

        // Ensure +91 format
        if (!phone.startsWith("+")) {
            phone = "+91" + phone;
        }

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber("whatsapp:" + phone),          // TO
                new PhoneNumber("whatsapp:" + fromNumber),     // FROM (Twilio Sandbox)
                "🔐 Your EduBlink OTP is: " + otp
        ).create();
    }
}
