# 🚀 Android Automotive 启动欢迎页和登录页模块 - UI资源包

本模块包含完整的Android Automotive应用启动欢迎页和登录页的**UI资源包**，专为车载环境优化设计。

## 📁 完整文件结构

### 2_Layout_Files/ - 布局文件
```
├── activity_splash.xml          # 启动页布局（横屏优化）
├── activity_login.xml           # 登录页布局（车载大屏适配）
├── dialog_qr_login.xml          # 二维码登录对话框
└── dialog_phone_login.xml       # 手机号登录对话框
```

### 3_Image_Resources/ - 图片资源
#### 3.1_Main_Images/ - 主要图片（多分辨率）
```
├── mdpi/logo_music.png          # Logo - 基准分辨率
├── hdpi/logo_music.png          # Logo - 1.5x分辨率
├── xhdpi/logo_music.png         # Logo - 2x分辨率
├── xxhdpi/logo_music.png        # Logo - 3x分辨率
└── xxxhdpi/logo_music.png       # Logo - 4x分辨率
```

#### 3.4_Background_Images/ - 多分辨率背景图片
```
├── mdpi/cherry_blossom_car.webp     # 背景图 - 基准分辨率
├── hdpi/cherry_blossom_car.webp     # 背景图 - 1.5x分辨率
├── xhdpi/cherry_blossom_car.webp    # 背景图 - 2x分辨率
├── xxhdpi/cherry_blossom_car.webp   # 背景图 - 3x分辨率
└── xxxhdpi/cherry_blossom_car.webp  # 背景图 - 4x分辨率
```

#### 3.2_Login_Icons/ - 登录图标
```
├── ic_qrcode_login.xml          # 扫码登录图标
├── ic_phone_login.xml           # 手机登录图标
├── ic_guest_login.xml           # 游客登录图标
├── ic_qr_placeholder.xml        # 二维码占位符
└── ic_qr_error.xml              # 二维码错误图标
```

#### 3.3_Background_Styles/ - 背景样式
```
├── splash_background.xml        # 启动页背景渐变
├── splash_gradient.xml          # 启动页渐变效果
├── modern_login_button.xml      # 现代登录按钮样式
├── round_icon_bg.xml            # 圆形图标背景
├── sakura_dialog_background.xml # 樱花主题对话框背景
├── sakura_button.xml            # 樱花主题按钮
├── sakura_button_secondary.xml  # 樱花主题次要按钮
├── sakura_edit_background.xml   # 樱花主题输入框背景
├── cherry_blossom_car_background.xml # 应用图标背景
└── ic_launcher_background.xml   # 启动器图标背景
```

### 4_Style_Resources/ - 样式资源
```
├── colors.xml                   # 颜色定义（包含樱花主题色彩）
├── strings.xml                  # 字符串资源（中文本地化）
└── themes.xml                   # 主题样式（Android Automotive专用）
```

### 5_App_Icons/ - 应用图标
#### 5.1_Launcher_Icons/ - 启动器图标（已复制所有分辨率）
```
├── mdpi/ic_launcher.png         # 48x48dp (基准分辨率)
├── hdpi/ic_launcher.png         # 72x72dp (1.5x)
├── xhdpi/ic_launcher.png        # 96x96dp (2x)
├── xxhdpi/ic_launcher.png       # 144x144dp (3x)
└── xxxhdpi/ic_launcher.png      # 192x192dp (4x)
```

### 7_Animation_Resources/ - 动画资源
```
├── fade_in.xml                  # 淡入动画
├── fade_out.xml                 # 淡出动画
└── button_press.xml             # 按钮点击动画
```

## 🚀 详细使用说明

### 第一步：文件复制到新项目

#### 1.1 Activity类文件复制
```bash
# 复制到你的项目包结构中
app/src/main/java/你的包名/ui/splash/SplashActivity.java
app/src/main/java/你的包名/ui/login/LoginActivity.kt
```

#### 1.2 布局文件复制
```bash
# 复制到布局目录
app/src/main/res/layout/activity_splash.xml
app/src/main/res/layout/activity_login.xml
app/src/main/res/layout/dialog_qr_login.xml
app/src/main/res/layout/dialog_phone_login.xml
```

#### 1.3 图片资源复制
```bash
# 多分辨率Logo图片（推荐按分辨率分别复制以获得最佳效果）
app/src/main/res/drawable-mdpi/logo_music.png
app/src/main/res/drawable-hdpi/logo_music.png
app/src/main/res/drawable-xhdpi/logo_music.png
app/src/main/res/drawable-xxhdpi/logo_music.png
app/src/main/res/drawable-xxxhdpi/logo_music.png

# 或者使用单一版本（如果多分辨率版本相同）
app/src/main/res/drawable/logo_music.png

# 多分辨率背景图片（需要按分辨率分别复制）
app/src/main/res/drawable-mdpi/cherry_blossom_car.webp
app/src/main/res/drawable-hdpi/cherry_blossom_car.webp
app/src/main/res/drawable-xhdpi/cherry_blossom_car.webp
app/src/main/res/drawable-xxhdpi/cherry_blossom_car.webp
app/src/main/res/drawable-xxxhdpi/cherry_blossom_car.webp

# 登录图标（XML矢量图标）
app/src/main/res/drawable/ic_qrcode_login.xml
app/src/main/res/drawable/ic_phone_login.xml
app/src/main/res/drawable/ic_guest_login.xml
app/src/main/res/drawable/ic_qr_placeholder.xml
app/src/main/res/drawable/ic_qr_error.xml

# 背景样式
app/src/main/res/drawable/splash_background.xml
app/src/main/res/drawable/splash_gradient.xml
app/src/main/res/drawable/modern_login_button.xml
app/src/main/res/drawable/round_icon_bg.xml
app/src/main/res/drawable/sakura_dialog_background.xml
app/src/main/res/drawable/sakura_button.xml
app/src/main/res/drawable/sakura_button_secondary.xml
app/src/main/res/drawable/sakura_edit_background.xml
```

#### 1.4 应用图标复制
```bash
# 各分辨率图标（按分辨率分别复制到对应目录）
app/src/main/res/mipmap-mdpi/ic_launcher.png
app/src/main/res/mipmap-hdpi/ic_launcher.png
app/src/main/res/mipmap-xhdpi/ic_launcher.png
app/src/main/res/mipmap-xxhdpi/ic_launcher.png
app/src/main/res/mipmap-xxxhdpi/ic_launcher.png

# 注意：只使用ic_launcher.png，这是AndroidManifest.xml中实际引用的图标
```

#### 1.5 动画资源复制
```bash
app/src/main/res/anim/fade_in.xml
app/src/main/res/anim/fade_out.xml
app/src/main/res/anim/button_press.xml
```

### 第二步：样式资源合并

#### 2.1 合并颜色资源
将 `4_Style_Resources/colors.xml` 中的颜色定义合并到你的 `app/src/main/res/values/colors.xml` 文件中。

#### 2.2 合并字符串资源
将 `4_Style_Resources/strings.xml` 中的字符串定义合并到你的 `app/src/main/res/values/strings.xml` 文件中。

#### 2.3 合并主题样式
将 `4_Style_Resources/themes.xml` 中的主题定义合并到你的 `app/src/main/res/values/themes.xml` 文件中。

### 第三步：修改包名引用

在Activity类文件中修改包名：

#### 3.1 SplashActivity.java
```java
// 原包名
package com.example.aimusicplayer.ui.splash;

// 改为你的包名
package 你的包名.ui.splash;

// 同时修改import语句中的包名引用
import 你的包名.R;
import 你的包名.ui.login.LoginActivity;
```

#### 3.2 LoginActivity.kt
```kotlin
// 原包名
package com.example.aimusicplayer.ui.login

// 改为你的包名
package 你的包名.ui.login

// 同时修改import语句中的包名引用
import 你的包名.R
import 你的包名.databinding.*
```

### 第四步：更新AndroidManifest.xml

将 `6_Configuration_Files/AndroidManifest_additions.xml` 中的配置添加到你的AndroidManifest.xml文件中：

```xml
<!-- 在<application>标签内添加 -->
<activity
    android:name=".ui.splash.SplashActivity"
    android:exported="true"
    android:screenOrientation="landscape"
    android:theme="@style/FullScreenTheme"
    android:hardwareAccelerated="true"
    android:launchMode="singleTop">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
        <category android:name="android.intent.category.LEANBACK_LAUNCHER" />
    </intent-filter>
</activity>

<activity
    android:name=".ui.login.LoginActivity"
    android:exported="false"
    android:screenOrientation="landscape"
    android:theme="@style/FullScreenTheme"
    android:hardwareAccelerated="true"
    android:launchMode="singleTop" />

<!-- 在<manifest>标签内添加权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />

<!-- Android Automotive特性声明 -->
<uses-feature
    android:name="android.hardware.type.automotive"
    android:required="false" />
<uses-feature
    android:name="android.software.leanback"
    android:required="false" />
```

### 第五步：添加Gradle依赖

将 `6_Configuration_Files/build_gradle_dependencies.gradle` 中的依赖添加到你的app/build.gradle文件中。

#### 5.1 在android块中添加：
```gradle
android {
    buildFeatures {
        viewBinding true
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = '1.8'
    }
}
```

#### 5.2 在dependencies块中添加：
```gradle
dependencies {
    // Hilt依赖注入
    implementation 'com.google.dagger:hilt-android:2.50'
    kapt 'com.google.dagger:hilt-compiler:2.50'

    // Retrofit网络请求
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'

    // ZXing二维码
    implementation 'com.google.zxing:core:3.4.1'
    implementation 'com.journeyapps:zxing-android-embedded:4.2.0'

    // Glide图片加载
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    kapt 'com.github.bumptech.glide:compiler:4.16.0'

    // Lifecycle组件
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.7.0'

    // Kotlin协程
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3'

    // Material Design
    implementation 'com.google.android.material:material:1.11.0'

    // AndroidX核心库
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.activity:activity-ktx:1.8.2'
    implementation 'androidx.fragment:fragment-ktx:1.6.2'

    // JSON处理
    implementation 'com.google.code.gson:gson:2.10.1'

    // 权限处理
    implementation 'pub.devrel:easypermissions:3.0.0'
}
```

#### 5.3 在文件末尾添加：
```gradle
apply plugin: 'dagger.hilt.android.plugin'
```

#### 5.4 在项目级build.gradle中添加：
```gradle
buildscript {
    dependencies {
        classpath 'com.google.dagger:hilt-android-gradle-plugin:2.50'
    }
}
```

## ⚠️ 重要注意事项

### 依赖要求
- **Android API 21+** (Android 5.0及以上)
- **Kotlin 1.8+**
- **Hilt 2.50** (依赖注入框架)
- **ViewBinding启用** (必须在build.gradle中启用)

### 必需的ViewModel和Repository
这些Activity依赖以下类，需要确保项目中包含：
- `SplashViewModel.kt` - 启动页视图模型
- `LoginViewModel.kt` - 登录页视图模型
- `UserRepository.kt` - 用户数据仓库
- 相关的数据模型类 (User, LoginRequest, QRCodeResponse等)

### 权限要求
确保AndroidManifest.xml中包含以下权限：
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />
```

### Android Automotive优化特性
- **横屏布局优化** - 所有布局都针对车载横屏环境设计
- **大屏幕适配** - 按钮尺寸和字体大小适合车载大屏显示
- **触摸操作优化** - 按钮间距和大小适合车载触摸操作
- **全屏沉浸式体验** - 隐藏系统状态栏和导航栏

## 📝 功能特性详解

### 启动页功能
- **渐变动画效果** - 平滑的背景渐变和Logo淡入动画
- **Logo和文字分层动画** - 分层次的动画效果，提升视觉体验
- **自动检测登录状态** - 自动判断用户登录状态并跳转
- **性能优化和错误处理** - 完善的异常处理和性能优化

### 登录页功能
- **三种登录方式**：
  - 🔲 **扫码登录** - 网易云音乐APP扫码登录
  - 📱 **手机号登录** - 支持密码登录和验证码登录
  - 👤 **游客登录** - 无需注册直接体验
- **二维码自动生成和状态监控** - 实时监控二维码状态
- **手机号密码/验证码登录切换** - 灵活的登录方式切换
- **完整的错误处理和用户反馈** - 详细的错误提示和用户引导

### 车载环境适配
- **横屏布局优化** - 专为车载横屏环境设计
- **大屏幕适配** - 适配各种车载大屏尺寸
- **触摸操作优化** - 按钮大小和间距适合车载操作
- **全屏沉浸式体验** - 充分利用屏幕空间

## 🔧 自定义配置

### 修改主题颜色
在 `colors.xml` 中修改樱花主题颜色：
```xml
<color name="sakura_primary">#你的主色</color>
<color name="sakura_accent">#你的强调色</color>
<color name="sakura_background">#你的背景色</color>
```

### 修改应用信息
在 `strings.xml` 中修改应用相关文本：
```xml
<string name="app_name">你的应用名</string>
<string name="app_slogan">你的应用标语</string>
<string name="version_info">你的应用版本信息</string>
```

### 替换背景图片
替换 `cherry_blossom_car.jpg` 为你的背景图片：
- **建议尺寸**：1920x1080或更高
- **格式**：JPG或PNG
- **注意**：确保图片适合车载横屏显示

### 替换应用Logo
替换 `logo_music.png` 为你的应用Logo：
- **建议尺寸**：512x512像素
- **格式**：PNG（支持透明背景）
- **注意**：确保Logo在深色背景下清晰可见

## 🐛 常见问题解决

### 编译错误
1. **依赖版本不匹配**
   - 确保所有依赖版本与示例一致
   - 检查Kotlin版本是否为1.8+
   - 确保compileSdk和targetSdk版本正确

2. **包名引用错误**
   - 检查Activity类中的包名是否正确修改
   - 确保import语句中的包名引用正确
   - 检查AndroidManifest.xml中的Activity名称

3. **ViewBinding未启用**
   - 确保在app/build.gradle中启用了ViewBinding
   - 检查是否正确应用了Hilt插件

### 资源找不到错误
1. **布局资源缺失**
   - 检查所有布局文件是否正确复制到layout目录
   - 确保布局文件名称正确，无拼写错误

2. **图片资源缺失**
   - 检查PNG/JPG图片是否正确复制到drawable目录
   - 确保各分辨率的mipmap图标都已复制

3. **颜色和字符串资源未定义**
   - 检查colors.xml和strings.xml是否正确合并
   - 确保所有引用的资源ID都已定义

### 运行时错误
1. **ViewModel或Repository找不到**
   - 确保项目中包含所需的ViewModel和Repository类
   - 检查Hilt依赖注入配置是否正确

2. **网络权限问题**
   - 确保AndroidManifest.xml中添加了网络权限
   - 检查网络安全配置（Android 9+需要允许HTTP请求）

3. **二维码功能异常**
   - 确保添加了ZXing相关依赖
   - 检查相机权限是否正确添加

### 界面显示问题
1. **布局在车载设备上显示异常**
   - 确保使用了横屏布局
   - 检查约束布局的约束关系是否正确

2. **主题样式不生效**
   - 确保themes.xml正确合并到项目中
   - 检查AndroidManifest.xml中是否正确应用了主题

## 📞 技术支持

### 检查清单
在遇到问题时，请按以下顺序检查：

1. **文件复制完整性**
   - [ ] 所有Activity类文件已复制并修改包名
   - [ ] 所有布局文件已复制到正确目录
   - [ ] 所有图片资源已复制（包括PNG/JPG文件）
   - [ ] 样式资源已正确合并

2. **配置正确性**
   - [ ] AndroidManifest.xml配置已添加
   - [ ] Gradle依赖已正确添加
   - [ ] 权限已正确声明
   - [ ] 主题已正确应用

3. **依赖完整性**
   - [ ] 所需的ViewModel和Repository类存在
   - [ ] Hilt依赖注入配置正确
   - [ ] 网络请求相关配置正确

### 调试建议
1. **查看编译日志** - 仔细阅读编译错误信息
2. **检查Logcat** - 查看运行时错误日志
3. **逐步测试** - 先确保启动页正常，再测试登录功能
4. **网络调试** - 使用网络抓包工具检查API请求

### 性能优化建议
1. **图片优化** - 使用WebP格式减小图片大小
2. **动画优化** - 合理使用动画，避免过度动画影响性能
3. **内存管理** - 及时释放不需要的资源
4. **网络优化** - 合理设置网络超时时间

## 📋 版本兼容性

### Android版本支持
- **最低支持**：Android 5.0 (API 21)
- **目标版本**：Android 14 (API 34)
- **推荐版本**：Android 8.0+ (API 26+)

### 设备兼容性
- **手机设备**：完全支持
- **平板设备**：完全支持
- **Android Automotive**：专门优化
- **Android TV**：基本支持

### 屏幕适配
- **小屏幕**：320dp宽度以上
- **普通屏幕**：480dp宽度
- **大屏幕**：720dp宽度以上
- **超大屏幕**：960dp宽度以上

---

## 📄 许可证
本模块遵循原项目的许可证协议。

## 🤝 贡献
欢迎提交Issue和Pull Request来改进这个模块。

## 📧 联系方式
如有技术问题，请通过以下方式联系：
- 创建GitHub Issue
- 发送邮件至项目维护者

---

**祝您使用愉快！🚗🎵**
