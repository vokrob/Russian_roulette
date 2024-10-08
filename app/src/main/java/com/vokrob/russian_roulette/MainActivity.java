package com.vokrob.russian_roulette;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends ComponentActivity {
    private SoundPool sounds;
    private int sound_shot;
    private int sound_false_shot;
    private int sound_drum;
    private ImageView blood_image;
    private int on_shot = 3;
    private int max_number = 6;
    private int random = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        createSoundPool();
        loadSounds();
        init();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sounds = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    private void loadSounds() {
        sound_shot = sounds.load(this, R.raw.revolver_shot, 1);
        sound_false_shot = sounds.load(this, R.raw.gun_false, 1);
        sound_drum = sounds.load(this, R.raw.revolver_baraban, 1);
    }

    public void onShot(View view) {
        if (random == on_shot) {
            sounds.play(sound_shot, 1.0f, 1.0f, 1, 0, 1);
            blood_image.setVisibility(View.VISIBLE);
        } else {
            sounds.play(sound_false_shot, 1.0f, 1.0f, 1, 0, 1);
        }
    }

    public void onDrum(View view) {
        sounds.play(sound_drum, 1.0f, 1.0f, 1, 0, 1);
        blood_image.setVisibility(View.GONE);
        random = new Random().nextInt(max_number);
    }

    private void init() {
        blood_image = findViewById(R.id.image_blood);
    }
}


















