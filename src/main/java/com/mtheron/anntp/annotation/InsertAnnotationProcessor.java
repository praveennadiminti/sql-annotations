package com.mtheron.anntp.annotation;

import com.mtheron.anntp.schema.InsertGenClassSchema;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.mtheron.anntp.annotation.GetInsertStatement")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class InsertAnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment re) {
	    processingEnv.getMessager().printMessage(Kind.NOTE, "Inside the processor, Hoorey");
		Set<? extends Element> annotatedElements = re.getElementsAnnotatedWith(GetInsertStatement.class);
		for (Element annotatedElement : annotatedElements) {
			if (annotatedElement.asType().getKind() != TypeKind.DECLARED) {
				processingEnv.getMessager().printMessage(Kind.ERROR, "Only classes needs to be annotated with GetInsertStatement");
			}
			TypeElement typeElement = (TypeElement) annotatedElement;
			String fullClassName = String.valueOf(typeElement.getQualifiedName());

			printInsertClass(fullClassName);
		}
	    
	    return false;
	}

	private void printInsertClass(String fullClassName) {
		String packageName = getPackageName(fullClassName);
		String simpleName = getSimpleName(fullClassName);
		String inserterName = simpleName + "InsertGen";
		String fullyQualifiedInserterName = packageName+"."+inserterName;

		InsertGenClassSchema insertGenClassSchema = new InsertGenClassSchema(packageName, inserterName);

		try {
			JavaFileObject outputFile = processingEnv.getFiler().createSourceFile(fullyQualifiedInserterName);
			try(PrintWriter out = new PrintWriter(outputFile.openOutputStream())){
				out.print(insertGenClassSchema.getInsertGenClassFileString());
				out.flush();
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getClassEndStatement() {
		return "}";
	}

	private String getClassIntializationStatement(String simpleName) {
		return "public class "+simpleName+ " {";
	}

	private String getPackageNameStatement(String packageName) {
		return "package "+packageName+";";
	}

	private String getSimpleName(String fullClassName) {
		int lastIndexOfDot = fullClassName.lastIndexOf('.');
		return fullClassName.substring(lastIndexOfDot+1);
	}

	private String getPackageName(String fullClassName) {

		int lastIndexOfDot = fullClassName.lastIndexOf('.');
		return fullClassName.substring(0, lastIndexOfDot);
	}

}
