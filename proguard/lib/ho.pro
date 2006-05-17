-injars '../../script/ho.jar'
-outjars hocoded.jar

#-libraryjars 'C:/j2sdk1.4.1_02/jre/lib/rt.jar'
-libraryjars <java.home>/lib/rt.jar

-dontshrink
-allowaccessmodification
-printmapping ho.map
-obfuscationdictionary '../examples/dictionaries/keywords.txt'
-overloadaggressively
-verbose


# Keep - Applications. Keep all application classes that have a main method.
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

# Also keep - Enumerations. Keep a method that is required in enumeration
# classes.
-keepclassmembers class * extends java.lang.Enum {
    public **[] values();
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepnames public class plugins.* {
    public <fields>;
    public <methods>;
}

-keepnames class gui.bilder.*

-keepnames public class gui.UserParameter {
    public <fields>;
    public <methods>;
}

-keepnames public class de.hattrickorganizer.HO {
    public <methods>;
}
