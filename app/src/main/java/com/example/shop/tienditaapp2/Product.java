package com.example.shop.tienditaapp2;

/**
 * Created by Manuel on 15/05/2017.
 */

public class Product
{
    String m_name;
    double m_value;

    Product(String name, double value)
    {
        m_name = name;
        m_value = value;
    }

    String getName()
    {
        return m_name;
    }

    double getValue()
    {
        return m_value;
    }
}

