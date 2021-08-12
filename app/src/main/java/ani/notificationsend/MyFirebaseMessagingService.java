package ani.notificationsend;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String body = Objects.requireNonNull(remoteMessage.getNotification()).getBody();

        Map<String, String> data = remoteMessage.getData();

        String brandId = data.get("brand");
        String category = data.get("category");

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "ANI")
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.noteicon);

        Intent intent;
        if (category.equals("shoes")) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("brand", brandId);
            intent.putExtra("category", category);

        } else {
            intent = new Intent(this, MainActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int id = (int) System.currentTimeMillis();


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ANI", "demo", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }


        manager.notify(id, builder.build());


    }
}
