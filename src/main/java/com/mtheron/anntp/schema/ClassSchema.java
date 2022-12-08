package com.mtheron.anntp.schema;

import java.util.List;

public interface ClassSchema {

    String getPackageName();
    String getSimpleInsertGenClassName();
    String getFullyQualifiedClassNameofAnnotatedClass();
    List<String> getColumnNamesExcludingLast();
    String getLastColumn();
    List<String> getFieldNamesExcludingLast();
    String getLastField();

    String getTableName();
}
