package com.javamentor.dto.model;

import com.javamentor.model.user.User;

import java.time.LocalDate;
import java.util.Objects;

public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean enabled;
    private Boolean isCurator;
    private LocalDate registrationDate;

    public UserDto(Long id, String firstName, String lastName, String email, LocalDate registrationDate) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
    }

    public UserDto(User user) {
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRegistrationDate());
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean getCurator() {
        return isCurator;
    }

    public void setCurator(Boolean curator) {
        isCurator = curator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(email, userDto.email) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(middleName, userDto.middleName) && Objects.equals(enabled, userDto.enabled) && Objects.equals(isCurator, userDto.isCurator) && Objects.equals(registrationDate, userDto.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, middleName, enabled, isCurator, registrationDate);
    }
}

