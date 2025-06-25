# WhisperPlay 语音控制功能

## 📋 功能概述

WhisperPlay音乐播放器的语音控制功能，支持通过语音命令控制音乐播放，专为Android Automotive平台优化。

## 🏗️ 架构设计

### 核心组件

1. **VoiceControlViewModel** - 语音控制的核心ViewModel
   - 管理语音控制状态
   - 处理语音命令
   - 协调各个组件

2. **BaiduSpeechManager** - 百度语音SDK管理器
   - 语音识别
   - 语音合成
   - 唤醒词检测

3. **VoiceControlState** - 语音控制状态数据类
   - 语音功能开关状态
   - 监听状态
   - 权限状态
   - 错误信息

4. **VoiceControlEvent** - 语音控制事件密封类
   - 权限结果事件
   - 唤醒词检测事件
   - 命令识别事件
   - 识别失败事件
   - 语音合成完成事件

## 🎯 支持的语音命令

### 🔊 唤醒词（对话模式）
- "你好小聆"
- "小聆同学"

### ⚡ 直接命令（无需唤醒词）
- "播放" - 开始播放音乐
- "暂停" - 暂停播放
- "下一首" - 播放下一首歌曲
- "上一首" - 播放上一首歌曲
- "随机播放" - 切换到随机播放模式
- "循环播放" - 切换到循环播放模式
- "增大音量" - 提高音量
- "减小音量" - 降低音量

### 🎵 播放控制命令（对话模式）
用户先说唤醒词，系统回复"我在"后，再说以下命令：
- "播放" - 开始播放音乐
- "暂停" - 暂停播放
- "下一首" - 播放下一首歌曲
- "上一首" - 播放上一首歌曲
- "随机播放" - 切换到随机播放模式
- "循环播放" - 切换到循环播放模式
- "增大音量" - 提高音量
- "减小音量" - 降低音量

## 🔧 集成说明

### 1. 权限配置

在 `AndroidManifest.xml` 中添加必要权限：

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
<uses-permission android:name="android.permission.INTERNET" />
```

### 2. 依赖注入

使用Hilt进行依赖注入，确保BaiduSpeechManager正确初始化。

### 3. UI集成

在播放页面布局中已包含语音控制按钮：
- 位置：进度条上方中央
- 状态：支持开启/关闭状态切换
- 动画：点击缩放动画效果

### 4. 设置页面

在设置页面添加了语音控制相关选项：
- "进入播放页自动开启语音模式" - 控制是否自动启用语音功能

## 🚀 使用流程

### 📱 智能双模式语音控制

#### 🔄 **直接操作响应模式**
1. **待机监听** - 系统持续监听直接命令
2. **直接识别** - 用户直接说"播放"、"暂停"等命令
3. **成功识别** - 播放"滴"提示音 → 执行对应操作
4. **识别失败** - 自动重新开始监听（无语音提示）

#### 💬 **对话模式响应**
1. **唤醒检测** - 用户说"你好小聆"或"小聆同学"
2. **语音确认** - 系统播放"滴"提示音 + 语音回复"我在"
3. **命令等待** - 暂停当前音乐，进入命令识别状态
4. **命令识别** - 用户说具体命令（如"播放"、"下一首"）
5. **执行反馈** - 播放"滴"提示音 → 执行命令 → 恢复音乐播放
6. **识别失败** - 播放"听不清，请再说一遍" → 恢复音乐 → 返回待机状态

### 🎵 **音频播放控制**
- **语音交互期间** - 自动暂停当前音乐播放
- **语音操作完成后** - 自动恢复音乐播放
- **MediaPlayer/ExoPlayer状态同步** - 确保与UI状态一致

## 🔍 测试

### 单元测试
- VoiceControlViewModelTest - ViewModel逻辑测试
- BaiduSpeechManagerTest - 语音SDK管理器测试

### 集成测试
- VoiceControlIntegrationTest - 组件协作测试

## 📱 Android Automotive 优化

1. **大屏适配** - 语音控制按钮针对车载大屏优化
2. **触摸友好** - 48dp最小触摸区域
3. **视觉反馈** - 清晰的状态指示
4. **免手操作** - 支持完全语音控制

## 🛠️ 开发注意事项

1. **权限处理** - 使用项目统一的Permissioner库
2. **状态管理** - 通过StateFlow管理状态变化
3. **错误处理** - 完善的错误处理和用户反馈
4. **性能优化** - 合理的资源管理和内存控制
5. **线程安全** - 所有操作都在适当的线程中执行

## 📄 相关文件

### 核心文件
- `VoiceControlViewModel.kt` - 语音控制ViewModel
- `BaiduSpeechManager.kt` - 百度语音SDK管理器
- `VoiceControlState.kt` - 状态数据类
- `VoiceControlEvent.kt` - 事件密封类

### UI文件
- `activity_playing_control.xml` - 播放控制布局（包含语音按钮）
- `preference_setting.xml` - 设置页面布局

### 资源文件
- `ic_voice_control_*.xml` - 语音控制图标
- `voice_control_tint_selector.xml` - 颜色选择器

### 扩展文件
- `PermissionerExtensions.kt` - 权限管理扩展
