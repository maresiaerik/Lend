package android.project.lend;

public class ItemResponse {

        private String fieldCount;

        private String serverStatus;

        private String protocol41;

        private String changedRows;

        private String affectedRows;

        private String warningCount;

        private String message;

        private String insertId;

        public String getFieldCount ()
        {
            return fieldCount;
        }

        public void setFieldCount (String fieldCount)
        {
            this.fieldCount = fieldCount;
        }

        public String getServerStatus ()
        {
            return serverStatus;
        }

        public void setServerStatus (String serverStatus)
        {
            this.serverStatus = serverStatus;
        }

        public String getProtocol41 ()
        {
            return protocol41;
        }

        public void setProtocol41 (String protocol41)
        {
            this.protocol41 = protocol41;
        }

        public String getChangedRows ()
        {
            return changedRows;
        }

        public void setChangedRows (String changedRows)
        {
            this.changedRows = changedRows;
        }

        public String getAffectedRows ()
        {
            return affectedRows;
        }

        public void setAffectedRows (String affectedRows)
        {
            this.affectedRows = affectedRows;
        }

        public String getWarningCount ()
        {
            return warningCount;
        }

        public void setWarningCount (String warningCount)
        {
            this.warningCount = warningCount;
        }

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        public String getInsertId ()
        {
            return insertId;
        }

        public void setInsertId (String insertId)
        {
            this.insertId = insertId;
        }

        @Override
        public String toString()
        {
            return "[fieldCount = "+fieldCount+", serverStatus = "+serverStatus+", protocol41 = "+protocol41+", changedRows = "+changedRows+", affectedRows = "+affectedRows+", warningCount = "+warningCount+", message = "+message+", insertId = "+insertId+"]";
        }
    }

