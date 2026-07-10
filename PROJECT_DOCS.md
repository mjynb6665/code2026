# code2026 — 校园小卖部 项目全面解析文档

> **生成日期**: 2026-06-29  
> **Git 分支**: v6  
> **文档用途**: 项目结构梳理、知识讲解、新成员快速上手

---

## 目录

1. [项目概述](#1-项目概述)
2. [技术栈详解](#2-技术栈详解)
3. [项目目录结构](#3-项目目录结构)
4. [后端架构详解](#4-后端架构详解)
5. [前端架构详解](#5-前端架构详解)
6. [数据库设计](#6-数据库设计)
7. [API 接口清单](#7-api-接口清单)
8. [核心业务流程](#8-核心业务流程)
9. [知识讲解——后端技术点](#9-知识讲解后端技术点)
10. [知识讲解——前端技术点](#10-知识讲解前端技术点)
11. [快速启动指南](#11-快速启动指南)
12. [近期提交记录](#12-近期提交记录)

---

## 1. 项目概述

这是一个**校园小卖部在线商城系统**，采用前后端分离架构，包含：

- **用户端（前台）**: 商品浏览、搜索、分类筛选、商品详情、收藏、个人中心
- **管理端（后台）**: 商品管理、分类管理、轮播图管理、用户管理、管理员管理、收藏查看

### 项目来源

视频教程作者：**程序员青戈**（B站空间：https://space.bilibili.com/402779077）

---

## 2. 技术栈详解

### 2.1 后端技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 3.3.1 | 后端框架，提供 RESTful API |
| Java | 21 | 编程语言 |
| MyBatis | 3.0.3 (starter) | ORM 框架，SQL 映射 |
| MySQL | (JDBC Connector) | 关系型数据库 |
| PageHelper | 1.4.6 | MyBatis 物理分页插件 |
| Hutool | 5.8.25 | Java 工具类库（文件操作、日期、字符串等） |
| Maven | (继承自 spring-boot-starter-parent) | 项目构建与依赖管理 |

### 2.2 前端技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Vue | 3.3.4 | 前端渐进式框架 |
| Vite | 4.4.11 | 构建工具（开发服务器 + 打包） |
| Vue Router | 4.2.5 | 前端路由 |
| Element Plus | 2.8.4 | UI 组件库 |
| Axios | 1.6.2 | HTTP 请求库 |
| wangEditor | 5.1.23 | 富文本编辑器（商品详情编辑） |
| SCSS/Sass | 1.69.5 | CSS 预处理器 |
| unplugin-auto-import | 0.16.7 | 自动导入 API（Vue、Element Plus） |
| unplugin-vue-components | 0.25.2 | 按需自动导入组件 |

---

## 3. 项目目录结构

```
code2026/
├── .gitignore                    # Git 忽略规则
├── PROJECT_DOCS.md               # 📄 本文档
│
├── springboot/                   # 🔧 后端 Spring Boot 项目
│   ├── pom.xml                   # Maven 依赖配置
│   └── src/main/
│       ├── java/com/example/
│       │   ├── SpringbootApplication.java    # 启动入口
│       │   ├── common/                        # 公共类
│       │   │   ├── CorsConfig.java           # 跨域配置
│       │   │   └── Result.java               # 统一响应封装
│       │   ├── controller/                    # 控制器层 (7个)
│       │   │   ├── AdminController.java       # 管理员 CRUD
│       │   │   ├── UserController.java        # 用户 CRUD
│       │   │   ├── GoodsController.java       # 商品 CRUD
│       │   │   ├── CategoryController.java    # 分类 CRUD
│       │   │   ├── CarouselController.java    # 轮播图 CRUD
│       │   │   ├── CollectController.java     # 收藏 CRUD
│       │   │   ├── FileController.java        # 文件上传/下载
│       │   │   └── WebController.java         # 登录/注册/改密
│       │   ├── service/                       # 业务逻辑层 (6个)
│       │   │   ├── AdminService.java
│       │   │   ├── UserService.java
│       │   │   ├── GoodsService.java
│       │   │   ├── CategoryService.java
│       │   │   ├── CarouselService.java
│       │   │   └── CollectService.java
│       │   ├── mapper/                        # MyBatis Mapper 接口 (6个)
│       │   │   ├── AdminMapper.java
│       │   │   ├── UserMapper.java
│       │   │   ├── GoodsMapper.java
│       │   │   ├── CategoryMapper.java
│       │   │   ├── CarouselMapper.java
│       │   │   └── CollectMapper.java
│       │   ├── entity/                        # 实体类 (7个)
│       │   │   ├── Account.java              # 用户/管理员父类
│       │   │   ├── User.java                 # 普通用户
│       │   │   ├── Admin.java                # 管理员
│       │   │   ├── Goods.java                # 商品
│       │   │   ├── Category.java             # 分类
│       │   │   ├── Carousel.java             # 轮播图
│       │   │   └── Collect.java              # 收藏
│       │   ├── exception/                     # 异常处理
│       │   │   ├── CustomException.java       # 自定义异常
│       │   │   └── GlobalExceptionHandler.java # 全局异常处理器
│       │   └── utils/
│       │       └── CopyUtils.java             # 代码复制小工具
│       └── resources/
│           ├── application.yml                # 应用配置
│           └── mapper/                        # MyBatis XML 映射文件 (6个)
│               ├── AdminMapper.xml
│               ├── UserMapper.xml
│               ├── GoodsMapper.xml
│               ├── CategoryMapper.xml
│               ├── CarouselMapper.xml
│               └── CollectMapper.xml
│
├── vue/                          # 🎨 前端 Vue 3 项目
│   ├── package.json              # NPM 依赖配置
│   ├── vite.config.js            # Vite 配置
│   ├── index.html                # 入口 HTML
│   ├── .env.development          # 开发环境变量
│   ├── .env.production           # 生产环境变量
│   ├── components.d.ts           # 组件类型声明（自动生成）
│   ├── auto-imports.d.ts         # 自动导入类型声明（自动生成）
│   └── src/
│       ├── main.js               # Vue 入口文件
│       ├── App.vue               # 根组件
│       ├── router/
│       │   └── index.js          # 路由配置
│       ├── utils/
│       │   └── request.js        # Axios 封装（拦截器）
│       ├── assets/
│       │   ├── css/
│       │   │   ├── global.css    # 全局样式
│       │   │   └── index.scss    # Element Plus 主题覆盖
│       │   └── imgs/             # 静态图片
│       │       ├── logo.png
│       │       ├── hot.png
│       │       ├── love.png
│       │       └── new.png
│       ├── components/
│       │   └── Footer.vue        # 页脚组件
│       └── views/                # 页面组件
│           ├── Login.vue         # 登录页
│           ├── Register.vue      # 注册页
│           ├── Front.vue         # 前台布局容器
│           ├── Manager.vue       # 后台布局容器
│           ├── front/            # 前台子页面
│           │   ├── Home.vue      # 首页（轮播、推荐、热销、最新）
│           │   ├── Goods.vue     # 商品列表（分类筛选 + 搜索）
│           │   ├── GoodsDetail.vue # 商品详情（收藏、购买）
│           │   ├── Person.vue    # 个人中心
│           │   ├── Password.vue  # 修改密码
│           │   └── UserCollect.vue # 我的收藏
│           └── manager/          # 后台管理页面
│               ├── Home.vue      # 后台首页
│               ├── Admin.vue     # 管理员管理
│               ├── User.vue      # 用户管理
│               ├── Category.vue  # 分类管理
│               ├── Goods.vue     # 商品管理
│               ├── Carousel.vue  # 轮播图管理
│               ├── Collect.vue   # 收藏管理
│               ├── Person.vue    # 管理员个人信息
│               └── Password.vue  # 管理员修改密码
│
└── files/                        # 📁 上传文件存储目录
    └── *.png / *.jpg             # 上传的图片文件
```

---

## 4. 后端架构详解

### 4.1 分层架构

```
Controller（控制器层）
    ↓ 调用
Service（业务逻辑层）
    ↓ 调用
Mapper（数据访问层）
    ↓ 映射
Mapper.xml（SQL 语句）
    ↓ 操作
MySQL 数据库
```

这是经典的**三层架构**模式：

- **Controller**: 接收 HTTP 请求，参数校验，调用 Service，返回 Result
- **Service**: 业务逻辑处理（校验、组装数据、业务规则）
- **Mapper**: 数据库操作接口 + XML 中的 SQL 语句

### 4.2 统一响应格式 — `Result`

```java
// Result 类封装了所有 API 的返回格式
{
  "code": "200",       // "200" 成功 / "500" 失败
  "msg": "请求成功",    // 提示信息
  "data": { ... }      // 业务数据（可以是对象、列表、PageInfo）
}
```

**设计模式**：静态工厂方法 + 链式调用，确保前端解析一致。

### 4.3 异常处理机制

两个异常类配合工作：

1. **`CustomException`**: 继承 `RuntimeException`，携带自定义错误消息
2. **`GlobalExceptionHandler`**: 使用 `@ControllerAdvice` 全局拦截异常
   - `Exception.class` → 通用异常 → 返回 `Result.error()`
   - `CustomException.class` → 业务异常 → 返回 `Result.error(e.getMsg())`

这样 Service 层可以自由 `throw new CustomException("用户不存在")`，前端自动收到友好的错误提示。

### 4.4 实体类继承设计

```
Account（抽象父类：id, username, password, name, role, avatar, newPassword）
    ├── User（增加: account 账户余额）
    └── Admin（无额外字段，重写所有 getter/setter）
```

`Account` 作为参数传递角色，在登录和改密时统一处理，通过 `role` 字段分发到不同的 Service。

### 4.5 数据库操作模式

每个业务模块遵循**完全相同的 CRUD 模式**（代码生成器的产物）：

| 操作 | HTTP 方法 | URL 模式 | 说明 |
|------|----------|---------|------|
| 新增 | POST | `/xxx/add` | `@RequestBody` 接收 JSON |
| 删除 | DELETE | `/xxx/delete/{id}` | 路径参数传 ID |
| 修改 | PUT | `/xxx/update` | `@RequestBody` 接收 JSON（需含 id） |
| 单个查询 | GET | `/xxx/selectById/{id}` | 路径参数传 ID |
| 全部查询 | GET | `/xxx/selectAll` | 支持对象参数过滤 |
| 分页查询 | GET | `/xxx/selectPage` | pageNum + pageSize + 过滤条件 |

### 4.6 分页实现

使用 **PageHelper** 插件：

```java
PageHelper.startPage(pageNum, pageSize);   // 1. 设置分页参数
List<Goods> list = goodsMapper.selectAll(goods);  // 2. 执行查询（PageHelper 自动拦截 SQL 加 LIMIT）
return PageInfo.of(list);                  // 3. 封装分页信息（total, pages, list 等）
```

### 4.7 文件上传/下载

- **存储路径**: `项目根目录/files/`（通过 `user.dir` 获取）
- **文件名策略**: `时间戳-原始文件名`（避免重名冲突）
- **上传**: 接收 `MultipartFile`，使用 Hutool 的 `FileUtil.writeBytes()` 写入磁盘
- **下载**: 读取文件字节数组，通过 `ServletOutputStream` 输出
- **wangEditor 适配**: 单独提供 `/files/wang/upload` 接口，返回编辑器要求的格式 `{errno: 0, data: [{url: "..."}]}`

### 4.8 跨域配置（CORS）

```java
@Configuration
public class CorsConfig {
    // 允许所有来源、所有请求头、所有 HTTP 方法
    // 适用场景：前后端分离开发（Vue 开发服务器端口 ≠ 后端端口）
}
```

### 4.9 CodeCopyUtils — 代码生成辅助工具

位于 `utils/CopyUtils.java`，可以通过 `main` 方法快速复制一套完整的业务模块：

```
输入源名称（如 "Category"）+ 目标名称（如 "Collect"）
    → 自动生成: Controller + Service + Mapper + Mapper.xml + Vue页面
    → 替换类名、对象名（大写/小驼峰）
```

这就是本项目中 `Collect`（收藏）模块的生成方式——从 `Category` 模块复制而来。

---

## 5. 前端架构详解

### 5.1 入口流程

```
index.html
  → main.js（创建 Vue 应用、注册路由、Element Plus、全局图标）
    → App.vue
      → <RouterView />（根据 URL 渲染对应页面组件）
```

### 5.2 路由设计

```
/ (根路径) → 重定向到 /login
├── /login → Login.vue（登录页）
├── /register → Register.vue（注册页）
├── /manager → Manager.vue（后台布局）→ 重定向到 /manager/home
│   ├── /manager/home         → manager/Home.vue（系统首页）
│   ├── /manager/admin        → manager/Admin.vue（管理员管理）
│   ├── /manager/user         → manager/User.vue（用户管理）
│   ├── /manager/category     → manager/Category.vue（分类管理）
│   ├── /manager/goods        → manager/Goods.vue（商品管理）
│   ├── /manager/carousel     → manager/Carousel.vue（轮播图管理）
│   ├── /manager/collect      → manager/Collect.vue（收藏管理）
│   ├── /manager/person       → manager/Person.vue（个人信息）
│   └── /manager/password     → manager/Password.vue（修改密码）
└── /front → Front.vue（前台布局）→ 重定向到 /front/home
    ├── /front/home         → front/Home.vue（首页）
    ├── /front/goods        → front/Goods.vue（商品列表）
    ├── /front/goodsDetail  → front/GoodsDetail.vue（商品详情）
    ├── /front/person       → front/Person.vue（个人中心）
    ├── /front/password     → front/Password.vue（修改密码）
    └── /front/userCollect  → front/UserCollect.vue（我的收藏）
```

**关键设计点**：
- 前台和后台各有独立的布局容器（Front.vue / Manager.vue）
- 子页面通过 `<router-view>` 在布局容器内渲染
- 所有路由组件使用**懒加载** `() => import(...)`，Vite 会按路由自动拆包
- `router.beforeEach` 在每次导航时自动滚动到页面顶部

### 5.3 Axios 封装（request.js）

```
request = axios.create({ baseURL, timeout })

request.interceptors.request  → 设置 Content-Type 请求头
request.interceptors.response → 统一处理：
  - blob 类型直接返回
  - 字符串自动 JSON.parse
  - code === '401' → 跳转登录页
  - 其他直接返回 res（Note：代码中只处理了 401，实际业务错误通过 res.code 判断）
```

**注意**：错误状态码 `401` 的处理存在但后端未使用；后端业务错误通过 `code === '500'` 返回，由各页面自行判断。

### 5.4 状态管理方式

本项目**没有使用 Vuex/Pinia**，而是采用简单方案：
- 登录用户信息存储在 `localStorage['system-user']`
- 各组件通过 `JSON.parse(localStorage.getItem('system-user'))` 读取
- 通过 `@updateUser` 事件在父子组件间通知更新

### 5.5 Vite 自动导入配置

```js
// vite.config.js 中的插件
AutoImport({ resolvers: [ElementPlusResolver()] })   // 自动导入 ref, reactive 等
Components({ resolvers: [ElementPlusResolver()] })    // 自动导入 Element Plus 组件
ElementPlus({ useSource: true })                      // 按需定制主题
```

**效果**：组件中无需手动 `import { ref } from 'vue'` 或 `import { ElButton } from 'element-plus'`，编译器自动处理。

### 5.6 样式方案

- **Element Plus SCSS 变量覆盖**：通过 `additionalData` 注入 `@/assets/css/index.scss`，覆盖 Element Plus 默认主题
- **全局样式**: `global.css`
- **组件样式**: `<style scoped>` 实现样式隔离

---

## 6. 数据库设计

数据库名称：`code2026`  
数据库地址：`localhost:3306`  
字符集：`utf8mb4`（通过 `characterEncoding=utf-8` 配置）

### 6.1 表结构（从实体类和 Mapper XML 推断）

#### admin（管理员表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT (PK, 自增) | 主键 |
| username | VARCHAR | 登录用户名 |
| password | VARCHAR | 密码（明文存储 ⚠️） |
| name | VARCHAR | 姓名 |
| avatar | VARCHAR | 头像 URL |
| role | VARCHAR | 角色标识（"管理员"） |

#### user（用户表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT (PK, 自增) | 主键 |
| username | VARCHAR | 登录用户名 |
| password | VARCHAR | 密码（明文存储 ⚠️） |
| name | VARCHAR | 昵称 |
| avatar | VARCHAR | 头像 URL |
| role | VARCHAR | 角色标识（"普通用户"） |
| account | DECIMAL | 账户余额 |

#### goods（商品表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT (PK, 自增) | 主键 |
| name | VARCHAR | 商品名称 |
| img | VARCHAR | 商品图片 URL |
| price | DECIMAL | 价格 |
| description | VARCHAR | 简介 |
| content | TEXT | 详情（富文本 HTML） |
| store | INT | 库存数量 |
| category_id | INT (FK) | 分类 ID → category.id |
| status | VARCHAR | 上架状态（"上架"/"下架"） |
| views | INT | 浏览量 |
| sale_count | INT | 销量 |
| recommend | VARCHAR | 是否推荐（"是"/"否"） |
| time | VARCHAR | 创建时间 |

#### category（分类表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT (PK, 自增) | 主键 |
| name | VARCHAR | 分类名称 |

#### carousel（轮播图表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT (PK, 自增) | 主键 |
| img | VARCHAR | 图片 URL |
| goods_id | INT (FK) | 关联商品 ID → goods.id |

#### collect（收藏表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT (PK, 自增) | 主键 |
| goods_id | INT (FK) | 商品 ID → goods.id |
| user_id | INT (FK) | 用户 ID → user.id |
| time | VARCHAR | 收藏时间 |

### 6.2 表关系

```
category (1) ──── (N) goods           # 一个分类下有多个商品
goods     (1) ──── (N) carousel       # 一个商品可出现在多个轮播图
goods     (1) ──── (N) collect        # 一个商品可被多次收藏
user      (1) ──── (N) collect        # 一个用户可以收藏多个商品
```

⚠️ **安全提醒**：当前系统密码以明文存储，生产环境应使用 BCrypt 等加密方案。

---

## 7. API 接口清单

### 7.1 公共接口

| 方法 | URL | 说明 | 请求体/参数 |
|------|-----|------|-----------|
| GET | `/` | 默认接口，返回成功 | - |
| POST | `/login` | 登录 | `{username, password, role}` |
| POST | `/register` | 注册 | `{username, password, newPassword, name}` |
| PUT | `/updatePassword` | 修改密码 | `{username, password, newPassword, role}` |

### 7.2 管理员接口 `/admin`

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/admin/add` | 新增管理员 |
| DELETE | `/admin/delete/{id}` | 删除管理员 |
| PUT | `/admin/update` | 修改管理员 |
| GET | `/admin/selectById/{id}` | 根据ID查询 |
| GET | `/admin/selectAll` | 查询所有（支持 name 过滤） |
| GET | `/admin/selectPage` | 分页查询（pageNum, pageSize, name） |

### 7.3 用户接口 `/user`

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/user/add` | 新增用户 |
| DELETE | `/user/delete/{id}` | 删除用户 |
| PUT | `/user/update` | 修改用户 |
| GET | `/user/selectById/{id}` | 根据ID查询 |
| GET | `/user/selectPage` | 分页查询（pageNum, pageSize, name） |

### 7.4 商品接口 `/goods`

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/goods/add` | 新增商品 |
| DELETE | `/goods/delete/{id}` | 删除商品 |
| PUT | `/goods/update` | 修改商品 |
| GET | `/goods/selectById/{id}` | 根据ID查询（含分类名） |
| GET | `/goods/selectAll` | 查询所有（支持 name, categoryId, status 过滤） |
| GET | `/goods/selectPage` | 分页查询 |

### 7.5 分类接口 `/category`

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/category/add` | 新增分类 |
| DELETE | `/category/delete/{id}` | 删除分类 |
| PUT | `/category/update` | 修改分类 |
| GET | `/category/selectById/{id}` | 根据ID查询 |
| GET | `/category/selectAll` | 查询所有 |
| GET | `/category/selectPage` | 分页查询 |

### 7.6 轮播图接口 `/carousel`

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/carousel/add` | 新增轮播图 |
| DELETE | `/carousel/delete/{id}` | 删除轮播图 |
| PUT | `/carousel/update` | 修改轮播图 |
| GET | `/carousel/selectById/{id}` | 根据ID查询 |
| GET | `/carousel/selectAll` | 查询所有（LEFT JOIN goods 获取商品名） |
| GET | `/carousel/selectPage` | 分页查询 |

### 7.7 收藏接口 `/collect`

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/collect/add` | 新增收藏 |
| DELETE | `/collect/delete/{id}` | 删除收藏（取消收藏） |
| PUT | `/collect/update` | 修改收藏 |
| GET | `/collect/selectById/{id}` | 根据ID查询 |
| GET | `/collect/selectAll` | 查询所有（支持 goodsId, userId, goodsName 过滤） |
| GET | `/collect/selectPage` | 分页查询 |

### 7.8 文件接口 `/files`

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/files/upload` | 文件上传（返回可访问 URL） |
| GET | `/files/download/{fileName}` | 文件下载 |
| POST | `/files/wang/upload` | wangEditor 编辑器图片上传 |

---

## 8. 核心业务流程

### 8.1 登录流程

```
Login.vue 表单 → POST /login {username, password, role}
    → WebController.login()
        → role === "管理员" → AdminService.login()
        → role === "普通用户" → UserService.login()
    → 查询数据库 → 验证密码 → 返回 Account 对象
    → 前端存储到 localStorage['system-user']
    → role === "管理员" → router.push('/manager/home')
    → role === "普通用户" → router.push('/front/home')
```

### 8.2 商品浏览流程

```
首页 (Home.vue) 加载:
  1. GET /carousel/selectAll → 获取轮播图列表
  2. GET /goods/selectAll?status=上架 → 按销量排序取前4 → 热销商品
  3. GET /goods/selectAll?status=上架 → 筛选 recommend='是' → 推荐商品
  4. GET /goods/selectAll?status=上架 → 取前4 → 最新商品

商品列表页 (Goods.vue):
  1. GET /category/selectAll → 分类标签
  2. GET /goods/selectPage?name=&categoryId=&pageNum=&pageSize= → 分页商品
  3. 点击分类 → 按分类筛选
  4. 输入关键词 → 按名称搜索

商品详情页 (GoodsDetail.vue):
  1. GET /goods/selectById/{id} → 商品详情
  2. GET /collect/selectAll?goodsId=&userId= → 查询是否已收藏
  3. POST /collect/add → 收藏商品
  4. DELETE /collect/delete/{id} → 取消收藏
```

### 8.3 管理端 CRUD 流程

```
列表展示: GET /xxx/selectPage → 分页表格
新增: 弹窗表单 → POST /xxx/add → 刷新列表
修改: 行内编辑 → PUT /xxx/update → 刷新列表
删除: 确认弹窗 → DELETE /xxx/delete/{id} → 刷新列表
```

所有管理端页面遵循**完全相同的 UI 模式**（表格 + 搜索 + 分页 + 新增/修改弹窗 + 删除确认），由 CopyUtils 工具批量生成。

### 8.4 文件上传流程

```
Element Plus Upload 组件 → POST /files/upload (multipart/form-data)
    → FileController.upload()
    → 文件名 = 时间戳 + "-" + 原始文件名
    → 存入 {user.dir}/files/
    → 返回 URL: http://localhost:9090/files/download/{fileName}
    → 前端将 URL 存入表单数据 → 提交保存到数据库
```

---

## 9. 知识讲解——后端技术点

### 9.1 Spring Boot 核心注解

| 注解 | 含义 |
|------|------|
| `@SpringBootApplication` | 组合注解：`@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan` |
| `@RestController` | `@Controller` + `@ResponseBody`，所有方法返回 JSON |
| `@RequestMapping("/path")` | 类级别的 URL 前缀 |
| `@GetMapping / @PostMapping / @PutMapping / @DeleteMapping` | RESTful 风格的 HTTP 方法映射 |
| `@PathVariable` | 获取 URL 路径参数（如 `/delete/{id}` 中的 id） |
| `@RequestParam` | 获取 URL 查询参数（如 `?pageNum=1`） |
| `@RequestBody` | 获取请求体中的 JSON 并自动反序列化为 Java 对象 |
| `@Service` | 标记业务逻辑层组件 |
| `@Resource / @Autowired` | 依赖注入（`@Resource` 按名称，`@Autowired` 按类型） |
| `@Configuration` | 标记配置类 |
| `@Bean` | 声明一个 Bean 由 Spring 管理 |
| `@ControllerAdvice` | 全局控制器增强（用于统一异常处理） |
| `@ExceptionHandler` | 声明异常处理方法 |
| `@Value("${key}")` | 读取配置文件中的值 |

### 9.2 MyBatis 工作原理

```
Mapper 接口 (Java)
    ↓ namespace 匹配
Mapper XML (SQL)
    ↓ id 匹配方法名
    ↓ parameterType 匹配参数类型
    ↓ resultType 匹配返回类型
    ↓
执行 SQL → 结果映射到 Java 对象
```

**动态 SQL 标签**：
- `<where>`: 自动处理 WHERE 子句，去除多余的 AND/OR
- `<if test="...">`: 条件判断，实现动态查询
- `<trim>`: 灵活的字符串处理（去除/添加前缀后缀）
- `<set>`: 动态 UPDATE 的 SET 子句

### 9.3 RESTful API 设计

```
GET    /resource        → 查询列表
GET    /resource/{id}   → 查询单个
POST   /resource        → 新增
PUT    /resource        → 修改
DELETE /resource/{id}   → 删除
```

本项目遵循了 RESTful 风格（虽有个别接口命名为 `selectPage` 而非标准的查询参数方式）。

### 9.4 三层架构的优势

1. **职责分离**: Controller 只管请求响应，Service 只管业务逻辑，Mapper 只管数据库
2. **可测试性**: 每一层可独立单元测试
3. **可维护性**: 修改某层不影响其他层
4. **可复用性**: Service 方法可被多个 Controller 调用

### 9.5 分页插件 PageHelper 原理

PageHelper 通过 MyBatis 的**拦截器（Interceptor）**机制，在执行 SQL 之前自动拼接 `LIMIT ? OFFSET ?` 子句，实现物理分页（而非内存分页）。

### 9.6 文件处理方案对比

| 方案 | 优点 | 缺点 |
|------|------|------|
| 本地磁盘存储（本项目） | 简单、零成本 | 不便于扩展、有单点故障 |
| OSS（阿里云/腾讯云） | 高可用、CDN加速 | 需要付费 |
| MinIO | 开源、S3兼容 | 需要自建服务 |

---

## 10. 知识讲解——前端技术点

### 10.1 Vue 3 Composition API

本项目使用 `<script setup>` 语法糖（Vue 3 推荐写法）：

```js
// 选项式 API (Vue 2 风格)
export default {
  data() { return { count: 0 } },
  methods: { increment() { this.count++ } }
}

// 组合式 API (Vue 3 <script setup>)
const count = ref(0)
const increment = () => count.value++
```

**核心 API**：
- `ref()`: 创建响应式引用（基本类型）
- `reactive()`: 创建响应式对象（引用类型）
- `computed()`: 计算属性
- `watch()`: 监听数据变化
- `onMounted()`: 生命周期钩子

### 10.2 Vue Router 核心概念

```js
// 路由配置
{
  path: '/manager',        // URL 路径
  component: Manager,      // 渲染的组件
  redirect: '/manager/home', // 重定向
  children: [...]          // 嵌套子路由
}

// 在组件中
<router-view />            // 子路由渲染出口
router.push('/path')       // 编程式导航
router.currentRoute.value  // 当前路由信息
```

### 10.3 Axios 拦截器模式

```
请求拦截器 → 发送请求 → 服务器处理 → 返回响应 → 响应拦截器 → 业务代码
```

- **请求拦截器**: 统一添加请求头（Token、Content-Type 等）
- **响应拦截器**: 统一处理错误码、数据格式转换

### 10.4 前端认证方案

本项目使用 **localStorage 存储用户信息**：

```js
// 登录后存储
localStorage.setItem('system-user', JSON.stringify(userData))

// 各组件读取
const user = JSON.parse(localStorage.getItem('system-user') || '{}')
```

⚠️ **生产环境建议**：使用 Token（JWT）+ HttpOnly Cookie，localStorage 存在 XSS 攻击风险。

### 10.5 Element Plus 使用模式

```html
<!-- 表单验证 -->
<el-form :model="data.form" :rules="data.rules" ref="formRef">
  <el-form-item prop="username">
    <el-input v-model="data.form.username" />
  </el-form-item>
</el-form>

<!-- 分页 -->
<el-pagination
  v-model:page-size="data.pageSize"
  v-model:current-page="data.pageNum"
  :total="data.total"
/>

<!-- 表格 -->
<el-table :data="data.tableData">
  <el-table-column prop="name" label="名称" />
</el-table>
```

### 10.6 Vite vs Webpack

| 特性 | Vite | Webpack |
|------|------|---------|
| 开发服务器启动 | 毫秒级（ESM 按需编译） | 秒级（全量打包） |
| 热更新 | 极快（模块级） | 较快（取决于项目大小） |
| 生产构建 | Rollup（高效 Tree Shaking） | 自建（功能全面） |
| 配置复杂度 | 低 | 高 |

---

## 11. 快速启动指南

### 环境要求

- **JDK** 21+
- **Maven** 3.6+
- **MySQL** 8.0+
- **Node.js** 18+
- **npm** 9+

### 数据库初始化

1. 启动 MySQL 服务
2. 创建数据库：`CREATE DATABASE code2026 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
3. 执行建表 SQL（需要在 `code2026` 库中创建 admin、user、goods、category、carousel、collect 表）
4. 数据库配置：`springboot/src/main/resources/application.yml`（默认 root/123456）

### 启动后端

```bash
cd springboot
mvn spring-boot:run
# 或者使用 IDE 运行 SpringbootApplication.main()
# 服务启动在 http://localhost:9090
```

### 启动前端

```bash
cd vue
npm install          # 首次运行需要安装依赖
npm run dev          # 启动开发服务器
# 界面访问 http://localhost:5173（Vite 默认端口）
```

### 默认账号

- 管理员：通过 `/register` 注册时选择角色 "管理员"，或直接在 admin 表中插入记录
- 普通用户：通过 `/register` 注册，角色选择 "普通用户"

---

## 12. 近期提交记录

| 提交哈希 | 说明 |
|---------|------|
| `0bab867` | 实现商品详情页 |
| `22c5c21` | 新增了商品的搜索，商品分类和检索功能 |
| `5071c71` | 新增了商品的搜索，商品分类和检索功能 |
| `f4e6181` | 新增了商品的轮播图的管理 |
| `6643269` | 新增了商品的轮播图的管理 |

### 当前分支 `v6` 的未提交变更

- 新增 `Collect` 收藏功能（Entity / Controller / Service / Mapper / XML / Vue 页面）
- 新增商品详情页 `GoodsDetail.vue`
- 修改 `CopyUtils.java`（代码复制工具）
- 路由更新（收藏相关路由）
- 前端布局更新（Front.vue / Manager.vue）

---

## 附录：常见问题

**Q: 前后端如何通信？**
A: 前端通过 Axios 发送 HTTP 请求到 `http://localhost:9090`（开发环境配置在 `.env.development`），后端通过 `CorsConfig` 允许跨域。

**Q: 如何新增一个业务模块？**
A: 使用 `CopyUtils.main()` 方法，设置 `sourceName` 和 `targetName`，即可自动生成全套文件（Controller/Service/Mapper/XML/Vue）。

**Q: 密码如何存储？**
A: 当前为**明文存储**（教学目的），生产环境必须使用 BCrypt/Scrypt 等哈希算法。

**Q: 文件存储在哪里？**
A: 项目根目录下的 `files/` 文件夹，通过 `System.getProperty("user.dir") + "/files/"` 定位。

---

> 📌 **提示**：本文档与代码同步生成于 2026-06-29。代码变更后请更新本文档相应章节。
