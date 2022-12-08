package com.mtheron.anntp.schema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InsertGenClassSchemaTest {

    @Test
    @Disabled
    public void getInsertStatementFunctionOneColumnTest(){
        InsertGenClassSchema insertGenClassSchema = new InsertGenClassSchema();
        insertGenClassSchema.setFullyQualifiedClassName("TestClass2");
        insertGenClassSchema.setTableName("CardTransactions");
        insertGenClassSchema.setColumns(List.of("column1"));
        insertGenClassSchema.setFields(List.of("column1"));

        String generatedFunction = insertGenClassSchema.generateInsertFunction();
        String expectedFunction = "public String generateInsertStatement (TestClass2 obj)\n" +
                "{\n" +
                "return \"Insert into CardTransactions ([column1]) VALUES (\"\n" +
                "+obj.getColumn1()+\" \"\n" +
                "+\")\";\n" +
                "}";
        Assertions.assertEquals(expectedFunction, generatedFunction);
    }

    @Test
    public void getInsertStatementFunctionTwoColumnTest(){
        InsertGenClassSchema insertGenClassSchema = new InsertGenClassSchema();
        insertGenClassSchema.setFullyQualifiedClassName("TestClass2");
        insertGenClassSchema.setTableName("CardTransactions");
        insertGenClassSchema.setColumns(List.of("column1","column2"));
        insertGenClassSchema.setFields(List.of("column1","column2"));

        String generatedFunction = insertGenClassSchema.generateInsertFunction();
        String expectedFunction = "public String generateInsertStatement (TestClass2 obj)\n" +
                "{\n" +
                "return \"Insert into CardTransactions ([column1],[column2]) VALUES (\"\n" +
                "+obj.getColumn1()+\" ,\"\n" +
                "+obj.getColumn2()+\" \"\n" +
                "+\")\";\n" +
                "}";
        Assertions.assertEquals(expectedFunction, generatedFunction);
    }
}
