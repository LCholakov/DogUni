package com.doguni.model;

public enum AccountType {
    OWNER("Owner"),
    TRAINER("Trainer");

    private final String displayName;


    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
