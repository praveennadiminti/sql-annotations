package com.mtheron.anntp.annotation;

import com.mtheron.anntp.schema.InsertGenClassSchema;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.persistence.Column;
import javax.persistence.Table;
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
			String tableName = typeElement.getAnnotation(Table.class).name();
			Map<String, String> columnNameFieldMap = getColumnFieldmapping(typeElement.getEnclosedElements());
			printInsertClass(fullClassName, columnNameFieldMap, tableName);
		}
	    
	    return false;
	}

	private Map<String, String> getColumnFieldmapping(List<? extends Element> enclosedElements) {
		Map<String, String> columnFieldMapping = new HashMap<>();
		for(Element enclosedElement: enclosedElements){
			Column column = enclosedElement.getAnnotation(Column.class);
			if(column != null){
				columnFieldMapping.put(column.name(), enclosedElement.getSimpleName().toString());
			}
		}
		return columnFieldMapping;
	}

	private void printInsertClass(String fullClassName, Map<String, String> columnNameFieldMap, String tableName) {
		String packageName = getPackageName(fullClassName);
		String simpleName = getSimpleName(fullClassName);
		String inserterName = simpleName + "InsertGen";
		String fullyQualifiedInserterName = packageName+"."+inserterName;

		InsertGenClassSchema insertGenClassSchema = new InsertGenClassSchema(packageName, inserterName);
		List<String> columns = new ArrayList<>();
		List<String> fields = new ArrayList<>();
		for(Map.Entry<String, String> entry: columnNameFieldMap.entrySet() ){
			columns.add(entry.getKey());
			fields.add(entry.getValue());
		}
		insertGenClassSchema.setColumns(columns);
		insertGenClassSchema.setFields(fields);
		insertGenClassSchema.setFullyQualifiedClassName(fullClassName);
		insertGenClassSchema.setTableName(tableName);

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

	private String getSimpleName(String fullClassName) {
		int lastIndexOfDot = fullClassName.lastIndexOf('.');
		return fullClassName.substring(lastIndexOfDot+1);
	}

	private String getPackageName(String fullClassName) {
		int lastIndexOfDot = fullClassName.lastIndexOf('.');
		return fullClassName.substring(0, lastIndexOfDot);
	}

}
