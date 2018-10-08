package com.example.aniket.businga;

public class ImportantContactsItemDetails {
    String name;
    String phoneNumber;

    public ImportantContactsItemDetails(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
