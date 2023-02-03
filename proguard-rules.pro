# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



# common java classes
-keep public class com.example.all.document.viewer.*
-keep class com.example.all.document.viewer**
-keep public class com.example.all.document.viewer.** { *;}
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-optimizationpasses 5
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
-dump obfuscation/class_files.txt
-printseeds obfuscation/seeds.txt
-obfuscationdictionary obfuscation/keywords.txt
-classobfuscationdictionary obfuscation/keywords.txt
-packageobfuscationdictionary obfuscation/keywords.txt
-keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#   pdf viewer
-keep class com.shockwave.**

# all files
-keep class com.wxiwei.office**
