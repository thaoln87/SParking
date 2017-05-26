package com.tma.sparking.utils;

/**
 * Activity that request permission to read phone number need to implement this interface
 * to do work whenever phone number is available
 */
public interface OnPhoneNumberAvailable {
    /**
     * This method will be called by PhoneInformation object when phone number is available
     *
     * @param phoneNumber Phone owner's phone number
     */
    void onPhoneNumberAvailable(String phoneNumber);
}
