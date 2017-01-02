# RackMonthPicker
android library dialog month picker

[![](https://jitpack.io/v/lutvie72/rackmonthpicker.svg)](https://jitpack.io/#lutvie72/rackmonthpicker)

![Screenshots](https://raw.githubusercontent.com/lutvie72/RackMonthPicker/master/photo_2017-01-02_08-51-23.jpg)

Download
--------
Download via Maven:

Add the JitPack repository to your build file
```xml
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
Add the dependency
```xml
<dependency>
	    <groupId>com.github.lutvie72</groupId>
	    <artifactId>rackmonthpicker</artifactId>
	    <version>1.0</version>
</dependency>
```

via Gradle:

Add it in your root build.gradle at the end of repositories
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```groovy
dependencies {
	        compile 'com.github.lutvie72:rackmonthpicker:1.0'
	}
```

How to use :
--------
```java
 new RackMonthPicker(this)
         .setPositiveButton(new DateMonthDialogListener() {
               @Override
               public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {

               }
          })
          .setNegativeButton(new OnCancelMonthDialogListener() {
                @Override
                public void onCancel(AlertDialog dialog) {

                }
          }).show();
```
adding .setPositiveText(String text) or .setNegativeText(String Text) to change the text of a button.

Once the dialog is shown, you can dismiss it:
```java
RackMonthPicker rackMonthPicker RackMonthPicker(this)
            .......
            .show();

rackMonthPicker.dismiss();
```
