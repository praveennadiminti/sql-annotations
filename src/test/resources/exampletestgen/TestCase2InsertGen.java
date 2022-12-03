package com.mtheron.anntp.schema;

public class TestCase2InsertGen
{
    public String generateInsertStatement (com.mtheron.anntp.schema.TestCase2 obj)
    {
        return "Insert into testCase2table ([column1name],[column2name],[column3name]) VALUES ("
                + obj.getColumn1() + " ,"
                + obj.getColumn2() + " ,"
                + obj.getColumn3() + " "
                + ")";
    }
}
