package com.example.poorwa.healthfood;

import java.util.Comparator;

/**
 * Created by poorwa on 25/2/16.
 */
public class TypeComparator implements Comparator<Meal>
{
    public int compare(Meal left, Meal right) {
        return left.getType().compareTo(right.getType());
    }
}
