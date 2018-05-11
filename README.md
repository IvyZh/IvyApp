# IvyApp
First Commit.


### 2018.05.11

**1. build.gradle（Module:app）**

	compileSdkVersion 26
	implementation 'com.android.support:appcompat-v7:26.1.0'
	implementation 'com.android.support.constraint:constraint-layout:1.1.0'
 
**2. build.gradle（Project:IvyApp）**
	
	buildscript {
	    repositories {
	        google()
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:3.0.1'
	    }
	}
	
	allprojects {
	    repositories {
	        google()
	        jcenter()
	    }
	}
	
	task clean(type: Delete) {
	    delete rootProject.buildDir
	}

**3. 去掉testInstrumentationRunner，AndroidJUnitRunner的东西**

