package com.kappa.web.values;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="values")
class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    Value() {}

    Value(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}