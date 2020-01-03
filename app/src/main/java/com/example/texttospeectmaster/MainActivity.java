package com.example.texttospeectmaster;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView t1,t2;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech ttsobject;
    int result;
    EditText et;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText1);

       t2 = findViewById(R.id.btntext);



        ttsobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {  //for initialize text to speech engine

                if (status == TextToSpeech.SUCCESS) {

                    result = ttsobject.setLanguage(Locale.UK);

                } else {

                    Toast.makeText(getApplicationContext(),
                            "Feature not Supported in Your Device",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_SPEECH_INPUT)
        {
            if(resultCode == RESULT_OK && null != data)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                t2.setText(result.get(0));

                // save speech to text file

            }
        }

    }

    public void doSomething(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.bspeak:

                if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){

                    Toast.makeText(getApplicationContext(),
                            "Feature not Supported in Your Device",
                            Toast.LENGTH_SHORT).show();

                }else{
                    text = et.getText().toString();

                    ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);  ///we can add multiple parameters for sound

                }


                break;

            case R.id.bstopspeaking:

                if(ttsobject != null){

                    ttsobject.stop();


                }

                break;
            case  R.id.btnspeak:

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii Hello Urmila");

                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                break;

        }


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(ttsobject != null){

            ttsobject.stop();
            ttsobject.shutdown();

        }

    }

}
