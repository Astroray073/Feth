package com.kyigames.feth;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.kyigames.feth.model.Database;

import java.util.Objects;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class InitializeActivity extends AppCompatActivity {
    private static final String TAG = InitializeActivity.class.getSimpleName();
    private static final int UPDATE_REQUEST_CODE = 0;

    private AppUpdateManager m_updateManager;

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

        // Creates instance of the manager.
        m_updateManager = AppUpdateManagerFactory.create(this);

        m_updateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                        try {
                            m_updateManager.startUpdateFlowForResult(
                                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                    appUpdateInfo,
                                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                    IMMEDIATE,
                                    // The current activity making the update request.
                                    this,
                                    // Include a request code to later monitor this update request.
                                    UPDATE_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                        }
                    } else {
                        initialize();
                    }
                });
    }

    private void initialize() {
        final NumberProgressBar progressBar = findViewById(R.id.number_progress_bar);
        progressBar.setProgress(0);
        progressBar.setMax(Database.tableCount());

        Database.loadAll(new OnProgressChangeListener() {
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
