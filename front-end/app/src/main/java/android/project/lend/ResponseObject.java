package android.project.lend;

public class ResponseObject {
    Integer fieldCount, affectedRows, insertId, serverStatus, warningCount, changedRows;
    String message;
    Boolean protocol41;

    public ResponseObject(Integer fieldCount, Integer affectedRows, Integer insertId, Integer serverStatus, Integer warningCount, Integer changedRows, String message, Boolean protocol41) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.serverStatus = serverStatus;
        this.warningCount = warningCount;
        this.changedRows = changedRows;
        this.message = message;
        this.protocol41 = protocol41;
    }
}
