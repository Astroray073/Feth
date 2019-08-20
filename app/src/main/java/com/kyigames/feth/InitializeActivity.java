package com.kyigames.feth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.utils.ResourceUtils;

import org.json.JSONException;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class InitializeActivity extends AppCompatActivity {
    private static final String TAG = InitializeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);
        setViewAsFullScreen();
    }

    private void setViewAsFullScreen() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        View view = findViewById(R.id.fullscreen_content);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        try
        {
            ResourceUtils.initialize(this);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            initialize();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void initialize() throws IOException, JSONException
    {
        final NumberProgressBar progressBar = findViewById(R.id.number_progress_bar);
        progressBar.setProgress(0);
        progressBar.setMax(Database.tableCount());

        Database.loadAll(this, new OnProgressChangeListener()
        {
            @Override
            public void onProgressChanged(int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onComplete() {
                Intent intent = new Intent(InitializeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
