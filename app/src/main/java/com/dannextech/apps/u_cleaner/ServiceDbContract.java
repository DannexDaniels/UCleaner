package com.dannextech.apps.u_cleaner;

import android.provider.BaseColumns;

public class ServiceDbContract {
    //To prevent someone from accidentally instanstiating this class we make the contructor private
    private ServiceDbContract(){}

    //inner class defines the table contents
    public static class Service implements BaseColumns {
        public static final String TABLE_NAME = "services";
        public static final String COL_SERVICE_NAME = "name";
        public static final String COL_SERVICE_PRICE = "price";
        public static final String COL_SERVICE_ID = "receipt";
        public static final String COL_SERVICE_DATE = "date";
    }
}
