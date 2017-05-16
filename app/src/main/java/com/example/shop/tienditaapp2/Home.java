package com.example.shop.tienditaapp2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import java.math.BigDecimal;

public class Home extends AppCompatActivity {

    static TextView m_response;
    static Cart m_cart;

    PayPalConfiguration m_configuration;
    String m_paypalClientId = "ASbSfj8PG-PPJb01yeJVJB_HKAcBMdZx2rqZ5emVwvpKCNongd8Mu9kgpnn_erQonCcdAseqxtzjUuGp";
    Intent m_service;
    int m_paypalRequestCode = 999; // or any number you want

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout list = (LinearLayout) findViewById(R.id.list);

        m_response = (TextView) findViewById(R.id.response);

        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        startService(m_service);

        m_cart = new Cart();

        Product products[] =
                {
                        new Product("Intel Core i7", 7000.00),
                        new Product("Nvidia GTX 1080 Gigabyte", 10000.00),
                        new Product("Motherboard Gigabyte Z170x", 3000.00),
                        new Product("SSD Adata 512 GB", 3300.00),
                        new Product("HDD Seagate 1 TB", 890.00),
                        new Product("Ventilador Riotoro", 200.00),
                        new Product("HyperX RAM 8 GB", 1400.00)
                };

        for(int i = 0 ; i < products.length ; i++)
        {
            Button button = new Button(this);

            ImageView image = new ImageView(this);
            switch (i)
            {
                case 0:
                    image.setImageResource(R.drawable.intel_i7);
                    break;
                case 1:
                    image.setImageResource(R.drawable.nvidia_1080);
                    break;
                case 2:
                    image.setImageResource(R.drawable.mother_z170);
                    break;
                case 3:
                    image.setImageResource(R.drawable.adata_512gb);
                    break;
                case 4:
                    image.setImageResource(R.drawable.seagate_1tb);
                    break;
                case 5:
                    image.setImageResource(R.drawable.riotoro_vent);
                    break;
                case 6:
                    image.setImageResource(R.drawable.hyperx_8gb);
                    break;
            }
            image.setAdjustViewBounds(true);
            image.setLayoutParams(new LinearLayout.LayoutParams(300, 300, Gravity.CENTER));

            button.setText(products[i].getName() + " --- " + products[i].getValue() + " $");
            button.setTag(products[i]);

            // display
            button.setTextSize(20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            layoutParams.setMargins(20, 50, 20, 50);
            button.setLayoutParams(layoutParams);

            button.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    Button button = (Button) view;
                    Product product = (Product) button.getTag();

                    m_cart.addToCart(product);
                    m_response.setText("Total = " + String.format("%.2f", m_cart.getValue()) + " $");
                }
            });

            list.addView(image);
            list.addView(button);
        }
    }

    void pay(View view)
    {
        PayPalPayment cart = new PayPalPayment(new BigDecimal(m_cart.getValue()), "MXN", "Cart",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, cart);
        startActivityForResult(intent, m_paypalRequestCode);
    }

    void viewCart(View view)
    {
        Intent intent = new Intent(this, ViewCart.class);
        m_cart = m_cart;
        startActivity(intent);
    }

    void reset(View view)
    {
        m_response.setText("Total cart value = 0 $");
        m_cart.empty();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == m_paypalRequestCode)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    String state = confirmation.getProofOfPayment().getState();

                    if(state.equals("approved"))
                        m_response.setText("payment approved");
                    else
                        m_response.setText("error in the payment");
                }
                else
                    m_response.setText("confirmation is null");
            }
        }
    }
}
