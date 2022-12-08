package com.mtheron.anntp.utils;

import io.toolisticon.cute.Constants;
import io.toolisticon.cute.FailingAssertionException;
import io.toolisticon.cute.GeneratedFileObjectMatcher;
import javax.tools.FileObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

public class IgnoreLineEndingsCustomMatcher implements GeneratedFileObjectMatcher {

    final FileObject expectedFileObject;

    /**
     * Hidden constructor.
     *
     * @param expectedFileObject the expected java file object
     * @throws PatternSyntaxException If the expression's syntax is invalid
     */
    public IgnoreLineEndingsCustomMatcher(FileObject expectedFileObject) {
        this.expectedFileObject = expectedFileObject;
    }

    @Override
    public boolean check(FileObject fileObject) throws IOException {

        if (!contentEquals(fileObject.openInputStream(), expectedFileObject.openInputStream())) {
            throw new FailingAssertionException(Constants.Messages.GFOM_FILEOBJECTS_ARENT_EQUAL_BY_TEXTUAL_COMPARISION_WITH_IGNORE_LINEENDINGS.produceMessage());
        }

        return true;
    }


    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {

        BufferedReader br1 = new BufferedReader(new InputStreamReader(input1));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(input2));

        String br1line;
        String br2line;
        do {
            br1line = br1.readLine();
            br2line = br2.readLine();


            if (br1line != null && br2line != null && br1line != br2line) {

                if (!br1line.trim().equals(br2line.trim())) {
                    return false;
                }

            } else if (br1line != null || br2line != null) {
                return false;
            }

        } while (br1line != null && br2line != null);

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IgnoreLineEndingsCustomMatcher that = (IgnoreLineEndingsCustomMatcher) o;

        return Objects.equals(expectedFileObject, that.expectedFileObject);
    }

    @Override
    public int hashCode() {
        return expectedFileObject != null ? expectedFileObject.hashCode() : 0;
    }

}
