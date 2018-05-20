# CoolApp

### 连接天天模拟器

    adb connect 127.0.0.1:6555


### 2018.05.20

- 轮播图广告

    1. 让图片滑动起来(ViewPager), OK
    2. 让图片和文字,指示器对应起来, OK
    3. 让轮播器无限循环
    
        向右无限循环  
            0 -> 4	newPosition = position % 5  
            5 -> 0  
            6 -> 1  
            7 -> 2  
            8 -> 3  
            9 -> 4  
           10 -> 0  
        向左无限循环  
            设置到中间某个位置.		
    4. 轮播器自动轮询, OK		
    5. 暂时还没有实现点击point跳转


- 下拉选择框


    * Button或ImageButton等自带按钮功能的控件会抢夺所在Layout的焦点.导致其他区域点击不生效.在所在layout声明一个属性
    
            android:descendantFocusability="blocksDescendants"
        
    * popupwindow获取焦点, 外部可点击
    
                // 设置点击外部区域, 自动隐藏
            popupWindow.setOutsideTouchable(true); // 外部可触摸
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置空的背景, 响应点击事件
            
            popupWindow.setFocusable(true); //设置可获取焦点


> - Android 的界面绘制流程
	 
	  测量			 摆放		绘制
	  measure	->	layout	->	draw
	  	  | 		  |			 |
	  onMeasure -> onLayout -> onDraw 重写这些方法, 实现自定义控件
	  
	  都在onResume()之后执行
	  
	  View流程
	  onMeasure() (在这个方法里指定自己的宽高) -> onDraw() (绘制自己的内容)
	  
	  ViewGroup流程
	  onMeasure() (指定自己的宽高, 所有子View的宽高)-> onLayout() (摆放所有子View) -> onDraw() (绘制内容)


### 2. 完全自定义控件.(继承View, ViewGroup)

* 1. 自定义开关  
> - 1. 写个类继承View, OK
> - 2. 拷贝包含包名的全路径到xml中, OK
> - 3. 界面中找到该控件, 设置初始信息, OK
> - 4. 根据需求绘制界面内容,OK
> - 5. 响应用户的触摸事件,OK
> - 6. 创建一个状态更新监听.OK
	
	// 1. 声明接口对象
	public interface OnSwitchStateUpdateListener{
		// 状态回调, 把当前状态传出去
		void onStateUpdate(boolean state);
	}
	// 2. 添加设置接口对象的方法, 外部进行调用
	public void setOnSwitchStateUpdateListener(
			OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
				this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
	}
	// 3. 在合适的位置.执行接口的方法	
	onSwitchStateUpdateListener.onStateUpdate(state);

	// 4. 界面/外部, 收到事件.
> - 7. 自定义属性

	1. 在attrs.xml声明节点declare-styleable

		<declare-styleable name="ToggleView">
	        <attr name="switch_background" format="reference" />
	        <attr name="slide_button" format="reference" />
	        <attr name="switch_state" format="boolean" />
	    </declare-styleable>

	2. R会自动创建变量

		attr 3个变量
		styleable 一个int数组, 3个变量(保存位置)

	3. 在xml配置声明的属性/ 注意添加命名空间
	
	    xmlns:itheima="http://schemas.android.com/apk/res/com.itheima74.toggleview"
		
        itheima:switch_background="@drawable/switch_background"
        itheima:slide_button="@drawable/slide_button"
        itheima:switch_state="false"

	4. 在构造函数中获取并使用

		// 获取配置的自定义属性
		String namespace = "http://schemas.android.com/apk/res/com.itheima74.toggleview";
		int switchBackgroundResource = attrs.getAttributeResourceValue(namespace , "switch_background", -1);
		
		


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

- 优酷菜单

    1. 在xml布局里摆放好, OK
    2. 给指定控件添加点击事件. OK
    3. 根据业务逻辑,执行动画(旋转动画: 补间动画). OK
    4. 菜单按钮的截获. OK
		


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