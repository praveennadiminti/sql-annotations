package com.mtheron.anntp.schema;

import static com.mtheron.anntp.utils.StringUtils.convertToString;

public class TestCase2InsertGen {

    public static String generateInsertStatement (com.mtheron.anntp.schema.TestCase2 obj) {
        return "Insert into testCase2table ( " +
                "[ column1name ]," +
                "[ column2name ]," +
                "[ column3name ] " +
                ") VALUES ("
                + convertToString( obj.getColumn1() ) + " ,"
                + convertToString( obj.getColumn2() ) + " ,"
                + convertToString( obj.getColumn3() ) + " "
                + ")";
    }
}