# WhisperPlay项目品牌标识重构与合规性指南

## 📋 项目概述

**项目名称**: WhisperPlay (基于 PonyMusic 重构)
**应用显示名**: 轻聆
**重构时间**: 2025年6月11日
**原作者**: wangchenyan
**课程设计作者**: ckn
**版本**: 轻聆音乐 v1.0 (统一显示版本)
**内部版本**: 2.3.0-beta01 (gradle构建版本)
**版本代码**: 2030001

## 🎓 项目说明

本项目是Android应用开发课程设计作品。项目基于开源项目PonyMusic进行学习性重构和功能扩展，旨在通过实际项目开发提升Android开发技能和开源项目理解能力。

## 🙏 致谢

- 感谢原作者 [wangchenyan](https://github.com/wangchenyan) 提供的优秀开源项目PonyMusic
- 感谢相关音乐API项目提供的技术支持
- 感谢所有开源贡献者为教育和学习提供的无私支持

## ⚠️ 免责声明

- 本项目仅用于教育学习目的，不用于商业用途
- 音乐数据来源于第三方API服务，本项目不承担相关版权责任
- 用户使用本应用时应遵守相关法律法规和服务条款
- 本项目为测试版本，功能可能不稳定

## 🎯 重构目标

### 核心目标
1. **项目重命名**: PonyMusic → WhisperPlay
2. **应用显示名**: 保持"轻聆"不变
3. **包名重构**: `me.wcy.music` → `me.ckn.music`
4. **品牌重构**: 所有`wcy/wangchenyan`标识 → `ckn`（保留原作者版权信息）
5. **代码质量提升**: 添加规范的中英文注释
6. **架构优化**: 确保符合标准MVVM架构
7. **开源合规**: Apache License 2.0完全合规
8. **功能完整**: 确保应用正常编译运行

### 技术要求
- **包名**: `me.wcy.music` → `me.ckn.music`
- **APK输出**: `whisperplay-*.apk`
- **签名文件**: `ckn.keystore`
- **注释覆盖率**: 关键代码100%
- **架构合规**: 完全符合MVVM标准

## ⚠️ 重要注意事项

### 手动修复的必要性
**强烈建议采用手动修复方式，避免脚本批处理，原因如下：**

1. **精确性要求**
   - 包名修改涉及大量文件，需要逐一确认
   - 避免误修改第三方库的包名引用
   - 确保每个修改都是准确和必要的

2. **中文编码安全**
   - 项目包含大量中文注释和字符串
   - 脚本批处理可能导致编码损坏
   - 手动修改可以保证UTF-8编码完整性

3. **上下文理解**
   - 某些包名引用可能有特殊含义
   - 需要人工判断是否应该修改
   - 避免破坏业务逻辑

4. **质量控制**
   - 每次修改后可以立即验证
   - 发现问题可以及时回滚
   - 确保修改的准确性

### 中文编码处理规范

#### 文件编码要求
- **统一使用**: UTF-8 编码
- **BOM设置**: 无BOM (UTF-8 without BOM)
- **换行符**: LF (Unix风格)
- **IDE设置**: 确保Android Studio使用UTF-8编码

#### 中文内容处理
1. **注释中的中文**
   - 保持原有中文注释的完整性
   - 新增注释采用中英文对照格式
   - 避免使用特殊字符和表情符号

2. **字符串资源**
   - 检查strings.xml中的中文字符串
   - 确保显示名称"轻聆"正确保留
   - 验证中文字符在不同设备上的显示

3. **文档文件**
   - README.md等文档保持UTF-8编码
   - 中文内容格式化保持一致
   - 避免编码混乱导致的乱码

## 🏗️ 详细执行计划

### 阶段一：环境准备与基础配置

#### 1.1 创建任务记录
- **文件**: `./issues/Project_Refactor_WhisperPlay_Complete.md`
- **内容**: 完整重构过程记录和关键决策

#### 1.2 修改构建配置
**文件**: `app/build.gradle.kts`

```kotlin
android {
    namespace = "me.ckn.music"
    
    defaultConfig {
        applicationId = "me.ckn.music"
        // 其他配置保持不变
        
        applicationVariants.all {
            outputs.all {
                if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                    this.outputFileName = "whisperplay-$versionName.apk"
                }
            }
        }
    }
    
    signingConfigs {
        register("release") {
            storeFile = file("ckn.keystore")
            // 其他签名配置
        }
    }
}
```

#### 1.3 重命名签名文件
- **操作**: `wangchenyan.keystore` → `ckn.keystore`
- **更新**: `local.properties`中的签名配置

### 阶段二：核心代码与目录重构

#### 2.1 创建新目录结构
```
app/src/main/java/
├── me/ckn/music/          # 新的包结构
│   ├── account/           # 账户模块
│   ├── album/             # 专辑模块
│   ├── artist/            # 艺术家模块
│   ├── common/            # 通用模块
│   ├── discover/          # 发现模块
│   ├── main/              # 主模块
│   ├── search/            # 搜索模块
│   ├── service/           # 服务模块
│   └── utils/             # 工具模块
```

#### 2.2 手动更新包声明（重要：必须逐文件手动修改）

**⚠️ 严禁使用批量替换工具，必须手动逐文件修改**

**手动修改步骤**：
1. **使用Android Studio的重构功能**
   - 右键点击包名 → Refactor → Rename
   - 逐级重命名：me → me, wcy → ckn, music → music
   - 让IDE自动处理引用关系

2. **逐文件检查package声明**
   ```kotlin
   // 修改前
   package me.wcy.music.main.playing

   // 修改后
   package me.ckn.music.main.playing
   ```

3. **逐文件检查import语句**
   ```kotlin
   // 修改前
   import me.wcy.music.common.BaseMusicFragment
   import me.wcy.music.service.PlayerController

   // 修改后
   import me.ckn.music.common.BaseMusicFragment
   import me.ckn.music.service.PlayerController
   ```

4. **特别注意事项**
   - ✅ 修改：`me.wcy.music.*` 相关的包名
   - ❌ 不修改：`me.wcy.lrcview.*` (第三方库)
   - ❌ 不修改：`me.wcy.router.*` (第三方库)
   - ❌ 不修改：`top.wangchenyan.common.*` (第三方库)

5. **中文编码保护**
   - 修改前备份包含中文注释的文件
   - 使用Android Studio的"Safe Delete"功能
   - 修改后立即检查中文字符显示是否正常

#### 2.3 手动更新AndroidManifest.xml

**⚠️ 必须手动逐项检查和修改，确保中文字符串完整性**

**手动修改清单**：

1. **根节点package属性**
   ```xml
   <!-- 修改前 -->
   <manifest package="me.wcy.music">

   <!-- 修改后 -->
   <manifest package="me.ckn.music">
   ```

2. **Application组件**
   ```xml
   <!-- 修改前 -->
   <application android:name=".MusicApplication">

   <!-- 修改后 -->
   <application android:name=".MusicApplication">
   <!-- 注意：相对路径无需修改，绝对路径需要修改 -->
   ```

3. **Activity组件逐一检查**
   ```xml
   <!-- 修改前 -->
   <activity android:name="me.wcy.music.main.MainActivity">

   <!-- 修改后 -->
   <activity android:name="me.ckn.music.main.MainActivity">
   ```

4. **Service组件逐一检查**
   ```xml
   <!-- 修改前 -->
   <service android:name="me.wcy.music.service.MusicService">

   <!-- 修改后 -->
   <service android:name="me.ckn.music.service.MusicService">
   ```

5. **中文字符串保护**
   - 应用名称"轻聆"必须保持不变
   - 检查所有android:label中的中文
   - 确保中文描述信息完整性

6. **验证步骤**
   - 修改后立即编译验证
   - 检查中文字符是否显示正常
   - 确认所有组件路径正确

### 阶段三：代码注释规范化

#### 3.1 文件级注释标准
```kotlin
/**
 * WhisperPlay Music Player
 * 
 * 文件描述：[具体功能描述]
 * File Description: [English description]
 * 
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
```

#### 3.2 类级注释标准
```kotlin
/**
 * [类名中文描述]
 * [Class English Description]
 * 
 * 主要功能：
 * Main Functions:
 * - [功能1] / [Function 1]
 * - [功能2] / [Function 2]
 * 
 * 使用示例：
 * Usage Example:
 * ```kotlin
 * val example = ClassName()
 * example.method()
 * ```
 * 
 * @author ckn
 */
```

#### 3.3 方法级注释标准
```kotlin
/**
 * [方法功能描述]
 * [Method function description]
 * 
 * @param [参数名] [参数描述] / [Parameter description]
 * @return [返回值描述] / [Return value description]
 * @throws [异常类型] [异常描述] / [Exception description]
 */
```

#### 3.4 需要添加注释的关键文件
- **ViewModel类**: 业务逻辑和状态管理
- **Repository类**: 数据访问层
- **Service类**: 后台服务
- **Utils类**: 工具类方法
- **Fragment/Activity**: UI控制器
- **自定义View**: UI组件
- **API接口**: 网络接口定义

### 阶段四：MVVM架构检查与优化

#### 4.1 架构层次标准
```
View Layer (视图层)
├── Activity/Fragment: UI展示和用户交互
├── ViewBinding: 视图绑定
└── Custom Views: 自定义控件

ViewModel Layer (视图模型层)
├── ViewModel: 管理UI数据和状态
├── LiveData/StateFlow: 数据观察
└── 业务逻辑处理

Model Layer (模型层)
├── Repository: 数据仓库模式
├── DataSource: 数据源（本地/远程）
├── Entity: 数据实体
└── Database/Network: 数据访问
```

#### 4.2 MVVM合规性检查项
- ✅ View不直接访问Model
- ✅ ViewModel不持有View引用
- ✅ 使用LiveData/StateFlow进行数据绑定
- ✅ Repository模式统一数据访问
- ✅ 依赖注入（Hilt）正确使用

#### 4.3 需要检查的关键文件
- `*ViewModel.kt`: 确保继承BaseViewModel
- `*Fragment.kt`: 确保正确使用ViewBinding和ViewModel
- `*Repository.kt`: 确保数据访问层分离
- `*DataSource.kt`: 确保数据源抽象

### 阶段五：wcy依赖库配置调整

#### 5.1 crouter配置调整
**文件**: `app/build.gradle.kts`
```kotlin
ksp {
    arg("moduleName", project.name)
    arg("defaultScheme", "app")
    arg("defaultHost", "whisperplay")  // 修改为whisperplay
    arg("room.schemaLocation", "$projectDir/schemas")
}
```

**文件**: `MusicApplication.kt`
```kotlin
private fun initCRouter() {
    CRouter.setRouterClient(
        RouterClient.Builder()
            .baseUrl("app://whisperplay")  // 修改为whisperplay
            .loginProvider { context, callback ->
                // 登录逻辑保持不变
            }
            .fragmentContainerIntentProvider {
                Intent(it, MusicFragmentContainerActivity::class.java)
            }
            .build()
    )
}
```

#### 5.2 依赖库兼容性确认
- ✅ **android-common** (`top.wangchenyan.common.*`): 无需调整
- ✅ **lrcview** (`me.wcy.lrcview.*`): 无需调整
- 🔧 **crouter** (`me.wcy.router.*`): 仅需配置调整

## 第六阶段：品牌标识重构（合规版）

### 6.1 文件头注释合规处理

#### 6.1.1 标准注释模板
```kotlin
/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on [原始日期]
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：[具体功能描述]
 * File Description: [English description]
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
```

#### 6.1.2 需要处理的文件清单
1. `app/src/main/java/me/ckn/music/service/likesong/LikeSongProcessorImpl.kt` - 第16行
2. `app/src/main/java/me/ckn/music/consts/FilePath.kt` - 第7行
3. `app/src/main/java/me/ckn/music/storage/LrcCache.kt` - 第13行
4. 其他通过搜索发现的文件

#### 6.1.3 搜索命令
```bash
# 搜索所有包含原作者信息的文件
grep -r "Created by wangchenyan" app/src/main/java/ --include="*.kt"
grep -r "wangchenyan.top" app/src/main/java/ --include="*.kt"
grep -r "@author.*wcy" app/src/main/java/ --include="*.kt"
```

### 6.2 README.md合规更新

#### 6.2.1 README.md 标准模板
```markdown
# WhisperPlay (轻聆)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Version](https://img.shields.io/badge/Version-v1.0-orange.svg)](#)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)

> 基于 PonyMusic 的 Android 音乐播放器 - 课程设计项目

## 📖 简介

WhisperPlay（轻聆）是一个基于 [PonyMusic](https://github.com/wangchenyan/ponymusic) 重构的 Android 音乐播放器应用，采用现代化的 MVVM 架构和 Material Design 设计语言。

### ✨ 主要特性

- 🎵 本地音乐播放
- 🎤 歌词显示与同步
- 🌙 夜间模式支持
- 📱 Material Design UI
- 🔄 MVVM 架构模式

### 🛠️ 技术栈

- **语言**: Kotlin
- **架构**: MVVM + Repository
- **UI**: Material Design Components
- **播放器**: Media3 + ExoPlayer
- **依赖注入**: Hilt
- **数据库**: Room
- **网络**: Retrofit

## 🚀 快速开始

### 环境要求

- Android Studio Arctic Fox 或更高版本
- Android SDK 21+ (Android 5.0+)
- Kotlin 1.8+

### 构建步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/your-username/whisperplay.git
   cd whisperplay
   ```

2. **配置环境**
   - 确保 Android SDK 已正确安装
   - 同步项目依赖

3. **编译运行**
   ```bash
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```

## 📄 许可证

本项目基于 [Apache License 2.0](LICENSE) 开源协议。

```
Copyright 2024 wangchenyan (original PonyMusic)
Copyright 2025 ckn (WhisperPlay modifications)

Licensed under the Apache License, Version 2.0
```

## 🙏 致谢

- 感谢 [wangchenyan](https://github.com/wangchenyan) 提供的优秀基础项目 PonyMusic
- 感谢所有开源贡献者为教育和学习提供的支持

## ⚠️ 免责声明

- 本项目仅用于教育学习目的，不用于商业用途
- 音乐数据来源于第三方服务，本项目不承担相关版权责任
- 本项目为测试版本，功能可能不稳定
- 使用时请遵守相关法律法规和服务条款

---

**注意**: 这是一个课程设计项目，基于开源项目 PonyMusic 进行学习性重构。
```

#### 6.2.2 版权声明更新
```markdown
## License

    Copyright 2024 wangchenyan (original PonyMusic)
    Copyright 2025 ckn (WhisperPlay modifications)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```

### 6.3 版本信息统一

#### 6.3.1 版本显示现状
项目中的版本显示保持现有格式：
- **Splash页面**: "Version 1.0" (简洁英文格式)
- **登录页面**: "轻聆音乐 v1.0" (完整中文格式)
- **文档统一版本**: 轻聆音乐 v1.0
- **内部构建版本**: 2.3.0-beta01 (gradle/libs.versions.toml)

#### 6.3.2 版本显示保持现状

**Splash 页面版本显示**（保持不变）：
```xml
<!-- 文件: app/src/main/res/layout/activity_splash.xml 第68行 -->
<!-- 保持现有格式，无需修改 -->
<TextView
    android:text="Version 1.0"
    ... />
```

**登录页面版本显示**（保持不变）：
```xml
<!-- 文件: app/src/main/res/layout/activity_login.xml 第210行 -->
<!-- 保持现有格式，无需修改 -->
<TextView
    android:text="轻聆音乐 v1.0"
    ... />
```

#### 6.3.3 版本信息说明
- **用户显示版本**: 轻聆音乐 v1.0 (简洁易懂)
- **开发构建版本**: 2.3.0-beta01 (技术版本号)
- **版本代码**: 2030001 (内部版本控制)

### 6.4 个人信息替换规则

#### 6.4.1 可以替换的内容
- 个人联系方式（邮箱、微博等）
- 个人网站链接
- 新增的功能说明
- 应用下载链接

#### 6.4.2 必须保留的内容
- 原始版权声明
- "Created by wangchenyan.top"等创建信息
- 原作者的技术贡献说明
- 第三方库的包名引用

#### 6.4.3 需要添加的内容
- 修改说明："Modified for WhisperPlay by ckn on 2025-06-11"
- 新的版权声明
- 项目关系说明

## 第七阶段：合规性审查与验证（增强版）

### 7.1 LICENSE文件创建

#### 7.1.1 创建LICENSE文件
```apache
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.

      "You" (or "Your") shall mean an individual or Legal Entity
      exercising permissions granted by this License.

      "Source" form shall mean the preferred form for making modifications,
      including but not limited to software source code, documentation
      source, and configuration files.

      "Object" form shall mean any form resulting from mechanical
      transformation or translation of a Source form, including but
      not limited to compiled object code, generated documentation,
      and conversions to other media types.

      "Work" shall mean the work of authorship, whether in Source or
      Object form, made available under the License, as indicated by a
      copyright notice that is included in or attached to the work
      (which shall not include communications that are solely written
      by individuals acting on behalf of a copyright holder).

      "Derivative Works" shall mean any work, whether in Source or Object
      form, that is based upon (or derived from) the Work and for which the
      editorial revisions, annotations, elaborations, or other modifications
      represent, as a whole, an original work of authorship. For the purposes
      of this License, Derivative Works shall not include works that remain
      separable from, or merely link (or bind by name) to the interfaces of,
      the Work and derivative works thereof.

      "Contribution" shall mean any work of authorship, including
      the original version of the Work and any modifications or additions
      to that Work or Derivative Works thereof, that is intentionally
      submitted to Licensor for inclusion in the Work by the copyright owner
      or by an individual or Legal Entity authorized to submit on behalf of
      the copyright owner. For the purposes of this definition, "submitted"
      means any form of electronic, verbal, or written communication sent
      to the Licensor or its representatives, including but not limited to
      communication on electronic mailing lists, source code control
      systems, and issue tracking systems that are managed by, or on behalf
      of, the Licensor for the purpose of discussing and improving the Work,
      but excluding communication that is conspicuously marked or otherwise
      designated in writing by the copyright owner as "Not a Contribution."

      "Contributor" shall mean Licensor and any individual or Legal Entity
      on behalf of whom a Contribution has been received by Licensor and
      subsequently incorporated within the Work.

   2. Grant of Copyright License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      copyright license to use, reproduce, modify, sublicense, and distribute
      the Work and in derivative works thereof.

   3. Grant of Patent License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      (except as stated in this section) patent license to make, have made,
      use, offer to sell, sell, import, and otherwise transfer the Work,
      where such license applies only to those patent claims licensable
      by such Contributor that are necessarily infringed by their
      Contribution(s) alone or by combination of their Contribution(s)
      with the Work to which such Contribution(s) was submitted. If You
      institute patent litigation against any entity (including a
      cross-claim or counterclaim in a lawsuit) alleging that the Work
      or a Contribution incorporated within the Work constitutes direct
      or contributory patent infringement, then any patent licenses
      granted to You under this License for that Work shall terminate
      as of the date such litigation is filed.

   4. Redistribution. You may reproduce and distribute copies of the
      Work or Derivative Works thereof in any medium, with or without
      modifications, and in Source or Object form, provided that You
      meet the following conditions:

      (a) You must give any other recipients of the Work or
          Derivative Works a copy of this License; and

      (b) You must cause any modified files to carry prominent notices
          stating that You changed the files; and

      (c) You must retain, in the Source form of any Derivative Works
          that You distribute, all copyright, patent, trademark, and
          attribution notices from the Source form of the Work,
          excluding those notices that do not pertain to any part of
          the Derivative Works; and

      (d) If the Work includes a "NOTICE" text file as part of its
          distribution, then any Derivative Works that You distribute must
          include a readable copy of the attribution notices contained
          within such NOTICE file, excluding those notices that do not
          pertain to any part of the Derivative Works, in at least one
          of the following places: within a NOTICE text file distributed
          as part of the Derivative Works; within the Source form or
          documentation, if provided along with the Derivative Works; or,
          within a display generated by the Derivative Works, if and
          wherever such third-party notices normally appear. The contents
          of the NOTICE file are for informational purposes only and
          do not modify the License. You may add Your own attribution
          notices within Derivative Works that You distribute, alongside
          or as an addendum to the NOTICE text from the Work, provided
          that such additional attribution notices cannot be construed
          as modifying the License.

      You may add Your own copyright notice to Your modifications and
      may provide additional or different license terms and conditions
      for use, reproduction, or distribution of Your modifications, or
      for any such Derivative Works as a whole, provided Your use,
      reproduction, and distribution of the Work otherwise complies with
      the conditions stated in this License.

   5. Submission of Contributions. Unless You explicitly state otherwise,
      any Contribution intentionally submitted for inclusion in the Work
      by You to the Licensor shall be under the terms and conditions of
      this License, without any additional terms or conditions.
      Notwithstanding the above, nothing herein shall supersede or modify
      the terms of any separate license agreement you may have executed
      with Licensor regarding such Contributions.

   6. Trademarks. This License does not grant permission to use the trade
      names, trademarks, service marks, or product names of the Licensor,
      except as required for reasonable and customary use in describing the
      origin of the Work and reproducing the content of the NOTICE file.

   7. Disclaimer of Warranty. Unless required by applicable law or
      agreed to in writing, Licensor provides the Work (and each
      Contributor provides its Contributions) on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
      implied, including, without limitation, any warranties or conditions
      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
      PARTICULAR PURPOSE. You are solely responsible for determining the
      appropriateness of using or redistributing the Work and assume any
      risks associated with Your exercise of permissions under this License.

   8. Limitation of Liability. In no event and under no legal theory,
      whether in tort (including negligence), contract, or otherwise,
      unless required by applicable law (such as deliberate and grossly
      negligent acts) or agreed to in writing, shall any Contributor be
      liable to You for damages, including any direct, indirect, special,
      incidental, or consequential damages of any character arising as a
      result of this License or out of the use or inability to use the
      Work (including but not limited to damages for loss of goodwill,
      work stoppage, computer failure or malfunction, or any and all
      other commercial damages or losses), even if such Contributor
      has been advised of the possibility of such damages.

   9. Accepting Warranty or Support. You may choose to offer, and to
      charge a fee for, warranty, support, indemnity or other liability
      obligations and/or rights consistent with this License. However, in
      accepting such obligations, You may act only on Your own behalf and on
      Your sole responsibility, not on behalf of any other Contributor, and
      only if You agree to indemnify, defend, and hold each Contributor
      harmless for any liability incurred by, or claims asserted against,
      such Contributor by reason of your accepting any such warranty or support.

   END OF TERMS AND CONDITIONS

   APPENDIX: How to apply the Apache License to your work.

      To apply the Apache License to your work, attach the following
      boilerplate notice, with the fields enclosed by brackets "[]"
      replaced with your own identifying information. (Don't include
      the brackets!)  The text should be enclosed in comments for the
      particular file format. We also recommend that a file or class name
      and description of purpose be included on the same page as the
      copyright notice for easier identification within third-party archives.

   Copyright 2024 wangchenyan (original PonyMusic)
   Copyright 2025 ckn (WhisperPlay v1.0 modifications)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

### 7.2 NOTICE文件创建（可选但推荐）

#### 7.2.1 创建NOTICE文件（按GitHub官方格式）
```
WhisperPlay
Copyright 2025 ckn

This product includes software developed by wangchenyan (PonyMusic).
Original project: https://github.com/wangchenyan/ponymusic

================================================================================

THIRD-PARTY SOFTWARE NOTICES AND INFORMATION

This project incorporates components from the projects listed below.

================================================================================

1. Music API Service
   Licensed under the MIT License

2. Media3 and ExoPlayer
   Copyright (C) 2016 The Android Open Source Project
   Licensed under the Apache License, Version 2.0

3. Retrofit
   Copyright 2013 Square, Inc.
   Licensed under the Apache License, Version 2.0

4. Room
   Copyright 2017 The Android Open Source Project
   Licensed under the Apache License, Version 2.0

5. Hilt
   Copyright 2020 The Android Open Source Project
   Licensed under the Apache License, Version 2.0

6. Glide
   Copyright 2014 Google, Inc. All rights reserved.
   Licensed under the BSD, part MIT and Apache 2.0

7. wangchenyan/crouter
   Copyright (c) 2018 wangchenyan
   Licensed under the Apache License, Version 2.0

8. wangchenyan/lrcview
   Copyright (c) 2016 wangchenyan
   Licensed under the Apache License, Version 2.0

9. wangchenyan/android-common
   Copyright (c) 2018 wangchenyan
   Licensed under the Apache License, Version 2.0

10. wangchenyan/radapter3
    Copyright (c) 2018 wangchenyan
    Licensed under the Apache License, Version 2.0

================================================================================
```

### 7.3 开源合规性检查清单

#### 7.3.1 许可证合规检查
- [ ] LICENSE文件已创建并包含完整的Apache License 2.0文本
- [ ] 版权声明包含原作者和新作者信息
- [ ] README.md中包含项目关系说明和致谢
- [ ] 所有修改的文件都标注了修改信息
- [ ] 保留了原作者的创建信息

#### 7.3.2 第三方依赖许可证审查
- [ ] 检查所有第三方库的许可证兼容性
- [ ] 确认wangchenyan的库（crouter、lrcview等）使用Apache License 2.0
- [ ] 验证网易云API项目的使用合规性
- [ ] 检查Media3、Retrofit等库的许可证要求

#### 7.3.3 品牌和商标检查
- [ ] 确认不使用原作者的商标或品牌标识
- [ ] 应用名称"轻聆"不与原项目冲突
- [ ] 包名已完全更改为me.ckn.music
- [ ] 应用图标和界面设计已区分

#### 7.3.4 代码归属检查
- [ ] 所有新增代码标注正确的作者信息
- [ ] 修改的代码保留原作者信息并添加修改说明
- [ ] 删除的功能不影响原项目的完整性引用

### 7.4 最终验证步骤

#### 7.4.1 编译和功能验证
```bash
# 清理构建
./gradlew clean

# 编译验证
./gradlew assembleDebug --console=plain --no-daemon

# 安装和启动验证
./gradlew installDebug
adb shell am start -n me.ckn.music/.splash.SplashActivity
```

#### 7.4.2 合规性最终检查
```bash
# 检查是否还有未处理的原作者信息
grep -r "Created by wangchenyan" app/src/main/java/ --include="*.kt" | grep -v "Original:"
grep -r "wangchenyan.top" app/src/main/java/ --include="*.kt" | grep -v "Original:"

# 检查版权声明
grep -r "Copyright.*wangchenyan" . --include="*.md" --include="LICENSE"
```

#### 7.4.3 功能测试检查项
- [ ] 应用正常启动
- [ ] 音乐播放功能
- [ ] 歌词显示功能
- [ ] 路由跳转功能
- [ ] 数据库访问功能
- [ ] 网络请求功能
- [ ] 用户界面响应

#### 7.4.4 第三方API免责声明验证
- [ ] README.md中包含API免责声明
- [ ] 应用内显示适当的免责信息
- [ ] 用户协议中包含第三方服务条款
- [ ] 明确标注教育用途和非商业性质

## 🎯 最终交付标准

### 合规性要求
1. **开源许可证合规**: 完全符合Apache License 2.0要求
2. **版权声明完整**: 包含原作者和修改者信息
3. **第三方组件声明**: NOTICE文件包含所有依赖
4. **免责声明完备**: 涵盖API使用和教育用途
5. **品牌标识合规**: 保留原作者贡献，添加修改说明

### 技术要求
1. **功能完整性**: 100%保持原有功能
2. **编译成功率**: 100%无错误无警告
3. **注释覆盖率**: 关键代码100%注释
4. **架构合规性**: 完全符合MVVM标准
5. **中文内容完整**: UTF-8编码无损

## 🔧 执行顺序

**第六阶段** → **第七阶段** → **最终验证**

**重要提醒**: 每个步骤完成后必须进行编译验证，确保无错误后方可继续下一步骤。

## � 执行检查清单

### 第六阶段检查项
- [ ] 文件头注释已按合规模板更新
- [ ] README.md已包含项目说明和致谢
- [ ] 个人信息替换符合规则
- [ ] 原作者信息得到保留
- [ ] 修改说明已正确添加

### 第七阶段检查项
- [ ] LICENSE文件已创建
- [ ] NOTICE文件已创建（可选）
- [ ] 开源合规性检查完成
- [ ] 第三方依赖许可证已审查
- [ ] 编译和功能验证通过
- [ ] 第三方API免责声明已添加

### 最终验证检查项
- [ ] 应用正常启动和运行
- [ ] 所有功能保持完整
- [ ] 中文内容显示正常
- [ ] 版权声明完整准确
- [ ] 开源协议完全合规

## � 重要注意事项

### 中文编码保护
1. **确保UTF-8编码**：所有文件修改时保持UTF-8编码
2. **应用名称保护**：应用显示名"轻聆"必须保持不变
3. **逐文件检查**：每个文件修改后立即检查中文显示

### 第三方库保护
1. **禁止修改第三方库包名**：
   - `top.wangchenyan.common.*` 保持不变
   - `me.wcy.lrcview.*` 保持不变
   - `me.wcy.router.*` 保持不变

2. **API免责声明**：
   - 音乐数据来源于第三方API
   - 本项目不承担相关版权责任
   - 仅用于教育学习目的

### 备份策略
```bash
# 执行前创建备份分支
git checkout -b backup-before-brand-refactor
git add .
git commit -m "Backup before brand refactor - 2025-06-11"
```

## 📋 最终验证清单

### 合规性验证
- [ ] LICENSE文件已创建并包含完整Apache License 2.0
- [ ] README.md包含项目说明、致谢和免责声明
- [ ] 文件头注释按合规模板更新
- [ ] 版权声明包含原作者和修改者信息
- [ ] 第三方API免责声明已添加

### 功能验证
- [ ] 应用正常编译无错误无警告
- [ ] 应用正常启动和运行
- [ ] 所有音乐播放功能正常
- [ ] 中文内容显示正常
- [ ] 第三方库正常工作

### 品牌验证
- [ ] 包名已完全更改为me.ckn.music
- [ ] 应用显示名"轻聆"保持不变
- [ ] 原作者信息得到保留和致谢
- [ ] 修改说明已正确添加

---

## 📝 文档信息

**文档标题**: WhisperPlay项目品牌标识重构与合规性指南
**创建时间**: 2025年6月11日
**文档作者**: ckn
**项目性质**: Android应用开发课程设计
**基于项目**: PonyMusic by wangchenyan
**开源协议**: Apache License 2.0
**文档版本**: 轻聆音乐 v1.0

**特别说明**: 本指南严格遵循Apache License 2.0要求，保护原作者权利，确保开源合规性。适用于教育学习目的的项目重构。
