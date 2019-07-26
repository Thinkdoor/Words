package com.example.administrator.words.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.words.Explain;
import com.example.administrator.words.R;
import com.example.administrator.words.ResetUsername;
import com.example.administrator.words.Result;
import com.example.administrator.words.allWordsBase;

/**
 * Created by Administrator on 2019/4/27.
 */

public class FragmentSelf extends Fragment {

    TextView textView_explain,textView_resetname,textView_allword,textView_result ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self,null);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_explain = view.findViewById(R.id.self_tv_explain);
        textView_resetname = view.findViewById(R.id.self_tv_resetname);

        textView_allword = view.findViewById(R.id.self_tv_allwords);
        textView_result = view.findViewById(R.id.self_tv_result);

        //点击软件说明
        textView_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Explain.class);
                startActivity(intent);
            }
        });

        //点击修改用户名
        textView_resetname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResetUsername.class);
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
        });

        //点击单词战况
        textView_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Result.class);
                startActivity(intent);
            }
        });
    }
}
