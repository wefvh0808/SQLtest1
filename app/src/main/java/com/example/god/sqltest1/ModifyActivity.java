package com.example.god.sqltest1;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by GOD on 2016/11/16.
 */

public class ModifyActivity extends Activity implements View.OnClickListener {
    DBAccess access;
    EditText dateET, timeET, titleET;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifyactivity);
        //找出按鈕物件與設定事件監聽器
        dateET=(EditText)findViewById(R.id.editText1);
        timeET=(EditText)findViewById(R.id.editText2);
        titleET=(EditText)findViewById(R.id.editText3);
        Button btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
        btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(this);
        //建立自訂資料庫操作(DBAccess)物件
        access=new DBAccess(this, "schedule", null, 1);
        Bundle bdl=getIntent().getExtras();
        id=bdl.getString("id","0");
        //資料查詢，條件為_id等於上一個活動視窗傳遞過來的資料
        Cursor c=access.getData(DBAccess.ID_FIELD+" ="+id, null);
        c.moveToFirst();
        //將資料呈現於EditText上
	    /*c.getString(1), c.getString(2), c.getString(3)分別取出Cursor物件中第二個(date), 第三個(time), 第四個(title)欄位值*/
        dateET.setText(c.getString(1));
        timeET.setText(c.getString(2));
        titleET.setText(c.getString(3));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1://資料表中的資料修改
                access.update(dateET.getText()+"", timeET.getText()+"",
                        titleET.getText()+"",DBAccess.ID_FIELD+" ="+id);
                break;
            case R.id.button2://資料表中的資料刪除
                access.delete(id);
                break;
        }
        finish();//關閉修改刪除活動視窗
    }
}