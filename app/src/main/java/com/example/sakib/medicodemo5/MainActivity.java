package com.example.sakib.medicodemo5;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.sakib.medicodemo5.model.AdmissionInfo;
import com.example.sakib.medicodemo5.model.Branches;
import com.example.sakib.medicodemo5.model.BuilderManager;
import com.example.sakib.medicodemo5.model.ProfileLogin;
import com.example.sakib.medicodemo5.model.QuestionItem;
import com.example.sakib.medicodemo5.model.prospectusView;
import com.example.sakib.medicodemo5.model.service_plan;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private TextView mTextMessage;
    private SliderLayout mDemoSlider;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scrollView);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

//        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/SolaimanLipi.ttf");
//        textView.setTypeface(myCustomFont);

        setSliderImage();
        setBoomManu();
        setLinkBoomMenu();
    }

    private void setSliderImage() {

        HashMap<String, Integer> url_maps = new HashMap<String, Integer>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "https://i.ytimg.com/vi/QxZVcpIzyrM/maxresdefault.jpg");

        url_maps.put("Prize Giving", R.drawable.slider_one);
        url_maps.put("Admission Date", R.drawable.slider_two);
        url_maps.put("Medico Coaching", R.drawable.medico_splash_image);
        url_maps.put("Bio Star 2017", R.drawable.slide_four);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    private void setBoomManu() {

        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_3);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            if (i == 0) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(R.drawable.profileicon).normalText("Profile").listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
//                                showAwesomeDialog("Profile", "There will be a description on how to add brand properties and what fields need to be filled." +
//                                        "Which fields are so important, what will be the impact of the inputs will be described here then user can have a prior knowledge before giving the entries.");
                                Intent intent = new Intent(MainActivity.this, ProfileLogin.class);
                                intent.putExtra("key","ProfileLogin");
                                startActivity(intent);
                                MainActivity.this.finish();

                            }
                        });
                bmb.addBuilder(builder);
            } else if (i == 1) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(R.drawable.examrecordicon).normalText("Exam Record").listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                //showAwesomeDialog("Exam Record", "Exam Record Will Available soon!!");
                                Intent intent = new Intent(MainActivity.this, ProfileLogin.class);
                                intent.putExtra("key","ExamRecord");
                                startActivity(intent);
                                MainActivity.this.finish();
                            }
                        });
                bmb.addBuilder(builder);
            } else if (i == 2) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(R.drawable.helplineicon).normalText("Help Line").listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                showAwesomeDialog("Help Line", "Help Line Will Available soon!!");
                            }
                        });
                bmb.addBuilder(builder);
            } else if (i == 3) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(R.drawable.faqicon).normalText("FAQ").listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                showAwesomeDialog("FAQ", "FAQ will available soon!!");
                            }
                        });
                bmb.addBuilder(builder);
            } else if (i == 4) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(R.drawable.notificationicon).normalText("Notification").listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
//                                //showAwesomeDialog("Notification", "Description of how to use the app, what are the features and how a user will be benefitted from this app.");
//
//
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                DatabaseReference myRef = database.getReference("message");
//                                //myRef.setValue("Hello NSU");
//
//                                // Read from the database
//                                myRef.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        // This method is called once with the initial value and again
//                                        // whenever data at this location is updated.
//                                        String value = dataSnapshot.getValue(String.class);
//                                        //Log.d(TAG, "Value is: " + value);
//
//
//                                        showAwesomeDialog("Notification", value);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError error) {
//                                        // Failed to read value
//                                        Log.w("messege", "Failed to read value.", error.toException());
//                                    }
//                                });

                            }
                        });
                bmb.addBuilder(builder);
            }
        }

    }

    private void showAwesomeDialog(final String tag, String description) {
        new AwesomeInfoDialog(this)
                .setTitle(tag)
                .setMessage(description)
                .setColoredCircle(R.color.colorPrimary)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText("OK")
                .setPositiveButtonbackgroundColor(R.color.colorPrimary)
                .setPositiveButtonTextColor(R.color.white)
                /*.setNegativeButtonText(getString(R.string.dialog_no_button))
                .setNegativeButtonbackgroundColor(R.color.dialogInfoBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)*/
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        if(tag=="FAQ"){
                            //Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                            //startActivity(intent);
                            Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNeutralButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                })
                .show();
    }

    private void setLinkBoomMenu() {
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View actionBar = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) actionBar.findViewById(R.id.title_text);
        mTitleTextView.setText("Medico Coaching");
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0,0);

        final BoomMenuButton rightBmb = (BoomMenuButton) actionBar.findViewById(R.id.action_bar_right_bmb);

        rightBmb.setButtonEnum(ButtonEnum.Ham);
        rightBmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        rightBmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);

        rightBmb.addBuilder(BuilderManager.getHamButtonBuilder("Facebook", "Medico Facebook Page Link"));
        rightBmb.addBuilder(BuilderManager.getHamButtonBuilder("Youtube", "Medico Youtube Channel Link")
                .normalImageRes(R.drawable.youtubeicon));
        rightBmb.addBuilder(BuilderManager.getHamButtonBuilder("Medico Website", "Medico Website Link")
                .normalColorRes(R.color.colorPrimary));
        rightBmb.addBuilder(BuilderManager.getHamButtonBuilder("Tutorial", "Medico Tutorial Link"));

        rightBmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                super.onClicked(index, boomButton);
                HamButton.Builder builder = (HamButton.Builder) rightBmb.getBuilder(index);
                if (index == 0)
                {
                    Toast.makeText(MainActivity.this,"FaceBook",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/medicocoaching/"));
                    startActivity(intent);
                }
                else if (index == 1)
                {
                    Toast.makeText(MainActivity.this,"Youtube",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/"));
                    startActivity(intent);
                }
                else if (index == 2)
                {
                    Toast.makeText(MainActivity.this,"Medico Website",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.medicocoaching.com/"));
                    startActivity(intent);
                }
                else if (index == 3)
                {
                    Toast.makeText(MainActivity.this,"Tutorial will available soon!!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void Prospectus(View view) {

        //showAwesomeDialog("Prospectus", "Prospectus Will Available soon!!");
        Intent intent = new Intent(MainActivity.this, prospectusView.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void servicePlan(View view) {

        //showAwesomeDialog("Service Plan", "Service Plan Will Available soon!!");
        Intent intent = new Intent(MainActivity.this, service_plan.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void admission(View view) {

        Intent intent = new Intent(MainActivity.this, AdmissionInfo.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void branches(View view) {

        Intent intent = new Intent(MainActivity.this, Branches.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void onlineExam(View view) {

        Intent intent = new Intent(MainActivity.this, ProfileLogin.class);
        intent.putExtra("key","OnlineExam");
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void onlineSolution(View view) {
        showAwesomeDialog("Online Solution", "Online Solution Will Available soon!!");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void about(View view) {
        Toast.makeText(MainActivity.this,"About the App will be available",Toast.LENGTH_LONG).show();
    }

}
