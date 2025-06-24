# WhisperPlay (轻聆)

[![构建状态](https://img.shields.io/github/actions/workflow/status/your-username/your-repo/android.yml?branch=main&style=for-the-badge)](https://github.com/your-username/your-repo/actions)
[![许可证](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge)](https://opensource.org/licenses/Apache-2.0)
[![版本](https://img.shields.io/github/v/release/your-username/your-repo?style=for-the-badge)](https://github.com/your-username/your-repo/releases)
[![平台](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=21)

**WhisperPlay (轻聆)** 是一款追求极致聆听体验的现代化 Android 音乐播放器。本项目是对经典的 [PonyMusic](https://github.com/wangchenyan/ponymusic) 项目的全面重构，旨在应用业界前沿的 Android 开发技术，构建一个拥有清晰 MVVM 架构和优美用户界面的、可用于学习和实践的现代化应用。

---

## ✨ 主要特性

- **🎵 高保真播放**: 基于 `Media3 (ExoPlayer)` 内核，提供稳定、高效的音频播放体验。
- **🎤 同步歌词**: 支持实时同步的歌词显示。
- **🎨 Material You 设计**: 优美且响应迅速的 UI，完美适配您的设备。
- **🌙 深色模式**: 提供舒适的夜间使用体验。
- **📂 本地音乐管理**: 轻松扫描和管理您的本地音频文件。
- **🚀 现代化架构**: 采用最新的 Android Jetpack 组件和最佳实践构建。
- **🚗 Android Automotive OS 支持**: 为车载信息娱乐系统进行了特别优化。

---

## 📸 应用截图

*(您可以在此处添加应用截图，例如：)*

| 主屏幕 | 播放页 | 深色模式 |
| :---: | :---: | :---: |
| [添加截图] | [添加截图] | [添加截图] |

---

## 🛠️ 技术栈与架构

本项目采用现代化的技术栈，以确保其健壮性、可扩展性和可维护性。

| 组件 | 技术 |
| :--- | :--- |
| **开发语言** | [Kotlin](https://kotlinlang.org/) |
| **项目架构** | [MVVM](https://developer.android.com/jetpack/guide) (Model-View-ViewModel) + Repository |
| **UI 框架** | [Material Design 3](https://m3.material.io/) |
| **异步处理** | [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) |
| **依赖注入**| [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) |
| **播放器内核** | [Media3 (ExoPlayer)](https://developer.android.com/guide/topics/media/media3) |
| **数据库** | [Room](https://developer.android.com/training/data-storage/room) |
| **网络请求** | [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) |

### 架构图
*(可在此处放置一张简洁的架构图来说明数据流)*
`View -> ViewModel -> Repository -> (Local/Remote DataSource)`

---

## 🚀 快速上手

### 环境要求
- Android Studio Iguana | 2023.2.1 或更高版本
- JDK 17
- Android SDK API Level 21+

### 快速体验 (推荐)
您可以从 [Releases](https://github.com/your-username/your-repo/releases) 页面下载最新的 APK 文件并直接安装。

### 从源码构建
1.  **克隆仓库:**
    ```bash
    git clone https://github.com/your-username/WhisperPlay.git
    cd WhisperPlay
    ```
2.  **在 Android Studio 中打开:**
    - 打开 Android Studio，选择 `Open an existing project`。
    - 导航至克隆下来的项目目录并打开。
3.  **同步并运行:**
    - 等待 Android Studio 完成 Gradle 项目同步。
    - 在模拟器或真实设备上运行 `app` 配置。

---

## 🆚 与 PonyMusic 对比

本项目不仅是一个分支，更是一次彻底的现代化重构。以下是主要区别：

| 特性 | PonyMusic (原版) | WhisperPlay (本项目) |
| :--- | :--- | :--- |
| **架构模式** | MVP | **MVVM + Repository** |
| **开发语言** | Java | **100% Kotlin** |
| **依赖注入**| 手动管理 | **Hilt** |
| **UI 风格** | Material Design | **Material Design 3** |
| **异步方案** | AsyncTask / RxJava | **Kotlin Coroutines & Flow** |
| **播放器内核** | MediaPlayer | **Media3 (ExoPlayer)** |
| **数据库** | GreenDAO | **Room** |
| **车载系统支持**| 无 | **有** |

---

## 🙏 致谢

- 非常感谢 [wangchenyan](https://github.com/wangchenyan) 创建了优秀的 [PonyMusic](https://github.com/wangchenyan/ponymusic) 项目，它为本此学习性重构提供了坚实的基础。
- 感谢所有为本项目提供支持的开源库及其贡献者。

---

## 📄 许可证

本项目基于 Apache License, Version 2.0 许可证开源。详情请参阅 [LICENSE](LICENSE) 文件。

```
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
