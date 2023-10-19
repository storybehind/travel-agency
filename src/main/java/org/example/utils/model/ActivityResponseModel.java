package org.example.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Model used to represent Activity
 */
@Getter
@Setter
public class ActivityResponseModel {

    /**
     * Activity name
     */
    private String name;

    /**
     * Activity description
     */
    private String description;

    /**
     * Activity cost
     */
    private BigDecimal cost;

    /**
     * Activity remaining capacity
     */
    private int capacity;

}
