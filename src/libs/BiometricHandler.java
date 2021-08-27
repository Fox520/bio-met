package org.kivy.plugins.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;

import java.util.concurrent.Executor;

public class BiometricHandler {
    static BiometricPrompt.AuthenticationResult authResult;
    static int randomNumber;
    static int errorCode;
    static String errorMessage;

    // @Override
    // protected void onCreate(Bundle savedInstanceState) {
    //     super.onCreate(savedInstanceState);
    //     setContentView(R.layout.activity_main);
//        authenticate(getApplicationContext(), this);
    // }


    public static int checkFingerprintSensor(){
        // Check if device has fingerprint sensor or not
        randomNumber = -1;
        BiometricManager biometricManager = BiometricManager.from(org.kivy.android.PythonActivity.mActivity.getApplicationContext());
        return biometricManager.canAuthenticate();

    }
    public static void authenticate(Context context, FragmentActivity activity){
        randomNumber = -1;
        BiometricManager biometricManager = BiometricManager.from(org.kivy.android.PythonActivity.mActivity.getApplicationContext());
        // Dialog
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Kivy Biometric")
                .setDescription("Use your fingerprint to login to your app")
                .setNegativeButtonText("Cancel")
                .build();
        Executor executor = ContextCompat.getMainExecutor(context);
        BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int code, @NonNull CharSequence msg) {
                errorCode = code;
                errorMessage = msg.toString();
                randomNumber = 2;
                authResult = null;
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                randomNumber = 0;
                authResult = result;
                errorMessage = "";
                errorCode = 0;
            }

            @Override
            public void onAuthenticationFailed() {
                // unauthorized
                randomNumber = 1;
                authResult = null;
                errorMessage = "";
                errorCode = 0;
            }
        });

        // Bring up dialog
        biometricPrompt.authenticate(promptInfo);
    }
}
