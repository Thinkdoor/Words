package com.example.administrator.words.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.words.R;
import com.example.administrator.words.database.DBAllWords;
import com.example.administrator.words.database.DBResult;
import com.example.administrator.words.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/4/26.
 */

public class FragmentRecite extends Fragment implements View.OnClickListener{
    DBAllWords dbOpenHelper;
    DBResult dbResult;

    TextView tv_word;
    EditText tv_translate;
    Button button_renshi,button_burenshi;
    ArrayList<Word> words;
    int i = 0;   //背诵数量
    int a = 1;   //删除数量
    int right = 0; //正确数量
    int wrong = 0; //错误数量
    int delete = 0; //删除数量

    //创建一个视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbOpenHelper = new DBAllWords(getActivity(),"tb_words",null,1);
        dbResult = new DBResult(getActivity(),"tb_result",null,1);

        View view = inflater.inflate(R.layout.fragment_recite,null);
        return view;
    }

    //创建时显示
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        words = getWords();
        String word = null;
        try {
            word = words.get(i).word;
        }
        catch (Exception e){
            ToastUtil.showMsg(getActivity(),"没有单词了");
        }
        tv_word = view.findViewById(R.id.recite_tv_word);
        tv_translate = view.findViewById(R.id.recite_tv_translate);
        button_renshi = view.findViewById(R.id.recite_btn_renshi);
        button_burenshi = view.findViewById(R.id.recite_btn_burenshi);
        setListener();

        tv_word.setText(word);
    }

    private void setListener()
    {
        button_burenshi.setOnClickListener(this);
        button_renshi.setOnClickListener(this);
    }

    private ArrayList<Word> getWords(){
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = dbOpenHelper.getReadableDatabase().query("tb_words",null,null,null,null,null,null);
        int count = cursor.getCount();
        int i = 0;
        while(i<=9){

            Word word = new Word();
            //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值

            int number = (int) (Math.random()*count);
            cursor.moveToPosition(number);  //移动到number这一行

            word.word = cursor.getString(cursor.getColumnIndex("word"));//取出word列的值
            word.translate = cursor.getString(cursor.getColumnIndex("translate"));
            words.add(word);

            i++;
        }
        return words;
    }
    public void ifBackToFirst(){
        dbResult.writeData(dbResult.getReadableDatabase(),"正确："+right,"错误："+wrong,"删除："+delete);
        wrong = 0;
        delete = 0;
        right = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("已经背到最后了").setMessage("是否再来一组？")
                .setIcon(R.drawable.icon_image)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        words = getWords();
                        i = 0;
                        try{
                            if(words.isEmpty()){
                                tv_word.setText("");
                                tv_translate.setText("");
                                ToastUtil.showMsg(getActivity(),"没有单词了");
                            }else{
                                tv_word.setText(words.get(i).word);
                                tv_translate.setText("");
                            }
                        }catch (Exception e){
                            ToastUtil.showMsg(getActivity(),"没有单词了");
                        }
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtil.showMsg(getActivity(), "已背到完一组啦！");
                tv_translate.setText("");
                tv_word.setText("");
            }
        }).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recite_btn_renshi: {   //点击确定，判断是否书写正确
                try {
                    String word = tv_word.getText().toString();
                    String translate = tv_translate.getText().toString();
                    Log.d("1111", "onClick: ");
                    if (words.get(i).translate.equals(translate)) {
                        ToastUtil.showMsg(getActivity(), "回答正确！");
                        right++;
                    }
                    else {
                        ToastUtil.showMsg(getActivity(), "回答错误！");
                        wrong++;
                    }
                    i++;
                    tv_word.setText(words.get(i).word);
                    tv_translate.setText("");
                } catch (Exception e) {
                    ifBackToFirst();
                }
                break;
            }
            case R.id.recite_btn_burenshi: {                                                    //点击认识，删除单词，跳入下一个单词
                try{
                    if (words.size() == 0)//如果单词库为空
                    {
                        ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                    } else if (words.size() == i + 1) { //如果单词到最后一个
                        words.remove(i);
                        delete++;
                        ifBackToFirst();
                    } else if (words.size() == i) {  //删掉最后一个后的i处理
                        ifBackToFirst();
                    } else {    //如果单词不是最后一个
                        words.remove(i);
                        delete++;
                        ToastUtil.showMsg(getActivity(), "删除成功");
                        i++;
                        i = i - a;
                        tv_word.setText(words.get(i).word);
                        tv_translate.setText("");
                    }
                    dbOpenHelper.onUpgrade(dbOpenHelper.getReadableDatabase(), 0, 0);   //更新数据库
                    for (Word word : words) {
                        dbOpenHelper.writeData(dbOpenHelper.getReadableDatabase(), word.word, word.translate);
                    }
                }catch (Exception e){
                    ifBackToFirst();
                }
                break;
        }


                /*if (words.size() == 0)//如果单词库为空
                {
                    ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                } else if (words.size() == i+1) { //如果单词到最后一个
                    dbReviewWords.writeData(dbReviewWords.getReadableDatabase(),words.get(i).word,words.get(i).translate);
                    words.remove(i);
                    ifBackToFirst();
                } else if(words.size() == i) {  //删掉最后一个后的i处理
                    ifBackToFirst();
                }else{    //如果单词不是最后一个
                        dbReviewWords.writeData(dbReviewWords.getReadableDatabase(),words.get(i).word,words.get(i).translate);
                        words.remove(i);
                        ToastUtil.showMsg(getActivity(), "删除成功");
                        i++;
                        i = i - a;
                        tv_word.setText(words.get(i).word);
                        tv_translate.setText("");
                    }
                dbOpenHelper.onUpgrade(dbOpenHelper.getReadableDatabase(), 0, 0);   //更新数据库
                for (Word word : words) {
                    dbOpenHelper.writeData(dbOpenHelper.getReadableDatabase(), word.word, word.translate);
                }
                break;
            }*/
        }
    }
    class Word{
        String word;
        String translate;
    }
}
