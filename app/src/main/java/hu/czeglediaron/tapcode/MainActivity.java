package hu.czeglediaron.tapcode;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvOutput;
    Button bTap;
    Button bShortPause;
    Button bLongPause;
    Button bSpace;
    Button bBackspace;

    boolean firstPart = true;
    char currentLetter = '\0';
    int[] code = {-1, 0, 0};
    int[] currentCode = {-1, 0, 0};
    String text = "";
    String currentWord = "";
    AudioManager am;
    MediaPlayer knockSound;
    MediaPlayer pauseSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.app_name));
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        tvOutput = (TextView) findViewById(R.id.tv_output);
        bTap = (Button) findViewById(R.id.b_tap);
        bShortPause = (Button) findViewById(R.id.b_shortpause);
        bLongPause = (Button) findViewById(R.id.b_longpause);
        bSpace = (Button) findViewById(R.id.b_space);
        bBackspace = (Button) findViewById(R.id.b_backspace);

        bShortPause.setEnabled(false);
        bLongPause.setEnabled(false);
        bSpace.setEnabled(false);
        bBackspace.setEnabled(false);

        if(text.length() == 1) {
            bBackspace.setEnabled(false);
        }
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        knockSound = MediaPlayer.create(this, R.raw.tap);
        pauseSound = MediaPlayer.create(this, R.raw.pause);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mi_help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
            case R.id.mi_quit:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_bTap(View view) {

        if(firstPart) {
            code[1] += 1;
        } else {
            code[2] += 1;
        }

        if(firstPart) {
            bShortPause.setEnabled(true);
        }

        setTitle(getResources().getString(R.string.app_name) + "(" + String.valueOf(code[1]) + "," + String.valueOf(code[2]) + ")");

        if(code[1] == 0 || code[2] == 0) {
            bLongPause.setEnabled(false);
            bSpace.setEnabled(false);
            bBackspace.setEnabled(false);
        } else {
            bLongPause.setEnabled(true);
        }

        if(code[1] == 5 && firstPart) {
            bTap.setEnabled(false);
        } else if(code[2] == 5) {
            bTap.setEnabled(false);
        }

        if(text.length() == 39) {
            bTap.setEnabled(false);
        }
    }

    public void onClick_bShortPause(View view) {
        firstPart = false;
        bShortPause.setEnabled(false);
        bTap.setEnabled(true);
    }

    public void onClick_bLongPause(View view) {

        if(code[1] == 1) {
            if(code[2] == 1) { currentLetter = 'A'; }
            if(code[2] == 2) { currentLetter = 'B'; }
            if(code[2] == 3) { currentLetter = 'C'; }
            if(code[2] == 4) { currentLetter = 'D'; }
            if(code[2] == 5) { currentLetter = 'E'; }
        } else if(code[1] == 2) {
            if(code[2] == 1) { currentLetter = 'F'; }
            if(code[2] == 2) { currentLetter = 'G'; }
            if(code[2] == 3) { currentLetter = 'H'; }
            if(code[2] == 4) { currentLetter = 'I'; }
            if(code[2] == 5) { currentLetter = 'J'; }
        } else if(code[1] == 3) {
            if (code[2] == 1) { currentLetter = 'L'; }
            if (code[2] == 2) { currentLetter = 'M'; }
            if (code[2] == 3) { currentLetter = 'N'; }
            if (code[2] == 4) { currentLetter = 'O'; }
            if (code[2] == 5) { currentLetter = 'P'; }
        } else if(code[1] == 4) {
            if (code[2] == 1) { currentLetter = 'Q'; }
            if (code[2] == 2) { currentLetter = 'R'; }
            if (code[2] == 3) { currentLetter = 'S'; }
            if (code[2] == 4) { currentLetter = 'T'; }
            if (code[2] == 5) { currentLetter = 'U'; }
        } else if(code[1] == 5) {
            if (code[2] == 1) { currentLetter = 'V'; }
            if (code[2] == 2) { currentLetter = 'W'; }
            if (code[2] == 3) { currentLetter = 'X'; }
            if (code[2] == 4) { currentLetter = 'Y'; }
            if (code[2] == 5) { currentLetter = 'Z'; }
        }

        currentWord += currentLetter;

        text += currentLetter;
        tvOutput.setText(text);

        code[1] = 0;
        code[2] = 0;
        firstPart = true;

        if(text.length() == 39) {
            bTap.setEnabled(false);
            bShortPause.setEnabled(false);
            bLongPause.setEnabled(false);
        } else {
            bTap.setEnabled(true);
        }

        if(currentWord.equals("")) {
            bSpace.setEnabled(false);
        }

        bShortPause.setEnabled(false);
        bSpace.setEnabled(true);

        bBackspace.setEnabled(true);
    }

    public void onClick_bSpace(View view) {

        setTitle(getResources().getString(R.string.app_name));

        if(am.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
            bTap.setEnabled(false);
            bShortPause.setEnabled(false);
            bLongPause.setEnabled(false);
            bSpace.setEnabled(false);
            bBackspace.setEnabled(false);
        } else {
            bLongPause.setEnabled(false);
            bSpace.setEnabled(false);
            bBackspace.setEnabled(false);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                text += " ";
                if(am.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
                    for (char c : currentWord.toCharArray()) {
                        switch (c) {
                            case 'A':
                                currentCode[1] = 1;
                                currentCode[2] = 1;
                                break;
                            case 'B':
                                currentCode[1] = 1;
                                currentCode[2] = 2;
                                break;
                            case 'C':
                                currentCode[1] = 1;
                                currentCode[2] = 3;
                                break;
                            case 'D':
                                currentCode[1] = 1;
                                currentCode[2] = 4;
                                break;
                            case 'E':
                                currentCode[1] = 1;
                                currentCode[2] = 5;
                                break;
                            case 'F':
                                currentCode[1] = 2;
                                currentCode[2] = 1;
                                break;
                            case 'G':
                                currentCode[1] = 2;
                                currentCode[2] = 2;
                                break;
                            case 'H':
                                currentCode[1] = 2;
                                currentCode[2] = 3;
                                break;
                            case 'I':
                                currentCode[1] = 2;
                                currentCode[2] = 4;
                                break;
                            case 'J':
                                currentCode[1] = 2;
                                currentCode[2] = 5;
                                break;
                            case 'L':
                                currentCode[1] = 3;
                                currentCode[2] = 1;
                                break;
                            case 'M':
                                currentCode[1] = 3;
                                currentCode[2] = 2;
                                break;
                            case 'N':
                                currentCode[1] = 3;
                                currentCode[2] = 3;
                                break;
                            case 'O':
                                currentCode[1] = 3;
                                currentCode[2] = 4;
                                break;
                            case 'P':
                                currentCode[1] = 3;
                                currentCode[2] = 5;
                                break;
                            case 'Q':
                                currentCode[1] = 4;
                                currentCode[2] = 1;
                                break;
                            case 'R':
                                currentCode[1] = 4;
                                currentCode[2] = 2;
                                break;
                            case 'S':
                                currentCode[1] = 4;
                                currentCode[2] = 3;
                                break;
                            case 'T':
                                currentCode[1] = 4;
                                currentCode[2] = 4;
                                break;
                            case 'U':
                                currentCode[1] = 4;
                                currentCode[2] = 5;
                                break;
                            case 'V':
                                currentCode[1] = 5;
                                currentCode[2] = 1;
                                break;
                            case 'W':
                                currentCode[1] = 5;
                                currentCode[2] = 2;
                                break;
                            case 'X':
                                currentCode[1] = 5;
                                currentCode[2] = 3;
                                break;
                            case 'Y':
                                currentCode[1] = 5;
                                currentCode[2] = 4;
                                break;
                            case 'Z':
                                currentCode[1] = 5;
                                currentCode[2] = 5;
                                break;
                        }

                        for (int i = 1; i <= currentCode[1]; i++) {
                            knockSound.start();
                            while (knockSound.isPlaying()) ;
                        }

                        pauseSound.start();
                        while (pauseSound.isPlaying()) ;

                        for (int i = 1; i <= currentCode[2]; i++) {
                            knockSound.start();
                            while (knockSound.isPlaying()) ;
                        }

                        pauseSound.start();
                        while (pauseSound.isPlaying()) ;
                        pauseSound.start();
                        while (pauseSound.isPlaying()) ;
                    }

                    currentWord = "";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            code[1] = 0;
                            code[2] = 0;

                            bTap.setEnabled(true);
                            bShortPause.setEnabled(false);
                            bLongPause.setEnabled(false);
                            bSpace.setEnabled(false);
                            bBackspace.setEnabled(false);
                        }
                    });
                } else {
                    currentWord = "";
                }
            }
        }).start();
    }

    public void onClick_bBackspace(View view) {

        bTap.setEnabled(true);
        bShortPause.setEnabled(false);
        bLongPause.setEnabled(true);

        if(text.length() > 1) {
            if(!currentWord.equals("")) {
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            } else {
                bSpace.setEnabled(false);
            }

            if(!text.substring(text.length() - 1).equals(" ")) {
                text = text.substring(0, text.length() - 1);
            } else {
                text = text.substring(0, text.length() - 2);
            }

            tvOutput.setText(text);
        } else {
            text = text.substring(0, text.length() - 1);
            if(!currentWord.equals(""))
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            tvOutput.setText(text);
            bBackspace.setEnabled(false);
        }
    }
}
