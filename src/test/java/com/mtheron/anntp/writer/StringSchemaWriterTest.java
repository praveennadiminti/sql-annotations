package com.mtheron.anntp.writer;

import com.mtheron.anntp.schema.ClassSchema;
import com.mtheron.anntp.utils.IgnoreLineEndingsCustomMatcher;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StringSchemaWriterTest {

    @Test
    public void stringSchemaWrirterTest() throws IOException {
        String actualGeneratedString = StringSchemaWriter.write(new ClassSchema() {
            @Override
            public String getPackageName() {
                return "com.mtheron.anntp.schema";
            }

            @Override
            public String getSimpleInsertGenClassName() {
                return "TestCase2InsertGen";
            }

            @Override
            public String getFullyQualifiedClassNameofAnnotatedClass() {
                return "com.mtheron.anntp.schema.TestCase2";
            }

            @Override
            public List<String> getColumnNamesExcludingLast() {
                return List.of("column1name","column2name");
            }

            @Override
            public String getLastColumn() {
                return "column3name";
            }

            @Override
            public List<String> getFieldNamesExcludingLast() {
                return List.of("Column1", "Column2");
            }

            @Override
            public String getLastField() {
                return "Column3";
            }

            @Override
            public String getTableName() {
                return "testCase2table";
            }
        });

        String expectedString = readFileAsString("exampletestgen/TestCase2InsertGen.java");
        Assertions.assertTrue(  IgnoreLineEndingsCustomMatcher.contentEquals(new ByteArrayInputStream(expectedString.getBytes()),
                new ByteArrayInputStream(actualGeneratedString.getBytes())));
    }

    private String readFileAsString(String filename) throws IOException {
        Path path = Paths.get(this.getClass().getClassLoader().getResource(filename).getPath());
        return Files.readString(path);
    }
}
