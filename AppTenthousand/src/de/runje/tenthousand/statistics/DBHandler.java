package de.runje.tenthousand.statistics;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "tenthousand.db";
 
    // Contacts table name
    private static final String TABLE_PLAYERS = "players";
 
    // Contacts Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_GAMES = "games";
    private static final String KEY_PLAYING = "playing";
    private static final String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "("
            + KEY_NAME + " TEXT," + KEY_PLAYING + " INT,"
            + KEY_GAMES + " INT" + ")";
 
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
        
        db.execSQL(CREATE_PLAYERS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
 
        // Create tables again
        onCreate(db);
    }
    
    public void dropPlayers()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
    }
    
    public void addPlayer(DBPlayer player)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String query = "select count(*) from " + TABLE_PLAYERS + " where " + KEY_NAME + " = ?";
    	Cursor c = db.rawQuery(query, new String[] { player.getName() });
    	if (c.moveToFirst())
    	{
    		if (c.getInt(0) != 0)
    		{
    			Log.d("DBHandler", player.getName() + " is already in DB: " + c.getInt(0));
    			c.close();
    			return;
    		}
    		
    	}
    	
    	c.close();
    	
        ContentValues values = PlayerToValues(player); 
     // TODO: only add if not exist
        // Inserting Row
        db.insert(TABLE_PLAYERS, null, values);
        db.close(); // Closing database connection
    }

	private ContentValues PlayerToValues(DBPlayer player) {
		ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName()); 
        values.put(KEY_GAMES, player.getGames()); 
        values.put(KEY_PLAYING, player.getPlaying());
		return values;
	}
    
    public DBPlayer getPlayer(String name) throws Exception
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_PLAYERS, new String[] { 
                KEY_NAME, KEY_PLAYING, KEY_GAMES }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
        {
	            cursor.moveToFirst();
	            return new DBPlayer(
	                cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
        }
        else
        {
        	Log.d("DBHandler", name + " is not in DB.");
        	throw new Exception();
        }
    }
    
    public ArrayList<DBPlayer> getAllPlayers()
    {
    	ArrayList<DBPlayer> players = new ArrayList<DBPlayer>();
    	
    	String selectQuery = "SELECT * FROM " + TABLE_PLAYERS;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    			
    	if (cursor.moveToFirst())
    	{
    		do
    		{
    			DBPlayer player = new DBPlayer(
    	                cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
    			players.add(player);
    		} while (cursor.moveToNext());
    	}
    	
    	return players;
    }

	public void CreatePlayers() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		
        db.execSQL(CREATE_PLAYERS_TABLE);
	}

	public void updatePlayer(DBPlayer player) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = PlayerToValues(player);
		int i = db.update(TABLE_PLAYERS, values, KEY_NAME + " = ?", new String[] { player.getName() });
		Log.d("DBHandler", "There are " + i + " " + player.getName());
	}

	public void logAllPlayers()
	{
		ArrayList<DBPlayer> players = getAllPlayers();
		Log.d("DBHandler", "All Players");
		for (DBPlayer dbPlayer : players) {
			Log.d("DBHandler", dbPlayer.toString());
		}
	}

	public DBPlayer getPlaying(int index) throws Exception {
		SQLiteDatabase db = this.getReadableDatabase();
   	 
        Cursor cursor = db.query(TABLE_PLAYERS, new String[] { 
                KEY_NAME, KEY_PLAYING, KEY_GAMES }, KEY_PLAYING + "=?",
                new String[] { String.valueOf(index) }, null, null, null, null);
        if (cursor != null)
        {
	            cursor.moveToFirst();
	            return new DBPlayer(
	                cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
        }
        else
        {
        	Log.d("DBHandler", index + " is not in DB.");
        	throw new Exception();
        }
	}
}
