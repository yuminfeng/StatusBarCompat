# StatusBarCompat
StatusBarCompat是一个用于设置系统状态栏颜色的兼容库，兼容Android 4.4.2(API 19)以上，使用简单，仅需要一行代码的调用。

### how to use

 - **Add it in your root build.gradle at the end of repositories:**

```
allprojects {
   repositories {
    ...
    maven { url 'https://jitpack.io' }
}
}
```

 -  **Add the dependency**
```sh
dependencies {
        compile 'com.github.yuminfeng:statusbarcompat:v0.4'
}
```

- **开始使用**
```sh
StatusBarCompat compat = new StatusBarCompat();
compat.setStatusBarColor(this, Color.GRAY);
```