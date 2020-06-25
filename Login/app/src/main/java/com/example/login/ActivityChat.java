package com.example.login;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.Scanner;

import cz.msebera.android.httpclient.Header;



public class ActivityChat extends AppCompatActivity {
    TextView msg;
    final int sleepTime = 30000;  // = 30sec

    class ChatMsg {
        int id = -1;
        String user = "";
        String msg = "";
        String tag = "default";

        public ChatMsg(int id, String user, String msg) {
            this.id = id;
            this.user = user;
            this.msg = msg;
        }

        public ChatMsg(int id, String user, String msg, String tag) {
            this.id = id;
            this.user = user;
            this.msg = msg;
            this.tag = tag;
        }


        public String toString(){
            return user + " : " + msg;
        }


    }
    ArrayList<ChatMsg> allChats = new ArrayList <>();


    ListView meineListe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter adapter = new ArrayAdapter<ChatMsg>(this,
                R.layout.support_simple_spinner_dropdown_item, allChats);

        setContentView(R.layout.activity_chat);
         meineListe = (ListView) findViewById(R.id.list_View);


        //verknuepfen inhalt mit adapter & adapter mit dem listView
        meineListe.setAdapter(adapter);



        msg = (TextView)findViewById(R.id.editText2);

        Button clickButton = (Button) findViewById(R.id.button_go);
        clickButton.setOnClickListener( new View.OnClickListener() {

            // action was passieren soll
            @Override
            public void onClick(View view) {
                push("" +msg.getText());
                msg.setText("");

                fetch();

            }
        });

        //alle 30 sec.
        Thread th = new Thread(new Runnable() {
            public void run() {
                while ( true ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fetch();
                        }
                    });
                    try {
                        Thread.sleep(sleepTime);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();



        //chat.setText("eins\n zwei");

    }




    public void putMsgList(String msg) {
        allChats.clear();


        Scanner scan = new Scanner(msg);
        scan.useDelimiter("\\n");


        while (scan.hasNext()) {

            String line = scan.next();

            //Scann um die einzelne line an hand des ':' zu trennen
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(":");
            String u = lineScan.next();
            String m = lineScan.next();


            allChats.add(new ChatMsg(0, u.trim(), m.trim()));

        }
        meineListe.setSelection(meineListe.getAdapter().getCount() - 1);


}



    public void fetch (){
    AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("u", MainActivity.userName);
        params.put("pass", MainActivity.userPass);
        params.put("tag", MainActivity.userTag);
        params.put("todo", "show");

    client.post("https://fbb.dasgoldeneclo.de/~marco/chat.php", params, new TextHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

           // chat.setText("Nicht geklapt");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {

          putMsgList(responseString);
        }


    });


    }  //fetch End


    public void push(String m){
        AsyncHttpClient client = new AsyncHttpClient();


        RequestParams params = new RequestParams();
        params.put("u", MainActivity.userName);
        params.put("pass", MainActivity.userPass);
        params.put("tag", MainActivity.userTag);
        params.put("todo", "insert");
        params.put("msg", m);



        client.post("https://fbb.dasgoldeneclo.de/~marco/chat.php",params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                //chat.setText("Nicht geklapt");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //chat.setText(responseString);
            }

        });

    }



}
