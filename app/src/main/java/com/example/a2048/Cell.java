package com.example.a2048;

import android.widget.ImageView;

enum statusCell{
    empty, employed, block
}

 class Cell {
    int value;
     statusCell status;

    public  Cell (){
        this.value = 0;
        this.status = statusCell.empty;
    }

    public int GetValue(){
        return  value;
    }

    public statusCell GetStatus(){ return status; }

    public void SetValue(int value){
        this.value = value;
    }

    public  void SetStatus(statusCell status){
        this.status = status;
    }

     //Функциональная возможность №1: уменьшение значения клетки
     public void ValueDown(int newValue){
         if(this.value > 0) this.value = newValue;
     }

     //функицональная возможность №2: удаление выбранной ячейки
     public  void DeleteCell(){
         if(this.value > 0){
         this.value = 0;
         this.status = statusCell.empty;}
     }

     //Функциональная возможность №3: увеличение значения клеток в ряду в 2 раза
     public void LineValuesUp(){
        if(this.value > 0) this.value = this.value * 2 ;
     }

}
