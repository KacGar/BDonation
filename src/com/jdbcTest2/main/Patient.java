package com.jdbcTest2.main;
import java.util.Map;

public class Patient {

    private static Map<String,String> patientData;

    public Patient(Map<String,String> patient){
        patientData = patient;
    }

    public String getName(){
        return patientData.get("name");
    }
    public String getLastName(){
        return patientData.get("lastName");
    }
    public String getPhoneNumber(){
        return patientData.get("telNumber");
    }
    public String getSocialNumber(){
        return patientData.get("socialNumber");
    }
    public String getEmail(){
        return patientData.get("email");
    }
    public String getAge(){
        return patientData.get("age");
    }
    public String getWeight(){
        return patientData.get("weight");
    }
    public String getSex(){
        return patientData.get("sex");
    }
    public String getStreet(){
        return patientData.get("street");
    }
    public String getCity(){
        return patientData.get("city");
    }
    public String getCityCode(){
        return patientData.get("cityCode");
    }
    public String getCountry(){
        return patientData.get("country");
    }
    public String getAmountOfBlood(){
        return patientData.get("amountOfBlood");
    }
    public String getBloodType(){
        return patientData.get("bloodType");
    }
    public String isRegistered(){
        return patientData.get("isRegistered");
    }
    public String getDate(){
        return patientData.get("date");
    }
    public String getTime(){
        return patientData.get("time");
    }
}
