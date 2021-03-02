package com.javamentor.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Set;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin(String email, String firstName, String lastName, String middleName, String password, Set<Role> roles, Boolean enabled, LocalDate registrationDate) {
        super(email, firstName, lastName, middleName, password, roles, enabled, registrationDate);
    }

    public Admin() {

    }
}
