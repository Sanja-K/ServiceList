package com.example.servicelist;

import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;


import androidx.appcompat.view.menu.MenuView;

import com.google.android.material.tabs.TabItem;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class RxValidationForm  {
    private static String TAG = "RxValidationForm ";
    EditText nameForm;
    EditText commentForm;
    static MenuItem item;

    Observable<Boolean> observableValid;

    public RxValidationForm ( EditText name, EditText comment){
        this.nameForm =name;
        this.commentForm = comment;
    }

    public RxValidationForm(MenuItem sendItem){
    this.item = sendItem;
        Log.i(TAG, "onCreateOptionsMenu: RxValidationForm "+ item);
    }

    public void init(){

        Observable<String> nameObservable = RxTextView.textChanges(nameForm)
                                            .skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return charSequence.toString();
            }
        });

        Observable<String> commentObservable = RxTextView.textChanges(commentForm).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                Log.i(TAG, "apply: " +charSequence);
                return charSequence.toString();
            }
        });

        observableValid = Observable.combineLatest(nameObservable, commentObservable, new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String s, String s2) throws Exception {
                return isValidForm(s,s2);
            }
        });

        observableValid.subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                updateButton( aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void updateButton( boolean valid){
    Log.i(TAG, "onCreateOptionsMenu: RxValidationForm updateButton "+ item);
        if(valid){
            item.setEnabled(true);
        }else{
            item.setEnabled(false);
        }
    }

    public boolean isValidForm(String name, String comment) {

        Pattern pattern = Pattern.compile(new String ("^[а-яА-Я\\s]*$"));
        Matcher matcher = pattern.matcher(name);

       // boolean validName = !name.isEmpty() && name.matches("[[а-яА-Я\\\\s]*$]");
        boolean validName = !name.isEmpty() && matcher.matches();
        Log.i(TAG, "isValidForm: " + " name " + name + " "+ !matcher.matches() );

        if (!validName) {
            nameForm.setError("Поле \"Имя\" обязательно для заполнения и не может содержать цифры," +
                    " символы и знаки подчёркивания");
        }

        boolean validComm = !comment.isEmpty();
        if (!validComm) {
            commentForm.setError("Поле \"Примечания\" является обязательным для заполнения");
        }
        return validName && validComm;
    }

    public void  onDisableMenuBtn (String name, String comment, MenuItem item) {
        Pattern pattern = Pattern.compile(new String ("^[а-яА-Я\\s]*$"));
        Matcher matcher = pattern.matcher(name);

        boolean validName = !name.isEmpty() && matcher.matches();
        boolean validComm = !comment.isEmpty();

        if(!(validName && validComm)){
            item.setEnabled(false);
        }
    }

}
