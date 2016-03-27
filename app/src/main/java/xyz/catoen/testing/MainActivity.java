package xyz.catoen.testing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.Constants;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    int leftPingKey = 2;
    int rightPingKey = 3;
    final UUID appUuid = UUID.fromString("ad21068a-4213-45cd-8aae-0e0cfa3a4151");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button leftButton = (Button)findViewById(R.id.PingLeft);
        Button rightButton = (Button)findViewById((R.id.PingRight));
        FloatingActionButton continueButton = (FloatingActionButton)findViewById(R.id.ContinueButton);

        leftButton.setOnClickListener(onClickListener);
        rightButton.setOnClickListener(onClickListener);
        continueButton.setOnClickListener(onClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        super.onResume();

        // Construct output String
        StringBuilder builder = new StringBuilder();
        builder.append("Pebble Info\n\n");

        // Is the watch connected?
        boolean isConnected = PebbleKit.isWatchConnected(this);
        builder.append("Watch connected: " + (isConnected ? "true" : "false")).append("\n");

        // What is the firmware version?
        PebbleKit.FirmwareVersionInfo info = PebbleKit.getWatchFWVersion(this);
        builder.append("Firmware version: ");
        builder.append(info.getMajor()).append(".");
        builder.append(info.getMinor()).append("\n");

        // Is AppMesage supported?
        boolean appMessageSupported = PebbleKit.areAppMessagesSupported(this);
        builder.append("AppMessage supported: " + (appMessageSupported ? "true" : "false"));

        TextView textView = (TextView)findViewById(R.id.text_view);
        textView.setText(builder.toString());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PebbleDictionary dict = new PebbleDictionary();
            switch(v.getId()){
                case R.id.ContinueButton:
                    Toast.makeText(getApplicationContext(), "Entering Maps Activity", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.PingRight:
                    dict.addInt32(rightPingKey, 10);
                    PebbleKit.sendDataToPebble(getApplicationContext(), appUuid, dict);
                    Toast.makeText(getApplicationContext(), "Sent Right Ping", Toast.LENGTH_LONG).show();
                    break;
                case R.id.PingLeft:
                    dict.addInt32(leftPingKey, 20);
                    PebbleKit.sendDataToPebble(getApplicationContext(), appUuid, dict);
                    Toast.makeText(getApplicationContext(), "Sent Left Ping", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
