package com.armut.messenger.business.constant;

public class ProjectConstants {
    public static final long ID_UNSAVED_VALUE = 0L;
    public static final String ID_COLUMN_NAME = "record_id";
    public static final String VERSION_COLUMN_NAME = "version";
    public static final String ACTIVE_COLUMN_NAME = "is_active";
    public static final String CREATION_DATE_COLUMN_NAME = "creation_date";
    public static final String HEADER_ATTRIBUTE_AUTH_USER ="authUser";

    public static final String API_RESPONSE_STATUS_SUCCESS = "success";
    public static final String API_RESPONSE_STATUS_ERROR = "error";


    public class TableConstants {
        public static final String USER_TABLE_NAME = "USER";
        public static final String MESSAGE_TABLE_NAME = "MESSAGE";
        public static final String BLACK_LIST_TABLE_NAME = "BLACK_LIST";

        public class User{
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String TOKEN = "token";
            public static final String TOKEN_EXPIRY_DATE = "token_expiry_date";
        }

        public class Message{
            public static final String CONTENT = "content";
            public static final String FROM_USER_ID = "from_user_id";
            public static final String TO_USER_ID = "to_user_id";
        }

        public class BlackList{
            public static final String BLOCKING_USER_ID = "blocking_user_id";
            public static final String BLOCKED_USER_ID = "blocked_user_id";
        }
    }

}
