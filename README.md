# BackgroundImagePlus - IntelliJ IDEA 背景图片插件

[![JetBrains Plugin](https://img.shields.io/badge/JetBrains-Marketplace-blue)](https://plugins.jetbrains.com)
[![IntelliJ Platform](https://img.shields.io/badge/IntelliJ-Platform-orange)](https://plugins.jetbrains.com/docs/intellij)

一个简单易用的IntelliJ IDEA背景图片插件，支持自动切换壁纸功能。

## 功能特性

- 🖼️ **自动壁纸切换** - 从指定文件夹自动循环切换背景图片
- ⚙️ **简单配置** - 只需选择壁纸文件夹即可使用
- 🎛️ **灵活调节** - 支持透明度和切换间隔的精确调节
- 🌍 **多语言支持** - 支持中文和英文界面
- 🔧 **开箱即用** - 安装后自动生效，无需复杂配置
- 🎯 **快捷菜单** - View菜单下的专用功能组，支持快速操作

## 使用方法

### 基本配置
1. 安装插件后，在IDEA设置中找到 **外观与行为** → **背景图片增强版**
2. 选择包含壁纸图片的文件夹
3. 调整透明度（0-100）和切换间隔（1-60分钟）
4. 点击应用，立即生效！

### 快捷菜单操作
在IDEA的 **View** 菜单中找到 **Background Image Plus** 子菜单，可进行以下操作：
- **Toggle Auto Switch** - 暂停或恢复自动壁纸切换
- **Next Image** - 立即显示下一张壁纸
- **Clear Background** - 移除当前背景图片

## 项目结构

```
src/
├── main/
│   ├── kotlin/com/kody/backgroundimageplus/
│   │   ├── actions/
│   │   │   ├── ToggleAutoSwitchAction.kt         # 切换自动切换动作
│   │   │   ├── ShowNextImageAction.kt            # 显示下一张图片动作
│   │   │   └── ClearBackgroundAction.kt          # 清空背景动作
│   │   ├── config/
│   │   │   ├── BackgroundImageConfigurable.kt    # 配置界面
│   │   │   └── BackgroundImageSettings.kt        # 设置存储
│   │   ├── listener/
│   │   │   └── StartupActivity.kt                # 启动监听器
│   │   ├── service/
│   │   │   └── BackgroundImageManager.kt         # 核心服务
│   │   └── MyMessageBundle.kt                    # 多语言支持
│   └── resources/
│       ├── META-INF/
│       │   └── plugin.xml                        # 插件配置
│       └── messages/
│           ├── MyMessageBundle.properties        # 英文资源
│           └── MyMessageBundle_zh.properties     # 中文资源
```

## 核心组件

### BackgroundImageManager
核心背景图片管理服务，负责：
- 图片文件的加载和验证
- 自动切换定时任务管理
- IDE背景的设置和清除
- 暂停/恢复切换状态管理
- 快速切换和清空功能

### BackgroundImageSettings
持久化设置管理，存储：
- 壁纸文件夹路径
- 透明度设置
- 切换间隔配置

### BackgroundImageConfigurable
用户配置界面，提供：
- 文件夹选择功能
- 透明度和间隔调节控件
- 实时预览和应用功能

### StartupActivity
启动监听器，在IDE启动时：
- 自动加载保存的设置
- 立即应用背景图片
- 确保插件开箱即用

### Actions (动作类)
提供View菜单下的快捷操作：
- **ToggleAutoSwitchAction** - 切换自动切换状态
- **ShowNextImageAction** - 立即显示下一张图片
- **ClearBackgroundAction** - 清空当前背景

## 构建和运行

### 环境要求
- JDK 21+
- IntelliJ IDEA 2025.2.4+
- Gradle 9.0+

### 开发命令

```bash
# 运行插件调试
./gradlew runIde

# 构建插件
./gradlew buildPlugin

# 验证插件兼容性
./gradlew verifyPlugin

# 运行测试
./gradlew test
```

### 项目配置

项目使用Gradle Kotlin DSL构建，主要配置文件：
- `build.gradle.kts` - 构建配置
- `settings.gradle.kts` - 项目设置
- `gradle.properties` - Gradle属性

## 插件配置

插件配置文件位于 `src/main/resources/META-INF/plugin.xml`，定义了：
- 插件基本信息（ID、名称、作者）
- 依赖模块声明
- 扩展点注册（配置界面、服务、启动监听器）
- 多语言资源绑定

## 支持的图片格式

插件支持以下常见图片格式：
- PNG (.png)
- JPEG (.jpg, .jpeg)

## 多语言支持

插件内置中英文双语支持：
- 系统语言为中文时自动显示中文界面
- 其他情况显示英文界面

资源文件位置：
- `src/main/resources/messages/MyMessageBundle.properties` (英文)
- `src/main/resources/messages/MyMessageBundle_zh.properties` (中文)

## 发布插件

准备发布到JetBrains Marketplace：

```bash
# 发布插件
./gradlew publishPlugin
```

发布前请确保：
1. 更新版本号
2. 完善插件描述
3. 测试所有功能
4. 遵循[质量指南][jb:quality-guidelines]

## 开发资源

- [IntelliJ Platform SDK 文档][docs]
- [插件开发示例代码][gh:code-samples]
- [插件配置文件说明][docs:plugin.xml]
- [UI设计指南][jb:ui-guidelines]
- [插件质量标准][jb:quality-guidelines]

## 许可证

本项目采用MIT许可证，详情请参见LICENSE文件。

## 贡献

欢迎提交Issue和Pull Request来改进这个插件！

[docs]: https://plugins.jetbrains.com/docs/intellij
[docs:plugin.xml]: https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html
[gh:code-samples]: https://github.com/JetBrains/intellij-sdk-code-samples
## 更新日志

### v1.0.0
- 初始版本发布
- 基础自动壁纸切换功能
- 配置界面和多语言支持
- View菜单快捷操作功能

[jb:ui-guidelines]: https://jetbrains.github.io/ui
[jb:quality-guidelines]: https://plugins.jetbrains.com/docs/marketplace/quality-guidelines.html