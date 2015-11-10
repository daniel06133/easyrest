package BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.ContentLoadingProgressBar;

public class DataBaseHelper extends SQLiteOpenHelper{

    static final String dbName = "dbEasyRest";
	
	static final String usersTable = "User";
	static final String colUsername = "username";
	static final String colPassword = "password";
	
	static final String menuXMesaTable = "MenuXMesa";
	static final String colNumeroMesa = "numeroMesa";
	static final String colNumeroMenu = "numeroMenu";
	static final String colEstado = "estado";
	
	static final String menuTable = "Menu";
	static final String colIdMenu = "idMenu";
	static final String colNombreMenu = "nombreMenu";
	static final String colUrlFotoLowQMenu = "urlFotoLowQMenu";
	static final String colUrlFotoHighQMenu = "urlFotoHighQMenu";
	static final String colDescripcionMenu = "descripcionMenu";
	static final String colPrecioMenu = "precioMenu";
	static final String colIdCategoriaMenu = "idCategoriaMenu";
	
	static final String categoriaTable = "Categoria";
	static final String colIdCategoria = "idCategoria";
	static final String colnombreCategoria = "nombreCategoria";

	
	
	public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, dbName, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + usersTable + " (" + colUsername
				+ " TEXT PRIMARY KEY , " + colPassword + " TEXT)");
		
		db.execSQL("CREATE TABLE " + categoriaTable + " (" + colIdCategoria
				+ " INTEGER PRIMARY KEY , " + colnombreCategoria + " TEXT)");

		
		db.execSQL("CREATE TABLE " + menuTable + " (" + colIdMenu
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colNombreMenu + " TEXT , "
				+ colUrlFotoLowQMenu + " TEXT , " + colUrlFotoHighQMenu + " TEXT , "
				+ colDescripcionMenu + " TEXT , " + colPrecioMenu + " NUMERIC , "
				+ colIdCategoriaMenu + " INTEGER , FOREIGN KEY (" + colIdCategoriaMenu
				+ ") REFERENCES " + categoriaTable + " (" + colIdCategoria + "));");
		
		
		db.execSQL("CREATE TABLE " + menuXMesaTable + " (" + colNumeroMesa
				+ " INTEGER, " + colNumeroMenu + " INTEGER, "
				+ colEstado + " INTEGER , PRIMARY KEY (" + colNumeroMesa + ", " + colNumeroMenu +"),"
				+ " FOREIGN KEY (" + colNumeroMenu
				+ ") REFERENCES " + menuTable + " (" + colIdMenu + "));");
		
		
		
		db.execSQL("CREATE TRIGGER fk_categ_menu " + " BEFORE INSERT "
				+ " ON " + menuTable +
				" FOR EACH ROW BEGIN" + " SELECT CASE WHEN ((SELECT "
				+ colIdCategoria + " FROM " + categoriaTable + " WHERE " + colIdCategoria
				+ "=new." + colIdCategoriaMenu + " ) IS NULL)"
				+ " THEN RAISE (ABORT,'Foreign Key Violation') END;" + "  END;");
		
		db.execSQL("CREATE TRIGGER fk_menuXMesa_Menu " + " BEFORE INSERT "
				+ " ON " + menuXMesaTable +
				" FOR EACH ROW BEGIN" + " SELECT CASE WHEN ((SELECT "
				+ colIdMenu + " FROM " + menuTable + " WHERE " + colIdMenu
				+ "=new." + colNumeroMenu + " ) IS NULL)"
				+ " THEN RAISE (ABORT,'Foreign Key Violation') END;" + "  END;");
		
		
		insertarCategorias(db);
		insertarUsuario(db);
		
		/*
		db.execSQL("CREATE VIEW " + viewEmps + " AS SELECT " + employeeTable
				+ "." + colID + " AS _id," + " " + employeeTable + "."
				+ colName + "," + " " + employeeTable + "." + colAge + ","
				+ " " + deptTable + "." + colDeptName + "" + " FROM "
				+ employeeTable + " JOIN " + deptTable + " ON " + employeeTable
				+ "." + colDept + " =" + deptTable + "." + colDeptID);
		// Inserts pre-defined departments
		
		
		db.execSQL("CREATE TABLE " + carTable + " (" + colcarID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT , " + colCarName + " TEXT ," 
				+ colMarca + " TEXT )");
		*/
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	void insertarCategorias(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		cv.put(colIdCategoria, 1);
		cv.put(colnombreCategoria, "ENTRADAS");
		db.insert(categoriaTable, colIdCategoria, cv);
		cv.put(colIdCategoria, 2);
		cv.put(colnombreCategoria, "TABLAS");
		db.insert(categoriaTable, colIdCategoria, cv);
		cv.put(colIdCategoria, 3);
		cv.put(colnombreCategoria, "SANDWICHES");
		db.insert(categoriaTable, colIdCategoria, cv);
		cv.put(colIdCategoria, 4);
		cv.put(colnombreCategoria, "CARNES");
		db.insert(categoriaTable, colIdCategoria, cv);
		cv.put(colIdCategoria, 5);
		cv.put(colnombreCategoria, "MINUTAS");
		db.insert(categoriaTable, colIdCategoria, cv);
		cv.put(colIdCategoria, 6);
		cv.put(colnombreCategoria, "PASTAS");
		db.insert(categoriaTable, colIdCategoria, cv);
		cv.put(colIdCategoria, 7);
		cv.put(colnombreCategoria, "BEBIDAS");
		db.insert(categoriaTable, colIdCategoria, cv);
		cv.put(colIdCategoria, 8);
		cv.put(colnombreCategoria, "POSTRE");
		db.insert(categoriaTable, colIdCategoria, cv);

	}

	void insertarUsuario(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		cv.put(colUsername, "Mozo");
		cv.put(colPassword, "Mozo");
		db.insert(usersTable, colUsername, cv);
	}
	
	void insertarMenus(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		cv.put(colIdMenu, 1);
		cv.put(colNombreMenu, "CARNE Y PAPAS AL HORNO");
		cv.put(colUrlFotoLowQMenu,"");
		db.insert(categoriaTable, colIdCategoria, cv);
		

	}
}
