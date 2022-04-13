package com.lendingclub.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"streetAndNumber"}))
@Data
@Builder
public class Address {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private Long version;

    @Size(min = 2, max = 50)
    private String streetAndNumber;

    @Pattern(regexp = "^[0-9]{5}\\-[0-9]{3}$", message = "zipcode pattern invalid")
    private String zipcode;

    @OneToMany(mappedBy = "address")
    private Collection<User> users;

}
