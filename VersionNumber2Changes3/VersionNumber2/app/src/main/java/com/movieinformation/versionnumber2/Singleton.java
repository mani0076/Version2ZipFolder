package com.movieinformation.versionnumber2;

/*
   Singleton class for accessing shared preferences and general functions.
*/
public class Singleton {
    private static Singleton instance;
    public SharedPrefs sharedPrefs;
    public GeneralFunctions generalFunctions;

    /*
      Private constructor to prevent instantiation from outside the class.
   */
    private Singleton() {
        sharedPrefs = new SharedPrefs();
        generalFunctions = new GeneralFunctions();
    }

    /*
       Retrieves the singleton instance of the class.
       @return The singleton instance.
    */
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}