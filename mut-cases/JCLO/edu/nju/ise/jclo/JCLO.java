//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.jclo;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JCLO {
    private final Field[] fields;
    private final Object object;
    private boolean doubleDashes;
    private boolean hasEquals;
    private String prefix;
    private final String[][] aliases;

    public JCLO(Object object) {
        this(null, object, null);
    }

    public JCLO(Object object, String[][] aliases) {
        this(null, object, aliases);
    }

    public JCLO(String prefix, Object object) {
        this(prefix, object, null);
    }

    public JCLO(String prefix, Object object, String[][] aliases) {
        this.prefix = "";
        this.object = object;
        this.prefix = prefix;
        this.aliases = aliases;
        this.fields = object.getClass().getDeclaredFields();
        Field[] var4 = this.fields;
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            field.setAccessible(true);
        }

    }

    private Object getObject(Field f) {
        try {
            return f.get(this.object);
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    private Field getField(String key) {
        if (this.prefix != null) {
            key = this.prefix + key;
        }

        Field[] var2 = this.fields;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];
            String name = field.getName();
            if (name.equals(key)) {
                return field;
            }
        }

        return null;
    }

    private void setObject(Field f, Object o) {
        try {
            f.set(this.object, o);
        } catch (IllegalAccessException var4) {
            var4.printStackTrace();
        }

    }

    private String getArrayType(Class type) {
        return type.getComponentType().toString().replaceFirst("class.*\\.", "");
    }

    public String toString() {
        String r = "";
        boolean first = true;
        Field[] var3 = this.fields;
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            String key = field.getName();
            Class type = field.getType();
            Object object = this.getObject(field);
            if (!first) {
                r = r + "\n";
            }

            first = false;
            if (type.isArray()) {
                r = r + this.getArrayType(type) + "[]: " + key + " = ";
                r = r + Arrays.toString((Object[])object);
            } else {
                r = r + type.toString().replaceFirst("class java.lang.", "") + ": " + key + " = " + object;
            }
        }

        return r;
    }

    private String getUsageType(Class type) {
        String dd = this.doubleDashes ? "=" : " ";
        if (!type.getName().equals("boolean") && !type.getName().equals("java.lang.Boolean")) {
            if (type.isArray()) {
                return dd + this.getArrayType(type) + "...";
            } else {
                return type.isEnum() ? dd + Arrays.toString(type.getEnumConstants()) : dd + type.getName().replaceFirst("java.lang.", "");
            }
        } else {
            return this.doubleDashes ? "[=boolean]" : "";
        }
    }

    public String usage() {
        List<String> list = new ArrayList();
        Field[] var2 = this.fields;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];
            String key = field.getName();
            Class type = field.getType();
            if (!key.equals("additional")) {
                if (this.prefix != null) {
                    if (!key.startsWith(this.prefix)) {
                        continue;
                    }

                    key = key.replaceFirst("^" + this.prefix, "");
                }

                if (!Modifier.isFinal(field.getModifiers())) {
                    key = key.replaceFirst("^_([0-9])", "$1");
                    key = key.replaceAll("__", "-");
                    key = key.replaceAll("_\\$", ".");
                    list.add((this.doubleDashes ? "--" : "-") + key + this.getUsageType(type) + "\n");
                }
            }
        }

        Collections.sort(list);
        String r = "";

        String l;
        for(Iterator var9 = list.iterator(); var9.hasNext(); r = r + l) {
            l = (String)var9.next();
        }

        return r;
    }

    private void parseAdditional(String[] args, int i) {
        int number = args.length - i;
        String[] add = new String[number];

        for(int j = 0; j < number; ++i) {
            add[j] = args[i];
            ++j;
        }

        Field f = this.getField("additional");
        if (f != null) {
            this.setObject(f, add);
        } else {
            System.err.println("No varible 'additional' found");
        }

    }

    private Object addToArray(Field field, Object o) {
        Object orig = this.getObject(field);
        Class componentType = field.getType().getComponentType();
        Object ret;
        if (orig == null) {
            ret = Array.newInstance(componentType, 1);
            Array.set(ret, 0, o);
        } else {
            int length = Array.getLength(orig);
            ret = Array.newInstance(componentType, length + 1);

            int j;
            for(j = 0; j < length; ++j) {
                Array.set(ret, j, Array.get(orig, j));
            }

            Array.set(ret, j, o);
        }

        return ret;
    }

    private String getKey(String arg) {
        if (this.hasEquals) {
            arg = arg.replaceFirst("=.*", "");
        }

        if (this.doubleDashes) {
            arg = arg.substring(2);
        } else {
            arg = arg.substring(1);
        }

        arg = arg.replaceAll("^([0-9])", "_$1");
        arg = arg.replaceAll("-", "__");
        arg = arg.replaceAll("\\.", "_\\$");
        if (this.aliases != null) {
            String[][] var2 = this.aliases;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String[] aliase = var2[var4];
                if (aliase[0].equals(arg)) {
                    arg = aliase[1];
                }
            }
        }

        return arg;
    }

    private String getBooleanValue(String arg) {
        if (this.hasEquals) {
            arg = arg.replaceFirst("[^=]*=", "");
            return !arg.equalsIgnoreCase("true") && !arg.equalsIgnoreCase("yes") ? "false" : "true";
        } else {
            return "true";
        }
    }

    private Object makeObject(String type, String val) {
        if (!type.equals("boolean") && type != "java.lang.Boolean") {
            if (type.equals("byte")) {
                return Byte.valueOf(val);
            } else if (type.equals("short")) {
                return Short.valueOf(val);
            } else if (type.equals("int")) {
                return Integer.valueOf(val);
            } else if (type.equals("float")) {
                return Float.valueOf(val);
            } else if (type.equals("double")) {
                return Double.valueOf(val);
            } else if (type.equals("long")) {
                return Long.valueOf(val);
            } else if (type.equals("string")) {
                return val;
            } else if (type.equals("char")) {
                return val.charAt(0);
            } else {
                throw new IllegalArgumentException("Unknown type: " + type);
            }
        } else {
            return Boolean.valueOf(val);
        }
    }

    private String getEqualsValue(String arg) {
        if (!arg.contains("=")) {
            throw new IllegalArgumentException("'" + arg + "' requires '=VALUE'");
        } else {
            return arg.replaceFirst("[^=]*=", "");
        }
    }

    public void parse(String[] args) {
        for(int i = 0; i < args.length; ++i) {
            if (!args[i].startsWith("-")) {
                this.parseAdditional(args, i);
                return;
            }

            this.doubleDashes = args[i].startsWith("--");
            this.hasEquals = args[i].contains("=");
            String key = this.getKey(args[i]);
            Field field = this.getField(key);
            if (field == null) {
                throw new IllegalArgumentException("No such option: \"" + key + "\"");
            }

            Class type = field.getType();
            String name = type.getName();
            if (type.isArray()) {
                name = type.getComponentType().getName();
            }

            String value;
            if (!name.equals("boolean") && !name.equals("java.lang.Boolean")) {
                if (!this.doubleDashes && !this.hasEquals) {
                    ++i;
                    value = args[i];
                } else {
                    value = this.getEqualsValue(args[i]);
                }
            } else {
                value = this.getBooleanValue(args[i]);
            }

            if (!type.isPrimitive()) {
                name = name.replaceFirst("java.lang.", "").toLowerCase();
            }

            Object o = type.isEnum() ? Enum.valueOf(type, value) : this.makeObject(name, value);
            if (type.isArray()) {
                o = this.addToArray(field, o);
            }

            this.setObject(field, o);
        }

    }
}
