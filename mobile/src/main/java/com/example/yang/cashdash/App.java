package com.example.yang.cashdash;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class App extends Application {

   @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this, "KeSIeybm7QVaTSxFmYrQ2UEaCUJPSdyrWjeaZqHWq", "2j5rM29tTCLHb01lXxqUo64pWOeLeFT4l3b5BUI");
              ///*  .applicationId("KeSIeybm7QVaTSxFmYrQ2UEaCUJPSdyrWjeaZqHWq"),
              //  .clientKey(\"2j5rM29tTCLHb01lXxqUo64pWOeLeFT4l3b5BUI"),
              //  .server("cap-cashdash.herokuapp.com/parse")
       ParseObject testObject = new ParseObject("TestObject");
       testObject.put("foo", "bar");
       testObject.saveInBackground();
   }

}
