package com.mtheron.anntp.schema;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.mtheron.anntp.schema.StringWriterUtils.*;

public class InsertGenClassSchema {

    private String packageName;
    private String className;

    private Map<String,String> columnFieldNameMap;

    private List<String> columns;

    private List<String> fields;

    private String tableName;

    private String simpleClassName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getColumnFieldNameMap() {
        return columnFieldNameMap;
    }

    public void setColumnFieldNameMap(Map<String, String> columnFieldNameMap) {
        this.columnFieldNameMap = columnFieldNameMap;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public InsertGenClassSchema(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public InsertGenClassSchema() {
    }

    public String getInsertGenClassFileString(){
        return getPackageDeclaration(packageName) +
                getClassDeclaration(className) +
                codeBlock("");
    }

    public String generateInsertFunction(){

        return "public String generateInsertStatement ("+simpleClassName+" obj)\n" +
                codeBlock(
                        returnString("Insert into "+tableName+" " +
                                "("+
                                getAllColumnsWrapped(columns)+
                                ") " +
                                "VALUES (\"\n" +
                                getAllValues(fields)+
                        "+\")")
                );

    }


    private String getAllValues(List<String> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String field: fields) {
            stringBuilder.append("+");
            stringBuilder.append("obj.get").append(capitalize(field)).append("()+\" ,\"");
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        return stringBuilder.toString();
    }

    private String getAllColumnsWrapped(List<String> columns) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String col :
                columns) {
            stringBuilder.append("[").append(col).append("]");
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        return stringBuilder.toString();

    }


}