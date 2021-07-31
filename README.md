# FavColorPicker
Color picker(HEX, RGBA)

![ScreenShot 1](https://{www.mediafire.com/view/6ha2pq62eirtvw3/Screenshot_2021-07-31-18-33-41-22_4b594a191017da108b84c992b65948f7.jpg/file})

![ScreenShot 2](https://{www.mediafire.com/view/imlaelwtk1jqh2m/Screenshot_2021-07-31-18-33-48-13_4b594a191017da108b84c992b65948f7.jpg/file})


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
  > NOTE : you can replace the default color codes and your activity from here
  > 
  > This repo is for my personal project, if you want you can use it.
