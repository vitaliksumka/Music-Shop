package android.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

// Імплементуємо інтерфейс "AdapterView.OnItemSelectedListener" - наш клас буде слушать события и реагировать на эти события, после имплементации сразу автоматически добавляется два метода: onItemSelected,  onNothingSelected;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int quantity = 0;
    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter; // адаптер для спіннера
    HashMap goodsMap;
    String goodsName;
    double price;
    EditText userNameEditText; // додатковий клас для поля в якому ми записуємо імя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.enterNameField); // знаходимо по ID поле в якому записуємо імя
        createSpinner();
        createHaspMap();
    }

     void createSpinner(){
         spinner = findViewById(R.id.spinner);
         spinner.setOnItemSelectedListener(this); // присвоємо лісенер щоб наш клас прослуховував cобытие этого спиннера;
         spinnerArrayList = new ArrayList();
         spinnerArrayList.add("Квиток на 1 день");
         spinnerArrayList.add("Квиток на 2 дні");
         spinnerArrayList.add("Квиток на 3 дні");
         spinnerArrayList.add("Квиток на 4 дні");
         spinnerArrayList.add("Квиток на 5 днів");

         spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList );   // тут передаємо в context на клас "this" в якому находиться спіннер, далі передаємо переопределенный елемент спіннера, і наш ArrayList з елементами
         spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // тут за допомогою цієї строки ми добавляємо випадающий список "simple_spinner_dropdown_item" до нашого спіннера
         spinner.setAdapter(spinnerAdapter);      // тут встановлюємо наш адаптера для спіннера
    }

    void createHaspMap(){
        goodsMap = new HashMap();
        goodsMap.put("Квиток на 1 день", 300.0);
        goodsMap.put("Квиток на 2 дні", 600.0);
        goodsMap.put("Квиток на 3 дні", 900.0);
        goodsMap.put("Квиток на 4 дні", 1200.0);
        goodsMap.put("Квиток на 5 днів", 1500.0);
    }

    public void methodPlus(View view) {
        quantity = quantity + 1;
        TextView quatityButton = findViewById(R.id.numberInTheCenterButtons);
        quatityButton.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.totalPrice); // підєднюємо поле totalPrice;
        priceTextView.setText(""+ quantity * price);
    }

    public void minusButton(View view) {
        quantity = quantity - 1;
        if(quantity < 0){
            quantity = 0;
        }
        TextView mb = findViewById(R.id.numberInTheCenterButtons);
        mb.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.totalPrice); // підєднюємо поле totalPrice;
        priceTextView.setText(""+ quantity * price);
    }


    @Override // тут пишемо код який повинен діяти коли елемент вибраний
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString(); //присвоємо поточне значення яке стоїть на вибраному Item в нашому спіннері
        price = (double)goodsMap.get(goodsName); // присвоюємо значення з нашої "goodsMap" по ключу "goodsName"
        TextView priceTextView = findViewById(R.id.totalPrice); // підєднюємо поле totalPrice;
        priceTextView.setText(""+ quantity * price);



        ImageView im = findViewById(R.id.imageView2); // робимо switch case для того щоб мінялась картинка коли обираємо квитки на різні дні в спіннері
        switch (goodsName) {
            case "Квиток на 1 день":
                im.setImageResource(R.drawable.faine);
                break;
            case "Квиток на 2 дні":
                im.setImageResource(R.drawable.images2);
                break;
            case "Квиток на 3 дні":
                im.setImageResource(R.drawable.images3);
                break;
            case "Квиток на 4 дні":
                im.setImageResource(R.drawable.images4);
                break;
            case "Квиток на 5 днів":
                im.setImageResource(R.drawable.images1);
                break;
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCart(View view) {
        Order order = new Order();
        order.userName = userNameEditText.getText().toString();
         //  Log.d("printUserName",  order.userName); // в LogCat в пошуку ми можемо перевірити по тегу "printUserName" чи записується наше імя клієнта

        order.goodsName = goodsName;
         //  Log.d("goodsName",  order.goodsName); // в LogCat в пошуку ми можемо перевірити по тегу "goodsName" чи записується імя товару

        order.quantity = quantity;
        //   Log.d("quantity", "" + order.quantity); // в LogCat в пошуку ми можемо перевірити по тегу "printUserName" чи записується кількість товарів

        order.orderPrice = quantity * price;
        //    Log.d("orderPrice",  ""+ order.orderPrice); // в LogCat в пошуку ми можемо перевірити по тегу "printUserName" чи записується ціна товару

        order.price = price;

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class); //  щоб запустити другу activity эє такий допоміжний клас, в дужках вказуємо в першому параметрі - з якого актівіті запускаємо, в другому - яке саме актівіті запускаємо
        orderIntent.putExtra("userNameForIntent", order.userName ); // передаємо дані до нової сторінки до першого значення привязали ключ шо знаходиться в другому значені
        orderIntent.putExtra("goodsName", order.goodsName );
        orderIntent.putExtra("quantity", order.quantity );
        orderIntent.putExtra("orderPrice", order.orderPrice );
        orderIntent.putExtra("price", order.price);

        startActivity(orderIntent); // метод в який передаємо який Intent з параметрами хочемо запустити
    }
}