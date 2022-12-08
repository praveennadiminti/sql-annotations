package com.mtheron.anntp.annotation;

import com.mtheron.anntp.schema.ClassSchema;
import com.mtheron.anntp.schema.ClassSchemaImpl;
import com.mtheron.anntp.writer.StringSchemaWriter;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@SupportedAnnotationTypes("com.mtheron.anntp.annotation.GetInsertStatement")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class InsertAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Function<RoundEnvironment, Stream<? extends TypeElement>> extractAllAnnotedClasses = this::extractAllAnnotedClasses;
        Function<TypeElement, Pair<String,TypeElement>> getGeneratedClassString = this::getGeneratedClassString;
        Consumer<Pair<String,TypeElement>> writeOutputToFile = this::writeOutputToFile;

        Consumer<RoundEnvironment> pipeline = (re) -> extractAllAnnotedClasses.apply(re)
                .map(getGeneratedClassString)
                .forEach(writeOutputToFile);

        pipeline.accept(roundEnvironment);
        return false;
    }

    private void writeOutputToFile(Pair<String,TypeElement> pair) {
        TypeElement typeElement = pair.getRight();
        String fileData = pair.getLeft();
        String fullyQualifiedInserterName = getFullyQualifiedInserterName(typeElement);
        try {
            JavaFileObject outputFile = processingEnv.getFiler().createSourceFile(fullyQualifiedInserterName);
            try(PrintWriter out = new PrintWriter(outputFile.openOutputStream())){
                out.print(fileData);
                out.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFullyQualifiedInserterName(TypeElement typeElement) {
        String packageName = getPackageName(typeElement.getQualifiedName().toString());
        String simpleBaseName = typeElement.getSimpleName().toString();
        String inserterSimpleName = simpleBaseName + "InsertGen";
        return packageName + "."+inserterSimpleName;
    }

    private String getPackageName(String fullClassName) {
        int lastIndexOfComma = fullClassName.lastIndexOf('.');
        return fullClassName.substring(0,lastIndexOfComma);
    }

    private Pair<String,TypeElement> getGeneratedClassString(TypeElement typeElement) {
        Function<TypeElement, ClassSchema> generateSchema = this::generateSchema;
        Function<ClassSchema, String> generateStringFromInsertGenSchema = StringSchemaWriter::write;
        return Pair.of(generateSchema
                .andThen(generateStringFromInsertGenSchema)
                .apply(typeElement), typeElement);
    }

    private ClassSchema generateSchema(TypeElement typeElement) {
        return new ClassSchemaImpl(typeElement);
    }

    private Stream<? extends TypeElement> extractAllAnnotedClasses(RoundEnvironment roundEnvironment) {
        roundEnvironment.getElementsAnnotatedWith(GetInsertStatement.class).stream()
                .filter(element -> !(element instanceof TypeElement))
                .forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Only classes and interfaces with Table and column annotations can be annotated with @GetInsertStatement"));
        return roundEnvironment.getElementsAnnotatedWith(GetInsertStatement.class).stream()
                .filter(element -> element instanceof TypeElement)
                .map(element -> (TypeElement) element);
    }
}
