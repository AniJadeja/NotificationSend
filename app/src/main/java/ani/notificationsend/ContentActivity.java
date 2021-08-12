package ani.notificationsend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        TextView cattv = findViewById(R.id.category);
        TextView brandtv = findViewById(R.id.brandId);

        if (getIntent().hasExtra("category"))
        {
            String category = getIntent().getStringExtra("category");
            String brand = getIntent().getStringExtra("brand");
            cattv.setText(category);
            brandtv.setText(brand);
        }
    }
}