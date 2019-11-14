package com.example.toquizz.Model;

public class User {

    private String FirstName;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String mFirstName) {
        this.FirstName = mFirstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "mFirstname='" + FirstName + '\'' +
                '}';
    }

}
