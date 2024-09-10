package com.edulab.e_commerceapp.Class;

import java.util.Date;

public class Claim {
    public Date dateclaim;
    public String Subject, Message,Phone,Photo64;

    public Claim() {
    }

    public Claim(Date dateclaim, String subject, String message, String phone, String photo64) {
        this.dateclaim = dateclaim;
        Subject = subject;
        Message = message;
        Phone = phone;
        Photo64 = photo64;
    }
}
