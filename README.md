# FavColorPicker
Color picker(HEX, RGBA)

>Step 1. Add the JitPack repository to your build file

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  >Step 2. Add the dependency
  ```gradle
  	dependencies {
	        implementation 'com.github.ShaikzzCode:FavColorPicker:1.0.0'
	}
  ```
  >Step 3. Add the below code your activity - above your  "setContentView(R.layout.activity_main);"
  ```java
   FavColors favColors = new FavColors(
                MainActivity.this, // Context
                255, // Default Alpha value
                255, // Default Red value
                212, // Default Green value
                67 // Default Blue value
        );
```

> For showing Color Picker, use below line (You can use it in buttons and on any click listner)
```java
favColors.show();
```
  > NOTE : you can replace the default color codes and your activity from here
  > 
  > This repo is for my personal project, if you want you can use it.
