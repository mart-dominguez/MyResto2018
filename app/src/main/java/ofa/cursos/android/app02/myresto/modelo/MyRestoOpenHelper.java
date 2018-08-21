package ofa.cursos.android.app02.myresto.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyRestoOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myresto.db";

    public MyRestoOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PRODUCTO ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NOMBRE TEXT, PRECIO REAL)");
        db.execSQL("CREATE TABLE PEDIDO_DETALLE ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ID_PRODUCTO INTEGER, CANTIDAD INTEGER)");
        db.execSQL("CREATE TABLE PEDIDO ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NOMBRE TEXT, ENVIO_DOMICILIO INTEGER," +
                "BEBIDA_XL INTEGER,PERMITE_CANCELAR INTEGER,INCLUYE_PROPINA INTEGER,ENVIAR_NOTIFICACION INTEGER,PAGO_AUTOMATICO INTEGER,ESTADO INTEGER)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // cargar los nomencladores
        if(newVersion==1){
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Coca Cola\", 19.36)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Cerveza\", 32.40)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Seven UP\", 29.00)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Vino\", 100.06)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Agua\", 15.28)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Soda\", 40.08)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Choripan\", 55.34)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Tallarines\", 76.85)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Ravioles\", 69.08)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Asado\", 99.55)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Milanesa\", 65.40)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Costeleta\", 87.65)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Fugazza\", 24.60)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Empanada\", 22.35)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Tarta\", 18.67)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Ensalada de Frutas\", 20.50)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Chocotorta\", 15.60)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Flan\", 54.20)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Mousse\", 31.60)");
            db.execSQL("INSERT INTO PRODUCTO ( NOMBRE,PRECIO) VALUES (\"Helado\", 28.40)");
        }
    }

}
