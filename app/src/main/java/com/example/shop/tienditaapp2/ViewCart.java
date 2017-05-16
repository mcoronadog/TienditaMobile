package com.example.shop.tienditaapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.Iterator;
import java.util.Set;

public class ViewCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        Cart cart = Home.m_cart;

        LinearLayout cartLayout = (LinearLayout) findViewById(R.id.cart);

        Set<Product> products = cart.getProducts();

        if(cart.getValue()==0)
        {
            TextView msj = new TextView(this);
            msj.setText("Tu carrito esta vacio");
            cartLayout.addView(msj);
            msj.setTextSize(26);
            msj.setTextColor(Color.BLACK);
            msj.setGravity(Gravity.CENTER);
        }

        Iterator iterator = products.iterator();
        while(iterator.hasNext()) {
            Product product = (Product) iterator.next();

            // logic
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView name = new TextView(this);
            TextView quantity = new TextView(this);

            name.setText(product.getName());
            quantity.setText(Integer.toString(cart.getQuantity(product)));

            linearLayout.addView(name);
            linearLayout.addView(quantity);

            // display
            name.setTextSize(24);
            quantity.setTextSize(24);
            name.setTextColor(Color.BLACK);
            quantity.setTextColor(Color.RED);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    200, Gravity.CENTER);
            layoutParams.setMargins(20, 50, 20, 50);
            linearLayout.setLayoutParams(layoutParams);

            name.setLayoutParams(new TableLayout.LayoutParams(0,
                    ActionBar.LayoutParams.WRAP_CONTENT, 1));

            quantity.setLayoutParams(new TableLayout.LayoutParams(0,
                    ActionBar.LayoutParams.WRAP_CONTENT, 1));

            name.setGravity(Gravity.CENTER);
            quantity.setGravity(Gravity.CENTER);

            cartLayout.addView(linearLayout);
        }
    }
}
