package com.example.administrator.words;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.words.database.DBResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result extends AppCompatActivity {

    DBResult dbOpenHelper;
    ListView listView;
    List<Map<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        dbOpenHelper = new DBResult(Result.this,"tb_result",null,1);

        ArrayList<Result_item> words = getWords();
        listView = (ListView) findViewById(R.id.list);
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0;i < words.size() ;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",words.get(i).id+".");
            map.put("right", words.get(i).right);
            map.put("wrong", words.get(i).wrong);
            map.put("delete", words.get(i).delete);
            list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(Result.this,list, R.layout.list_item,
                new String[]{"id","right","wrong","delete"},new int[]{R.id.id,R.id.word,R.id.translate,R.id.delete});

        listView.setAdapter(simpleAdapter);

    }

    private ArrayList<Result_item> getWords(){
        ArrayList<Result_item> words = new ArrayList<>();
        Cursor cursor = dbOpenHelper.getReadableDatabase().query("tb_result",null,null,null,null,null,null);
        int i = 1;
        while(cursor.moveToNext()){
            Result_item word = new Result_item();
            //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
            word.id = i;
            word.right = cursor.getString(cursor.getColumnIndex("right"));
            word.wrong = cursor.getString(cursor.getColumnIndex("wrong"));
            word.delete = cursor.getString(cursor.getColumnIndex("remove"));
            words.add(word);
            i++;
        }
        return words;
    }
}
