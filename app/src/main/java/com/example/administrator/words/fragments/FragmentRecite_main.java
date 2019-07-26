package com.example.administrator.words.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.words.R;

/**
 * Created by Administrator on 2019/6/16.
 */

public class FragmentRecite_main extends Fragment {

    TextView textView_wordsbase,textView_explain,textView_resetname,textView_reviewword,textView_allword ;
    Fragment fragment1,fragment2,
            fragment3,fragment4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_recite__main,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView_wordsbase = view.findViewById(R.id.recite_1);
        textView_explain = view.findViewById(R.id.recite_2);
        textView_resetname = view.findViewById(R.id.recite_3);
        textView_reviewword = view.findViewById(R.id.recite_4);

        fragment1 = new FragmentRecite();
        fragment2 = new FragmentRecite_2();
        //点击英语写译
        textView_wordsbase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.first_fl,fragment1).commitAllowingStateLoss();
            }
        });

        //点击翻译写英
        textView_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.first_fl,fragment2).commitAllowingStateLoss();
            }
        });

/*        //点击修改用户名
        textView_resetname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResetUsername.class);
                startActivity(intent);
            }
        });

        //点击复习单词库
        textView_reviewword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        //点击单词库
        textView_allword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), allWordsBase.class);
                startActivity(intent);
            }
        });*/
    }

}
