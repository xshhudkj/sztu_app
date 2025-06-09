@echo off
REM 简化的 Gradle Wrapper 启动脚本
REM 使用方法: gw installDebug 或 gw assembleDebug

set "GRADLE_USER_HOME=D:\Android Studio"
gradlew.bat %*
