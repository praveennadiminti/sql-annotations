package com.mtheron.anntp;

import com.mtheron.anntp.annotation.InsertAnnotationProcessor;
import com.mtheron.anntp.utils.IgnoreLineEndingsCustomMatcher;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.junit.jupiter.api.Test;

public class AnnotationProcessorTest {

    @Test
    public void testAnnotationFunctionGeneration() {
        CompileTestBuilder.compilationTest()
                .addSources("exampletestsources/TestCase2.java")
                .addProcessors(InsertAnnotationProcessor.class)
                .compilationShouldSucceed()
                .expectThatGeneratedSourceFileExists("com.mtheron.anntp.schema.TestCase2InsertGen",
                        new IgnoreLineEndingsCustomMatcher(JavaFileObjectUtils.readFromResource("exampletestgen/TestCase2InsertGen.java")))
                .executeTest();
    }
}
