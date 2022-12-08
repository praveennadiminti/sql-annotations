# Annotation Processor for the generation of MSSQL statements from JPA POJOS
## Notes
Sometimes while dealing with legacy codebases, because of some limitation in ORM frameworks like Hibernate. It becomes a necessity to generate custom insert statement strings. This maven project contains annotations and annotation processors to generate functions for getting the sql statements. 
## Usage notes

annotate the javax pojos with the annotation `@GenerateInsertStatement`

```java
package com.mtheron.anntp.schema;

import javax.persistence.Column;
import javax.persistence.Table;

@GenerateInsertStatment
@Table(name = "testCase2table")
public class TestClass2 {

    @Column(name = "column1name")
    private String column1;
    @Column(name = "column2name")
    private String column2;
    @Column(name = "column3name")
    private String column3;
    // ....
}
```

A class would be generated in target folder with name `"{annotatedPojo}InsertGen"` with a function `generateInsertStatement`
```java
public class TestCase2InsertGen
{
    public String generateInsertStatement (com.mtheron.anntp.schema.TestCase2 obj)
    {
        return "Insert into testCase2table ([column1name],[column2name],[column3name]) VALUES ("
                + convertToString( obj.getColumn1() ) + " ,"
                + convertToString( obj.getColumn2() ) + " ,"
                + convertToString( obj.getColumn3() ) + " "
                + ")";
    }
}
```