package com.mtheron.anntp.writer;

import com.mtheron.anntp.schema.ClassSchema;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;

public class StringSchemaWriter {

    public static String write(ClassSchema classSchemaImpl) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        velocityEngine.addProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        Template t = velocityEngine.getTemplate("InsertGenClassSchema.vm");

        VelocityContext context = new VelocityContext();
        context.put("packageName", classSchemaImpl.getPackageName());
        context.put("generatedClassName", classSchemaImpl.getSimpleInsertGenClassName());
        context.put("qualifiedNameOfBaseClass", classSchemaImpl.getFullyQualifiedClassNameofAnnotatedClass());
        context.put("columnListExcludingLast", classSchemaImpl.getColumnNamesExcludingLast());
        context.put("lastColumn", classSchemaImpl.getLastColumn());
        context.put("fieldListExcludingLast", classSchemaImpl.getFieldNamesExcludingLast());
        context.put("lastField", classSchemaImpl.getLastField());
        context.put("tableName", classSchemaImpl.getTableName());

        StringWriter writer = new StringWriter();
        t.merge( context, writer );

        return writer.toString();
    }
}
