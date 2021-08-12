package ani.notificationsend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private final String URL = "https://fcm.googleapis.com/fcm/send";
    private final String AUTH_KEY = "key=AAAARpI306c:APA91bEicZci6OdRHLT-RjoxJ0tE6KyPkjrOyjooDdo7cWmBquAmn1uvi7U8Y-ZVBcn7mRoTFcEycACG_ou_1XBnSFPSE6WMkSMdX_Y3LUl3fai5smqsSXNuR7DGZt8iaZnxNPq3-jWf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("category"))
        {
            Intent intent;
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("brand", getIntent().getStringExtra("brand"));
            intent.putExtra("category", getIntent().getStringExtra("category"));
            startActivity(intent);
        }

        requestQueue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        findViewById(R.id.user_btn).setOnClickListener(v -> sendNotification());

    }

    private void sendNotification() {
        JSONObject object = new JSONObject();

        try {
            object.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","any title");
            notificationObj.put("body","anybody");

            JSONObject extra = new JSONObject();
            extra.put("brand","puma");
            extra.put("category","shoes");


            object.put("notification",notificationObj);
            object.put("data",extra);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, object,
                    response -> Toast.makeText(this, "Notification sent successfully...", Toast.LENGTH_SHORT).show(),

                    error -> {
                        Log.e("Notification send error", "onErrorResponse: "+error.toString());
                        Toast.makeText(this, "Couldn't send notification...", Toast.LENGTH_SHORT).show();}


            ){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization",AUTH_KEY);


                    return  header;
                }
            };

            requestQueue.add(request);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}