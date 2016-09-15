
#-dontwarn com.squareup.haha.guava.**
#-dontwarn com.squareup.haha.perflib.**
#-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
#-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }

#glide image implementation
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
