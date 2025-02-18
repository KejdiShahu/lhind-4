package com.lhind.internship.jdbc.model.enums;

public enum EmployeeQuery {

    SELECT_ALL("SELECT * FROM employees;"),
    SELECT_BY_ID("SELECT * FROM employees WHERE employeeNumber = ?;"),
    INSERT_OR_UPDATE_EMPLOYEE("INSERT INTO employees " +
            "(employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE " +
            "firstName = VALUES(firstName), " +
            "lastName = VALUES(lastName), " +
            "extension = VALUES(extension), " +
            "email = VALUES(email), " +
            "officeCode = VALUES(officeCode), " +
            "reportsTo = VALUES(reportsTo), " +
            "jobTitle = VALUES(jobTitle);"
    ),
    DELETE_BY_ID("DELETE FROM employees WHERE employeeNumber = ?");

    private final String query;

    EmployeeQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
