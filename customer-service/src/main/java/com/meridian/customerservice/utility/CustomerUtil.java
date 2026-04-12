package com.meridian.customerservice.utility;

import java.time.LocalDate;
import java.time.Period;

public class CustomerUtil {

    private CustomerUtil() {
    }
    public static int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        if (dateOfBirth == null || !dateOfBirth.isBefore(currentDate)) {
            throw new IllegalArgumentException("Invalid date of birth");
        }
        return Period.between(dateOfBirth, currentDate).getYears();
    }
    public static String determineCustomerType(LocalDate dateOfBirth) {
        int age = calculateAge(dateOfBirth);
        if (age < 18) {
            return "Minor";
        } else if (age >= 18 && age < 60) {
            return "Adult";
        } else {
            return "Senior citizen";
        }
    }


}
