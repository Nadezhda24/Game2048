package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static java.lang.Math.abs;

enum line {
    up, down, left, right, unknow
}

public class Game extends AppCompatActivity implements View.OnTouchListener {


    int x ,y , xStart=0, yStart=0,  xFinish =  0,yFinish =  0;;
    ImageView Im [][] = new ImageView[5][5];
    line line;
    Map map;
    int flag1, flag2, flag3 ;
    int status ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle arguments = getIntent().getExtras();
        TextView UserName = (TextView) findViewById(R.id.UserName);
        TextView Skill = (TextView) findViewById(R.id.Skill);
        UserName.setText(arguments.getString("name"));
        Skill.setText(arguments.getString("skill"));
         status = arguments.getInt("st");

        RelativeLayout relativeLayout = (RelativeLayout)  findViewById(R.id.R);
        relativeLayout.setOnTouchListener(this);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                int res = getResources().getIdentifier("imageView" + i + j, "id", getPackageName());
                Im [i][j] = (ImageView) findViewById(res);
            }
        }

        flag1 = 1; flag2 = 1; flag3 = 1;

        map = new Map(Im);
        map.GenerationNewCell();
        map.GenerationNewCell();
        map.Draw ();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

         for(int i=0; i< 5; i++)
             for (int j=0; j<5; j++) Im[i][j].setClickable(false);

        x = (int) event.getRawX();
        y = (int) event.getRawY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                xStart = x;
                yStart = y;

             case MotionEvent.ACTION_UP:
                xFinish =  (int) event.getRawX();
                yFinish =  (int) event.getRawY();
                break;
        }

        if ((xFinish-xStart)>0 && (abs(xFinish-xStart)>abs( yFinish - yStart ))) line =  com.example.a2048.line.right;
        else if ((xFinish-xStart)<0 && (abs(xFinish-xStart)>abs( yFinish - yStart ))) line =  com.example.a2048.line.left;
        else if ((yStart - yFinish)>0 && (abs(xFinish-xStart)<abs( yFinish - yStart ))) line =  com.example.a2048.line.down;
        else  if ((yStart - yFinish)<0 && (abs(xFinish-xStart)<abs( yFinish - yStart ))) line =  com.example.a2048.line.up;
        else line =  com.example.a2048.line.unknow;


        if(!map.FullMap()) {
            map.Game(line);
            TextView Result = (TextView) findViewById(R.id.Value);
            Result.setText( String.valueOf(map.GetCount()));

        }else{
            final Dialog dialog = new Dialog(Game.this);
            dialog.setContentView(R.layout.message);
            TextView Result = (TextView)  dialog.findViewById(R.id.Result);
            ImageView down =  dialog.findViewById(R.id.Down);
            boolean rating = false;
            if (map.GetLevel() <= 5){
                if (map.CountBlock() == -1) {
                    down.setImageResource(R.drawable.win);
                    if (map.GetLevel() == 5){
                        Result.setText("Ваш результат: " + String.valueOf(map.GetCount()));
                        rating = true;
                        map = new Map(Im);
                    }
                    else{
                        Result.setText("Вы перешли на новый уровень.");
                        map.NextLevel();
                    }
                }
                else {
                    down.setImageResource(R.drawable.lose);
                    Result.setText("Ваш результат: " + String.valueOf(map.GetCount()));
                    rating = true;
                    map = new Map(Im);
                }

            map.GenerationNewCell();
            Button button = (Button) dialog.findViewById(R.id.Ok);
            Result.setTypeface(ResourcesCompat.getFont(this, R.font.font));
                final boolean finalRating = rating;
                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (finalRating && status == 1) {
                        final Dialog dialog = new Dialog(Game.this);
                        dialog.setContentView(R.layout.rating);
                        Button button = (Button) dialog.findViewById(R.id.Ok);

                         final TextView Login1 = dialog.findViewById(R.id.Login1);
                         final TextView Skill1 = dialog.findViewById(R.id.Skill1);
                         final TextView Count1 = dialog.findViewById(R.id.Count1);

                         final TextView Login2 = dialog.findViewById(R.id.Login2);
                         final TextView Skill2 = dialog.findViewById(R.id.Skill2);
                         final TextView Count2 = dialog.findViewById(R.id.Count2);

                         final TextView Login3 = dialog.findViewById(R.id.Login3);
                         final TextView Skill3 = dialog.findViewById(R.id.Skill3);
                         final TextView Count3 = dialog.findViewById(R.id.Count3);


                        Thread thread = new Thread(new Runnable() { @Override public void run() {  try {

                            final Client client_obj = new Client();

                            client_obj.run( "[t]");

                            String Ans[] = client_obj.ansServer.split(" ");

                            Login1.setText(Ans[0]);
                            Skill1.setText(Ans[1]);
                            Count1.setText(Ans[2]);

                            Login2.setText(Ans[3]);
                            Skill2.setText(Ans[4]);
                            Count2.setText(Ans[5]);

                            Login3.setText(Ans[6]);
                            Skill3.setText(Ans[7]);
                            Count3.setText(Ans[8]);


                        } catch (IOException e) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Unable to connect. Server not running?", Toast.LENGTH_LONG);
                            toast.show();

                        } } }); thread.start();

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                dialog.dismiss();
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                }
            });
            dialog.setCancelable(false);
            dialog.show();
            }
        }

        return true;
    }

    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ValueDown:
                for(int i =0; i< 5; i++){
                    for (int j =0; j<5;j++) {
                        if(map.GetCell(i,j).GetStatus() == statusCell.employed) {
                            final int finalI = i;
                            final int finalJ = j;
                            Im[i][j].setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(final View v) {
                                    final Dialog dialog = new Dialog(Game.this);
                                    dialog.setContentView(R.layout.message);
                                    final TextView Value = (TextView)  dialog.findViewById(R.id.Result);
                                    Value.setTextSize(60);
                                    final Button button = (Button) dialog.findViewById(R.id.Ok);
                                    ImageView up =  dialog.findViewById(R.id.Up);
                                    up.setImageResource(R.drawable.upd);
                                    ImageView down =  dialog.findViewById(R.id.Down);
                                    down.setImageResource(R.drawable.downd);
                                    Value.setText(String.valueOf(map.GetCell(finalI, finalJ).GetValue()));
                                    final int maxValue = map.GetCell(finalI, finalJ).GetValue();
                                    up.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (maxValue > map.GetCell(finalI, finalJ).GetValue()) {
                                                map.GetCell(finalI, finalJ).SetValue(map.GetCell(finalI, finalJ).GetValue()*2);
                                                Value.setText(String.valueOf(map.GetCell(finalI, finalJ).GetValue()));
                                                map.GetCell(finalI, finalJ).ValueDown(map.GetCell(finalI, finalJ).GetValue());
                                            }
                                        }
                                    });

                                    down.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (2 < map.GetCell(finalI, finalJ).GetValue()) {
                                                map.GetCell(finalI, finalJ).SetValue(map.GetCell(finalI, finalJ).GetValue()/2);
                                                Value.setText(String.valueOf(map.GetCell(finalI, finalJ).GetValue()));
                                                map.GetCell(finalI, finalJ).ValueDown(map.GetCell(finalI, finalJ).GetValue());
                                            }
                                        }
                                    });

                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            map.Draw();
                                        }
                                    });
                                    dialog.setCancelable(false);
                                    dialog.show();

                                }
                            });
                        }
                }
            }

                break;
            case R.id.DeleteCell:
                for(int i =0; i< 5; i++){
                    for (int j =0; j<5;j++) {
                        final int finalI = i;
                        final int finalJ = j;
                        Im[i][j].setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                map.GetCell(finalI, finalJ).DeleteCell();
                                map.Draw();
                            }
                            });
                        }
                }

                break;
            case R.id.LineValueUp:
                    for(int i =0; i< 5; i++){
                        for (int j =0; j<5;j++) {
                            final int finalI = i;
                            Im[i][j].setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    for(int k=0; k<5; k++) map.GetCell(finalI, k).LineValuesUp();
                                    map.Draw();
                                }
                            });
                        }
                    }
                break;

        }


    }
}


