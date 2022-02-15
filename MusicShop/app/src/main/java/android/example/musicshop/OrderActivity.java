package android.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {


    String[] addresses = {"vitaliisumka@gmail.com"};
    String subject = "Festival tickets shop";
    String emaiDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setTitle("Your Order");

        Intent receivedOrderIntent = getIntent(); // getIntent() повертає інтент при за допомогою якого нам запускає Activity
        String userName = receivedOrderIntent.getStringExtra("userNameForIntent"); // витягуємо значення по ключу з нашого Інтента
        String goodsName = receivedOrderIntent.getStringExtra("goodsName");
        int quantity = receivedOrderIntent.getIntExtra("quantity", 0); // тут встановлюємо дефолтне додатково 0 значення якщо не буде найдено значення по ключу
        double orderPrice = receivedOrderIntent.getDoubleExtra("orderPrice", 0);
        double price = receivedOrderIntent.getDoubleExtra("price", 0);

        emaiDescription = "Customer name: " + userName +
                "\n" + "Goods name: " +goodsName +
                "\n" + "Price: "+  quantity +
                "\n" + "One price: " + price +
                "\n" +"Order price: " + orderPrice;

        TextView orderTextView = findViewById(R.id.orderTextView);
        orderTextView.setText(emaiDescription);
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // по умолчание это gmail
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emaiDescription);
        if (intent.resolveActivity(getPackageManager()) != null) { // выдаст кое то приложение которое может выслать мейл, если == null тогда мейл не отправиться потому что не нашло допустимого приложения для отправки мейла
            startActivity(intent); // открываем это приложение
        }
    }
}