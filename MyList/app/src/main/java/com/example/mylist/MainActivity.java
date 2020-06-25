package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Scanner; // import the Scanner class
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {
    //Inhalt der Liste
    class ChatMsg {
      int id = -1;
      String user = "";
      String msg = "";
      String tag = "default";

      public ChatMsg(int id, String user, String msg){
            this.id = id;
            this.user = user;
            this.msg = msg;
        }

      public ChatMsg(int id, String user, String msg, String tag){
          this.id = id;
          this.user = user;
          this.msg = msg;
          this.tag = tag;
      }

      public String toString(){
          return user + " : " + msg;
      }
    }

    String incomming=  "id : das kommt in die DB \nid : noch mehr in die \nid : ganz viel in die DB\nmarc : liij\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList <ChatMsg> allChats = new ArrayList <>();
        allChats.add(new ChatMsg( 1,  "id",  "oops"));
        allChats.add(new ChatMsg(2, "id", "Well Done"));
        allChats.add(new ChatMsg(3, "id", "Want more?"));
        allChats.add(new ChatMsg(4, "id", "Crazy stuff"));



        //adapter fuer inhalt  zur liste
        ArrayAdapter adapter = new ArrayAdapter<ChatMsg>(this,
                R.layout.support_simple_spinner_dropdown_item, allChats);



        final TextView textListe = findViewById(R.id.textTop);
       textListe.setText("headline");

        final ListView meineListe = findViewById(R.id.textListe);

        //meineListe.setOnItemClickListener(this);


        //verknuepfen inhalt mit adapter & adapter mit dem listView
        meineListe.setAdapter(adapter);



        meineListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> AdapterView, View view, int positionSelItem, long l) {

                ChatMsg selItem = (ChatMsg) meineListe.getItemAtPosition (positionSelItem);
                textListe.setText(selItem.toString());

            }
        });

        allChats.add(new ChatMsg( 1,  "id", "lalaland"));
        allChats.add(new ChatMsg( 1,  "id", "Brave New World"));
        textListe.setText("habe X items: " + meineListe.getAdapter().getCount());

        Scanner scan = new Scanner(incomming);
        scan.useDelimiter("\\n");

        while ( scan.hasNext() ) {

            String line = scan.next();

            //Scann um die einzelne line an hand des ':' zu trennen
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(":");
            String u = lineScan.next();
            String m = lineScan.next();


            allChats.add ( new ChatMsg( 0, u.trim(), m.trim()));
        }

    }
}
