package com.example.administrator.words;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.words.fragments.FragmentInput;
import com.example.administrator.words.fragments.FragmentQuery;
import com.example.administrator.words.fragments.FragmentRecite_main;
import com.example.administrator.words.fragments.FragmentSelf;

public class FristActivity extends AppCompatActivity {
    TextView name;
    Button button_input,button_recite,button_self,button_review;
    FragmentInput fragmentInput;
    FragmentRecite_main fragmentRecite;
    FragmentSelf fragmentSelf;
    FragmentQuery fragmentReview;
    //读取数据
    private SharedPreferences sharedPreferences;
    //写入数据
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        button_input = (Button) findViewById(R.id.frist_btn_input);
        button_recite = (Button) findViewById(R.id.frist_btn_recite);
        button_self = (Button) findViewById(R.id.frist_btn_self);
        button_review = (Button) findViewById(R.id.frist_btn_review);

        name = (TextView) findViewById(R.id.name);

        fragmentInput = new FragmentInput();
        fragmentRecite = new FragmentRecite_main();
        fragmentSelf = new FragmentSelf();
        fragmentReview = new FragmentQuery();


        //实例化sharedPreferences，参数：文件名称，模式（通常使用PRIVATE）
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);

        //获取ID
        name.setText(sharedPreferences.getString("user",""));

        //点击录入按钮
       button_input.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getFragmentManager().beginTransaction().replace(R.id.first_fl,fragmentInput).commitAllowingStateLoss();
           }
       });

        //点击背诵按钮
        button_recite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.first_fl,fragmentRecite).commitAllowingStateLoss();

            }
        });

        //点击个人按钮
        button_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.first_fl,fragmentSelf).commitAllowingStateLoss();
            }
        });

        //点击复习按钮
        button_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.first_fl,fragmentReview).commitAllowingStateLoss();
            }
        });

        //默认录入界面
        getFragmentManager().beginTransaction().add(R.id.first_fl,fragmentInput).commitAllowingStateLoss();
    }

    @Override
    protected void onRestart() {
        //获取ID
        name.setText(sharedPreferences.getString("user",""));
        super.onRestart();
    }
}
