package db.Managers;

public enum DBResult{
    // General:
    NONE,
    ERROR,
    // Connections:
    CONNECTION_CLOSED,
    // Creations:
    DATABASE_CREATED,
    TABLE_CREATED,
    TABLE_NOT_CREATED,
    // InsertToDB:
    ADDED,
    ALREADY_EXIST,
    // UpdateToDB:
    UPDATED,
    // DeleteFromDB:
    DELETED

}
