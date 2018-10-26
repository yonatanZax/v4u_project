package db.Managers;

public enum DBResult{
    // General:
    NONE,
    ERROR,
    // Connections:
    CONNECTION_CLOSED,
    // Creations:
    DATABASE_CREATED,
    DATABASE_DELETED,
    TABLE_CREATED,
    // InsertToDB:
    ADDED,
    ALREADY_EXIST,
    // UpdateToDB:
    UPDATED,
    // DeleteFromDB:
    DELETED

}
