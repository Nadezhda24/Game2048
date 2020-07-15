package com.example.a2048;

import android.widget.ImageView;

 class Map {

    Cell [][] map = new Cell[5][5];
    ImageView [][] Im = new ImageView[5][5];
    int Count; // количество очков
    int CountBlock; //количество пустых блоков
    int level;


    public Map(ImageView [][] Im){
        Count =0;
        level = 1;
        for (int i =0; i < 5; i++){
            for(int j =0 ; j< 5; j ++) {

                 map[i][j] = new Cell();
                 this.Im[i][j]= Im[i][j];
            }
        }
    }

     public int GetCount(){return Count;}
     public int CountBlock(){return CountBlock;}
     public int GetLevel(){return level;}
     public Cell GetCell(int i, int j){return map[i][j];}

     public void NextLevel(){
        for (int i =0; i < 5; i++){
            for(int j =0 ; j< 5; j ++) {
                map[i][j] = new Cell();
            }
        }
        level++;
        int n = 4;
        switch (level){
            case 2:
                map[n/2][n/2].SetValue(-1);
                map[n/2][n/2].SetStatus(statusCell.block);
                break;
            case 3:
                map[0][n/2].SetValue(-1);
                map[0][n/2].SetStatus(statusCell.block);
                map[n/2][0].SetValue(-1);
                map[n/2][0].SetStatus(statusCell.block);
                map[n][n/2].SetValue(-1);
                map[n][n/2].SetStatus(statusCell.block);
                map[n/2][n].SetValue(-1);
                map[n/2][n].SetStatus(statusCell.block);
                break;
            case 4:
                for(int k=1; k<4; k++){
                    map[n/2][k].SetValue(-1);
                    map[n/2][k].SetStatus(statusCell.block);
                }
                break;
            case 5:
                map[n/2][n/2].SetValue(-1);
                map[n/2][n/2].SetStatus(statusCell.block);
                map[0][n/2].SetValue(-1);
                map[0][n/2].SetStatus(statusCell.block);
                map[n/2][0].SetValue(-1);
                map[n/2][0].SetStatus(statusCell.block);
                map[n][n/2].SetValue(-1);
                map[n][n/2].SetStatus(statusCell.block);
                map[n/2][n].SetValue(-1);
                map[n/2][n].SetStatus(statusCell.block);
                break;
        }
     }

     //Проверка на начилие пустых блоков
     public boolean FullMap(){

         CountBlock = 0;
        for (int i=0; i< 5; i++){
            for(int j=0; j<5; j++){
                if (map[i][j].GetValue() == 0) CountBlock++;
                if(map[i][j].GetValue() == 2048) CountBlock = -1;
            }
        }
        if(CountBlock > 0) return false;
        else return true;
     }

     public void GenerationNewCell(){
        int newX ;
        int newY ;
        if(!FullMap()) {
            do {
                newX = 0 + (int) (Math.random() * 5);
                newY = 0 + (int) (Math.random() * 5);
            } while (this.map[newX][newY].GetStatus() != statusCell.empty);

        map[newX][newY].SetStatus(statusCell.employed);
        map[newX][newY].SetValue(2);

        }

    }

     public  void SwapRight(){
        for(int k=0; k<5; k++){
            for (int i=0; i<5;i++) {
                for (int j = 3; j >= 0; j--) {
                    if(map[i][j].GetStatus() != statusCell.block) {
                        if (map[i][j + 1].GetStatus() == statusCell.empty) {
                            map[i][j + 1] = map[i][j];
                            map[i][j] = new Cell();
                        }
                        if (j == 0 && map[i][0].GetStatus() == statusCell.empty)
                            map[i][j] = new Cell();

                        if (map[i][j].GetValue() == map[i][j + 1].GetValue() && map[i][j].GetValue() != 0) {
                            map[i][j + 1].SetValue(map[i][j].GetValue() * 2);
                            Count += map[i][j + 1].GetValue();
                            map[i][j] = new Cell();
                        }
                    }
                }
            }
        }
    }

    public  void  SwapLeft(){
        for(int k=0; k<5; k++){
            for (int i=0; i<5;i++){
                for (int j=1; j< 5; j++){
                    if(map[i][j].GetStatus() != statusCell.block) {
                        if (map[i][j - 1].GetStatus() == statusCell.empty) {
                            map[i][j - 1] = map[i][j];
                            map[i][j] = new Cell();
                        }
                        if (j == 4 && map[i][4].GetStatus() == statusCell.empty)
                            map[i][j] = new Cell();

                        if (map[i][j].GetValue() == map[i][j - 1].GetValue() && map[i][j].GetValue() != 0) {
                            map[i][j - 1].SetValue(map[i][j].GetValue() * 2);
                            Count += map[i][j - 1].GetValue();
                            map[i][j] = new Cell();
                        }
                    }
                }
            }
        }
    }

    public  void SwapDown(){
        for(int k=0; k<5; k++){
            for(int j =0; j<5; j++) {
                for (int i = 1; i < 5; i++) {
                    if(map[i][j].GetStatus() != statusCell.block) {
                        if (map[i - 1][j].GetStatus() == statusCell.empty) {
                            map[i - 1][j] = map[i][j];
                            map[i][j] = new Cell();
                        }
                        if (i == 4 && map[4][j].GetStatus() == statusCell.empty)
                            map[i][j] = new Cell();

                        if (map[i][j].GetValue() == map[i - 1][j].GetValue() && map[i][j].GetValue() != 0) {
                            map[i - 1][j].SetValue(map[i][j].GetValue() * 2);
                            Count += map[i - 1][j].GetValue();
                            map[i][j] = new Cell();
                        }
                    }
                }
            }
        }
    }

     public  void SwapUp(){
         for(int k=0; k<5; k++){
             for(int j =0; j<5; j++){
                 for(int i = 3; i>=0; i--){
                     if(map[i][j].GetStatus() != statusCell.block) {
                         if (map[i + 1][j].GetStatus() == statusCell.empty) {
                             map[i + 1][j] = map[i][j];

                             map[i][j] = new Cell();
                         }
                         if (i == 0 && map[0][j].GetStatus() == statusCell.empty)
                             map[i][j] = new Cell();

                         if (map[i][j].GetValue() == map[i + 1][j].GetValue() && map[i][j].GetValue() != 0) {
                             map[i + 1][j].SetValue(map[i][j].GetValue() * 2);
                             Count += map[i + 1][j].GetValue();
                             map[i][j] = new Cell();
                         }
                     }
                 }
             }
         }
     }



    public void Game( line line ){
        switch (line){
            case up:
                SwapUp();
                GenerationNewCell();
            break;
            case down:
                SwapDown();
                GenerationNewCell();
            break;
            case left:
                SwapLeft();
                GenerationNewCell();
            break;
            case right:
                SwapRight();
                GenerationNewCell();
                break;
        }

        Draw();
    }

     public  void Draw (){
         for (int i=0; i<5;i++){
             for (int j=0; j<5; j++){
                 switch ( map[i][j].GetValue()){
                     case -1: Im[i][j].setImageResource(R.drawable.block);
                     break;
                     case 0: Im[i][j].setImageResource(R.drawable.fon);
                     break;
                     case 2: Im[i][j].setImageResource(R.drawable.b2);
                     break;
                     case 4: Im[i][j].setImageResource(R.drawable.b4);
                     break;
                     case 8:  Im[i][j].setImageResource(R.drawable.b8);
                     break;
                     case 16:  Im[i][j].setImageResource(R.drawable.b16);
                     break;
                     case 32:  Im[i][j].setImageResource(R.drawable.b32);
                     break;
                     case 64: Im[i][j].setImageResource(R.drawable.b64);
                     break;
                     case 128:  Im[i][j].setImageResource(R.drawable.b128);
                     break;
                     case 256: Im[i][j].setImageResource(R.drawable.b256);
                     break;
                     case 512:  Im[i][j].setImageResource(R.drawable.b512);
                     break;
                     case 1024:  Im[i][j].setImageResource(R.drawable.b1024);
                     break;
                     case 2048:  Im[i][j].setImageResource(R.drawable.b2048);
                     break;

                 }

             }
         }
     }

 }
