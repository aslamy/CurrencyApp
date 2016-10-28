package com.benjamin.currencyapp.model;

/**
 * Created by Benjamin on 2015-11-19.
 */
public class CurrencyCalculator {

    public double Calculate(double rate1, double rate2, double value) {

        return (rate2 / rate1) * value;
    }

}
