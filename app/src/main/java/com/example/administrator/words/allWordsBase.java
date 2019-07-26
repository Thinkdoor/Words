package com.example.administrator.words;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.words.database.DBAllWords;
import com.example.administrator.words.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class allWordsBase extends AppCompatActivity {

    DBAllWords allWords;
    ListView listView;
    List<Map<String,Object>> list;

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initview();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_all_base);
        allWords = new DBAllWords(allWordsBase.this,"tb_words",null,1);

        final ArrayList<Word> words = getWords();

        listView = (ListView) findViewById(R.id.list2);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = words.get(i).word;
                String translate = words.get(i).translate;
                ToastUtil.showMsg(allWordsBase.this,"已重新加入背诵单词库");
                return false;
            }
        });
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0;i < words.size() ;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",words.get(i).id+".");
            map.put("word", words.get(i).word);
            map.put("translate", words.get(i).translate);
            list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(allWordsBase.this,list, R.layout.list_item,
                new String[]{"id","word","translate"},new int[]{R.id.id,R.id.word,R.id.translate});

        listView.setAdapter(simpleAdapter);
    }
    private ArrayList<Word> getWords(){
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = allWords.getReadableDatabase().query("tb_words",null,null,null,null,null,null);
        int i = 1;
        while(cursor.moveToNext()){
            Word word = new Word();
            //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
            word.id = i;
            word.word = cursor.getString(cursor.getColumnIndex("word"));
            word.translate = cursor.getString(cursor.getColumnIndex("translate"));
            words.add(word);
            i++;
        }
        return words;
    }
}
