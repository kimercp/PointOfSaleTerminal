package com.kimersoft.pointofsaleterminal;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kimersoft.pointofsaleterminal.utils.LottieAnimations;
import com.kimersoft.pointofsaleterminal.volleyHelpers.VolleyRequestsHandler;
import com.kimersoft.pointofsaleterminal.volleyHelpers.VolleyCallback;
import com.kimersoft.pointofsaleterminal.models.User;
import com.kimersoft.pointofsaleterminal.volleyHelpers.ObjectToJsonParser;
import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    private EditText etSignInEmail, etSignInPwd;
    private Button btnSignIn;

    private RelativeLayout rlLoading;
    private LottieAnimationView loading;
    private LottieAnimations lottieAnimations;

    // my added variables
    private VolleyRequestsHandler volleyRequestsHandler;
    private ObjectToJsonParser usersDetails;


    private SharedPreferences preferences;

    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViewById(R.id.rl_sign_in).setBackground(ContextCompat.getDrawable(this, R.drawable.gradient_background_radial));

// this is temporarty to avoid sing in
//        startActivity(new Intent(SignInActivity.this, MyMenuPOS.class));

        // full screen on device
        makeFullscreen();
        // splash screen
        splashScreen(2000);

        //Initializing values
        lottieAnimations = new LottieAnimations();

        //Bind UI
        etSignInEmail = findViewById(R.id.et_sign_in_email);
        etSignInPwd = findViewById(R.id.et_sign_in_pwd);
        btnSignIn = findViewById(R.id.btn_sign_in);
        rlLoading = findViewById(R.id.rl_sign_in_loading);
        loading = findViewById(R.id.loading);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String login = preferences.getString("login", "");
        if(!login.equals(""))
        {
            etSignInEmail.setText(login);
            etSignInPwd.setText(preferences.getString("password", ""));
        }

        //Sign in action
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email, password;
                email = etSignInEmail.getText().toString();
                password = etSignInPwd.getText().toString();

                //Starting Loading animation
                lottieAnimations.StartSignInLoading(loading, rlLoading);

                // create handler
                volleyRequestsHandler = new VolleyRequestsHandler(SignInActivity.this);
                // create JSON object with user's email and password
                usersDetails = new ObjectToJsonParser();
                JSONObject userDetailsJSONObject = usersDetails.userSignInJson(email, password);

                // temporary only, to avoid login service
//                startActivity(new Intent(SignInActivity.this, MyMenuPOS.class));

                volleyRequestsHandler.userSignIn(userDetailsJSONObject,
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(Object response) {
                                /*
                                * the login end point return Token
                                * we save the email, password and the returned token to shareprefs then we call
                                * user_info endpoint to retrieve wallet,email,name,lastname and type parameters
                                */
                                //User: test@rep.wales
                                //Pass: repcardiff

                                final String token = (String) response;
                                User u = new User();
                                u.setToken(token);
                                u.setEmail(email);
                                u.setPassword(password);
                                User.setCurrentUserAfterLogin(u, SignInActivity.this);
                                //user_info endpoint
                                new VolleyRequestsHandler(SignInActivity.this).getUserInfo(token, new VolleyCallback() {
                                    @Override
                                    public void onSuccess(Object respons) {

                                        lottieAnimations.StoppingSignInLoading(loading, rlLoading);
                                        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                        editor = preferences.edit();
                                        editor.putString("login", email);
                                        editor.putString("password", password);
                                        editor.apply();
                                        startActivity(new Intent(SignInActivity.this, MyMenuPOS.class));
                                    }

                                    @Override
                                    public void onError(Object error) {
                                        lottieAnimations.StoppingSignInLoading(loading, rlLoading);
                                    }
                                });

                            }

                            @Override
                            public void onError(Object error) {

                                Toast.makeText(SignInActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                lottieAnimations.StoppingSignInLoading(loading, rlLoading);
                            }
                        });

            }
        });

    }

    // hide UI action bar and make the app fullscreen
    public void makeFullscreen() {
        getSupportActionBar().hide();
        // API 19 (Kit Kat)
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else {
            if (Build.VERSION.SDK_INT > 10) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    // splashscreen
    public void splashScreen(int splashScreenDurationInMiliSeconds) {
        RelativeLayout secondLogo = (RelativeLayout) findViewById(R.id.rl_sign_in);
        ObjectAnimator signInLayout = ObjectAnimator.ofFloat(secondLogo, "alpha", 0f, 1f);
        signInLayout.setDuration(splashScreenDurationInMiliSeconds);
        signInLayout.start();
    }
}
