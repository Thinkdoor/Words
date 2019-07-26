package com.example.administrator.words.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.words.R;
import com.example.administrator.words.database.DBAllWords;
import com.example.administrator.words.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/4/26.
 */

public class FragmentQuery extends Fragment implements View.OnClickListener{
    DBAllWords dballWords;
    EditText tv_word,tv_translate;
    Button button_renshi,button_burenshi;
    ArrayList<Word> words;
    int i = 0;   //背诵数量
    int a = 1;   //删除数量
    //创建一个视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dballWords = new DBAllWords(getActivity(),"tb_words",null,1);

        View view = inflater.inflate(R.layout.fragment_review,null);
        return view;
    }

    //创建时显示
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_word = view.findViewById(R.id.query_et_word);
        tv_translate = view.findViewById(R.id.query_et_translate);
        button_renshi = view.findViewById(R.id.query_btn_translate);
        button_burenshi = view.findViewById(R.id.query_btn_word);
        setListener();

    }

    //设置按钮监听
    private void setListener()
    {
        button_burenshi.setOnClickListener(this);
        button_renshi.setOnClickListener(this);

    }

    private String getWords(){
        String translate = tv_word.getText().toString();
        String word = null;
        Cursor cursor = dballWords.getReadableDatabase().query("tb_words",null,"translate = ? ", new String[]{translate},null,null,null);
        if (cursor.getCount()==0)
            ToastUtil.showMsg(getActivity(),"没有该单词！");
        else {
            while (cursor.moveToNext()){
                Word word1 = new Word();
                //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
                word1.word = cursor.getString(cursor.getColumnIndex("word"));
                word = word1.word;
            }
        }
        return word;
    }

    private String getTranslate(){
        String word = tv_translate.getText().toString();
        String translate = null;
        Log.d("1111", "onClick: ");
        Cursor cursor = dballWords.getReadableDatabase().query("tb_words",null,"word = ?", new String[]{word},null,null,null);
        Log.d("1111", "onClick: ");
        if (cursor.getCount()==0)
            ToastUtil.showMsg(getActivity(),"没有该单词！");
        else {
            while (cursor.moveToNext()){
                Word word1 = new Word();
                //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
                word1.translate = cursor.getString(cursor.getColumnIndex("translate"));
                translate = word1.translate;
            }
        }
        return translate;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_btn_translate: {      //点击汉译英，查找英文
                tv_translate.setText(getWords());
                break;
            }
            case R.id.query_btn_word:{    //点击英译汉，查找汉语
                Log.d("1111", "onClick: ");
                tv_word.setText(getTranslate());
                break;
            }
        }
    }
    class Word{
        String word;
        String translate;
    }
}
