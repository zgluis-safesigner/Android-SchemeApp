package com.safesigner.schemetester;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MobisignerSchemeTester";
    public static final String DATA_SCHEME_RESULT_CODE = "statusCode";
    public static final String APP_SCHEME = "schemetester";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton buttonS = findViewById(R.id.openScheme);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScheme();
            }
        });
        intentCatcher(getIntent());
    }

    private void openScheme() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String mobisignerScheme = "mobisigner";
        String operation = "transaction";
        String color = "#326295";
        String token = "XXX.XXX.XXX";
        Uri data = Uri.parse(mobisignerScheme + "://" + operation +
                "?origin=" + APP_SCHEME +
                "&token=" + token +
                "&color=" + color
        );
        Log.i(TAG, "Sending this data:" + data.toString());

        intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intentCatcher(intent);
    }

    protected void intentCatcher(Intent intent) {
        if (intent.getData() != null) {
            Log.i(TAG, "App initialized from scheme");
            try {
                Uri uri = intent.getData();
                if (uri == null) {
                    throw new Exception("No query params found");
                }
                String mobisignerResult = uri.getQueryParameters(DATA_SCHEME_RESULT_CODE).get(0);
                Toast.makeText(this, "Document Status: " + mobisignerResult, Toast.LENGTH_LONG).show();

                Log.i(TAG, uri.toString());
            } catch (Exception e) {
                Log.e(TAG, "Invalid query");
            }
        }
    }
}
