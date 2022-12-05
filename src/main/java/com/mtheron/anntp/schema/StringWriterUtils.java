package com.mtheron.anntp.schema;

import java.util.Locale;

public class StringWriterUtils {

    public static String getPackageDeclaration(String packageName){
        return "package "+packageName+";\n";
    }

    public static String getClassDeclaration(String className){
        return "public class "+className+"\n";
    }

    public static String codeBlock(String code){
        return "{\n"+code+"\n}";
    }

    public static String returnString(String statement){
        return "return \""+statement+"\";";
    }

    public static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            String var10000 = name.substring(0, 1).toUpperCase(Locale.ENGLISH);
            return var10000 + name.substring(1);
        } else {
            return name;
        }
    }

    public static String addConvertToStringImportStatement() {
        return "import static com.mtheron.anntp.utils.StringUtils.convertToString;";
    }
}
