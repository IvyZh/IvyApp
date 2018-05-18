# CoolApp

### 2018.05.18

- 组合已有的控件实现
    - 优酷菜单
    - 轮播图广告
    - 下拉选择框
- 继承已有的控件实现
    - 下拉刷新功能的ListView
    - 完全自定义控件（继承View，ViewGroup）
- 自定义开关
    -  侧滑面板



### 2018.05.14

**引入Bbom登录的时候jar包冲突**

冲突1：common_library和bmob都引用了okhttp3

    compile project(':common_library')
    compile 'cn.bmob.android:bmob-sdk:3.5.5'
    
解决方案1(没解决):先不引用common_library，本项目内自测

    compile('com.squareup.okhttp3:okhttp:3.10.0') {
         exclude(module: 'okhttp3')
     }
     compile('cn.bmob.android:bmob-sdk:3.5.5') {
         exclude (module: 'okhttp3')
    }

解决方案2(没解决):去掉bmob的gradle直接引用改为jar包引用

    compile 'com.squareup.okhttp3:okhttp:3.10.0'
    //compile 'cn.bmob.android:bmob-sdk:3.5.5'
    
报错信息：
    
    Error:Error converting bytecode to dex:
    Cause: com.android.dex.DexException: Multiple dex files define Lokio/Buffer;

解决方案3(解决):删除okio-1.12.0.jar

    去掉bmob的gradle直接引用改为jar包引用，
    compile 'com.squareup.okhttp3:okhttp:3.10.0'
    //compile 'cn.bmob.android:bmob-sdk:3.5.5'

尝试去掉app.gradle中对okhttp3的引用，改为在common_library中对okhttp3的引用

测试结果：OK！


**字体修改的assets文件夹不能手动创建--**

    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FONT.TTF");
    mTvProtocol.setTypeface(typeface);

参考：http://code2care.org/pages/android-studio-native-typeface-cannot-be-made-error/

**解决Android dependency 'com.android.support:appcompat-v7' has different version for the compile (26.1...--**

参考：https://www.jianshu.com/p/fbc2996192c9



### 2018.05.12

**1.搭建豆瓣电影框架**

ViewPager 自动销毁重建问题




### 2018.05.11

First Commit.

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

**4. 引入Logger**

	https://github.com/orhanobut/logger
	
	implementation 'com.orhanobut:logger:2.2.0'

**5. 引入Butter Knife**

	https://github.com/JakeWharton/butterknife
	
	
	dependencies {
	  implementation 'com.jakewharton:butterknife:8.8.1'
	  annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
	}
	
	
	buildscript {
	  repositories {
	    mavenCentral()
	   }
	  dependencies {
	    classpath 'com.jakewharton:butterknife-gradle-plugin:8.8.1'
	  }
	}
	
	
	apply plugin: 'com.android.library'
	apply plugin: 'com.jakewharton.butterknife'


发现只要配置这个就好了：

	dependencies {
	  implementation 'com.jakewharton:butterknife:8.8.1'
	  annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
	}
	


**Genymotion-ARM-Translation.zip**


**看其他APP总结的一些开发经验**

- 1. 刚进应用就请求需要用到的权限
- 2. Splash页面要全屏


**全屏显示**

尝试1：


8.0模拟器上面，如果只是在代码中这样设置，Activity不设置主题的话，显示效果为依旧有Title，全屏效果是有的（状态栏没）：

    //设置无标题
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    //设置全屏
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




 

尝试2：

代码不加设置，只是在manifest文件中配置Activity的主题样式即：

	android:theme="@style/Theme.AppCompat.Light.NoActionBar"

则显示效果为：没有ActionBar了，但不是全屏效果，可以看到状态栏的电池等图标。

尝试3：可以了，之前也是这么操作的，所以这是目前的解决办法

	android:theme="@style/Theme.AppCompat.Light.NoActionBar"
	 //设置全屏
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

[注，2018.05.13]看到另外一种设置全屏的方法，只要设置theme就可以了，而不用在代码设置FLAG_FULLSCREEN这个了。


**ANDROID FRAMENT的切换（解决REPLACE的低效）**

	https://www.cnblogs.com/android-joker/p/4414891.html

    // 这种方式会导致重新加载资源
	// FragmentTransaction transaction = mFragmentManager.beginTransaction();
	// transaction.replace(R.id.fl_main, mFragmentList.get(pos)).commit();

    // 会用这种方法不会导致Fragment的重新替换
    switchFragment(mFragmentList.get(fromPos), mFragmentList.get(pos));


**hierarchy viewer**

查看酷安，发现底部栏用的Ahbottomnavigation， Material Design学习之 Bottom navigation

	https://github.com/aurelhubert/ahbottomnavigation