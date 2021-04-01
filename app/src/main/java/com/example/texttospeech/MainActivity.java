package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private EditText metMsg;
    private SeekBar mSeekbarPitch;
    private SeekBar mSeekbarSpeed;
    private Button mBtnSpeak;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnSpeak=findViewById(R.id.btnSayit);

        mTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
        if(status==TextToSpeech.SUCCESS)
        {
            int result=mTTS.setLanguage(Locale.ENGLISH);

            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS","Language not supported");
            }else{
                mBtnSpeak.setEnabled(true);

            }
        }
        else{
            Log.e("TTS","Initialization failed");

        }
        }

    });
        metMsg=findViewById(R.id.ETMsg);
        mSeekbarPitch=findViewById(R.id.seek_bar_pitch);
        mSeekbarSpeed=findViewById(R.id.seek_bar_speed);

        mBtnSpeak.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                speak();
        }
    });

    }

    private void speak(){
        String text=metMsg.getText().toString();

        float pitch= (float) mSeekbarPitch.getProgress() / 50;
        if(pitch<0.1)
        {
            pitch=0.1f;
        }

        float speed=(float) mSeekbarSpeed.getProgress() / 50;
        if(speed<0.1){
            speed=0.1f;
        }
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {
        if(mTTS!=null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

}