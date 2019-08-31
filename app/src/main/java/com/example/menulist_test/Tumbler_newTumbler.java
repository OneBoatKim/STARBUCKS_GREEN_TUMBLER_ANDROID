package com.example.menulist_test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Tumbler_newTumbler extends AppCompatActivity {

    EditText txt_tname;
    EditText txt_pin;
    MyApplication myApplication;


    //////////////////////////////NFC

    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    public static final String WRITE_ERROR = "Error during writing, is the NFC tag close enough to your device?";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean nfcMode;
    Tag myTag;
    Context context;
    ConstraintLayout constraintLayout;

    TextView tvNFCContent;
    SoundPool sound = new SoundPool(1, AudioManager.STREAM_ALARM, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_new_tumbler);
        myApplication = (MyApplication)getApplication();

        txt_tname = (EditText)findViewById(R.id.tumbler_new_txt_tname);
        txt_pin = (EditText)findViewById(R.id.tumbler_new_txt_pin);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();

            finish();
        }
        readFromIntent(getIntent());

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };


    }

    public void go_back (View v){
        finish();
    }

    public void add_tumbler_to_DB (View v){
        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/create/" + myApplication.getAccountId();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tumbler_pin", String.valueOf(txt_pin.getText()));
        contentValues.put("tumbler_name",String.valueOf(txt_tname.getText()));

        Tumbler_newtumblerTask networkTask = new Tumbler_newtumblerTask(url,contentValues);
        networkTask.execute();
        Intent intent = new Intent(Tumbler_newTumbler.this, Tumbler_main.class);
        startActivity(intent);

        finish();
    }


    public class Tumbler_newtumblerTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Tumbler_newtumblerTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            if(result.equals("1")){
                Toast.makeText(getApplicationContext(), "텀블러가 등록되었습니다.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "잘못된 입력입니다.", Toast.LENGTH_LONG).show();
            }


        }
    }

    /******************************************************************************
     **********************************Read From NFC Tag***************************
     ******************************************************************************/
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0){
            /*int soundId = sound.load(this, R.raw.siren, 1);
            int streamId = sound.play(soundId, 1.0F, 1.0F,  1,  -1,  1.0F);
            sound.stop(streamId);*/

            Toast.makeText(context,"This NFC is empty", Toast.LENGTH_LONG).show();
            return;
        }

        String text = "";
//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/create-nfc/" + myApplication.getAccountId()+"/"+ text;

        Tumbler_newtumblerTask networkTask = new Tumbler_newtumblerTask(url,null);

        networkTask.execute();

        Intent intent = new Intent(Tumbler_newTumbler.this, Tumbler_main.class);
        startActivity(intent);
        finish();

    }




    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        readFromIntent(intent);
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        ModeOff();
    }


    @Override
    public void onResume(){
        super.onResume();
        ModeOn();
    }


    /******************************************************************************
     **********************************Enable Nfc********************************
     ******************************************************************************/
    private void ModeOn(){
        nfcMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }
    private void ModeOff(){
        nfcMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }
}
