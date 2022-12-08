package com.mtheron.anntp.schema;

import org.apache.commons.lang3.tuple.Pair;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

public class ClassSchemaImpl implements ClassSchema{

    private final String packageName;
    private final String simpleInsertGenClassName;
    private final String fullyQualifiedClassNameofAnnotatedClass;
    private final List<String> columnNames;
    private final List<String> fieldNames;
    private final String tableName;

    //Getters
    public String getPackageName() {
        return packageName;
    }

    public String getSimpleInsertGenClassName() {
        return simpleInsertGenClassName;
    }

    public String getFullyQualifiedClassNameofAnnotatedClass() {
        return fullyQualifiedClassNameofAnnotatedClass;
    }

    public ClassSchemaImpl(TypeElement typeElement) {
        this.packageName = getPackageName(typeElement);
        this.simpleInsertGenClassName = getSimpleInsertGenClassName(typeElement);
        this.fullyQualifiedClassNameofAnnotatedClass = getFullyQualifiedClassName(typeElement);
        List<Pair<String, String>> columnAndFieldNames = getColumnFieldNamePairList(typeElement);
        this.columnNames = getColumnNames(columnAndFieldNames);
        this.fieldNames = getFieldNames(columnAndFieldNames);
        this.tableName = getTableName(typeElement);
    }

    private List<String> getFieldNames(List<Pair<String, String>> columnAndFieldNames) {
        return columnAndFieldNames.stream().map(Pair::getRight).collect(Collectors.toList());
    }

    private List<String> getColumnNames(List<Pair<String, String>> columnAndFieldNames) {
        return columnAndFieldNames.stream().map(Pair::getLeft).collect(Collectors.toList());
    }

    private List<Pair<String, String>> getColumnFieldNamePairList(TypeElement typeElement) {
        List<Pair<String, String>> columnFieldMapping = new ArrayList<>();
        for(Element enclosedElement: typeElement.getEnclosedElements()){
            Column column = enclosedElement.getAnnotation(Column.class);
            if(column != null){
                columnFieldMapping.add(Pair.of(column.name(), enclosedElement.getSimpleName().toString()));
            }
        }
        return columnFieldMapping;
    }

    private String getFullyQualifiedClassName(TypeElement typeElement) {
        return typeElement.getQualifiedName().toString();
    }

    private String getSimpleInsertGenClassName(TypeElement typeElement) {
        return typeElement.getSimpleName()+"InsertGen";
    }

    private String getPackageName(TypeElement typeElement) {
        String fullyQualifiedName = typeElement.getQualifiedName().toString();
        int indexOfLastComma = fullyQualifiedName.lastIndexOf(".");
        return fullyQualifiedName.substring(0, indexOfLastComma);
    }

    public List<String> getColumnNamesExcludingLast() {
        List<String> columnList = new ArrayList<>(this.columnNames);
        columnList.remove(columnList.size()-1);
        return columnList;
    }

    public String getLastColumn() {
        return this.columnNames.get(this.columnNames.size()-1);
    }

    public List<String> getFieldNamesExcludingLast() {
        List<String> columnList = new ArrayList<>(this.fieldNames);
        columnList = columnList.stream().map(this::capitalize).collect(Collectors.toList());
        columnList.remove(columnList.size()-1);
        return columnList;
    }

    public String getLastField() {
        return capitalize(this.fieldNames.get(this.fieldNames.size()-1));
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public String getTableName(TypeElement typeElement) {
        return typeElement.getAnnotation(Table.class).name();
    }

    private String capitalize(String name) {
        if (name != null && name.length() != 0) {
            String var10000 = name.substring(0, 1).toUpperCase(Locale.ENGLISH);
            return var10000 + name.substring(1);
        } else {
            return name;
        }
    }
}
