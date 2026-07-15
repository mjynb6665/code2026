<template>
  <div>
    <header class="manager-header">
      <div class="header-inner">
        <div class="header-logo" @click="router.push('/manager/home')">
          <img src="@/assets/imgs/logo.png" alt="logo" class="logo-img" />
          <span class="logo-text">小白2026</span>
          <span class="logo-badge">管理后台</span>
        </div>
        <div class="header-right">
          <el-dropdown trigger="click" class="user-dropdown">
            <div class="user-trigger">
              <el-avatar :size="36" :src="data.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
              <span class="user-name">{{ data.user.name || '管理员' }}</span>
              <el-icon class="arrow-icon"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push('/manager/person')">
                  <el-icon><User /></el-icon> 个人信息
                </el-dropdown-item>
                <el-dropdown-item @click="router.push('/manager/password')">
                  <el-icon><Lock /></el-icon> 修改密码
                </el-dropdown-item>
                <el-dropdown-item divided @click="logout">
                  <el-icon><SwitchButton /></el-icon> 退出系统
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>

    <div style="display: flex">
      <aside class="manager-sidebar">
        <el-menu
          router
          class="sidebar-menu"
          :default-active="router.currentRoute.value.path"
          :default-openeds="['info', 'user']"
        >
          <el-menu-item index="/manager/home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-sub-menu index="info">
            <template #title>
              <el-icon><Memo /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/category">
              <el-icon><Grid /></el-icon>
              <span>商品分类</span>
            </el-menu-item>
            <el-menu-item index="/manager/goods">
              <el-icon><Goods /></el-icon>
              <span>商品信息</span>
            </el-menu-item>
            <el-menu-item index="/manager/carousel">
              <el-icon><PictureFilled /></el-icon>
              <span>轮播图管理</span>
            </el-menu-item>
            <el-menu-item index="/manager/collect">
              <el-icon><Star /></el-icon>
              <span>用户收藏</span>
            </el-menu-item>
            <el-menu-item index="/manager/comment">
              <el-icon><ChatLineSquare /></el-icon>
              <span>评论管理</span>
            </el-menu-item>
            <el-menu-item index="/manager/order">
              <el-icon><Document /></el-icon>
              <span>订单管理</span>
            </el-menu-item>
            <el-menu-item index="/manager/statistics">
              <el-icon><DataAnalysis /></el-icon>
              <span>数据统计</span>
            </el-menu-item>
            <el-menu-item index="/manager/recharge">
              <el-icon><Wallet /></el-icon>
              <span>用户充值</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="user">
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">
              <el-icon><UserFilled /></el-icon>
              <span>管理员信息</span>
            </el-menu-item>
            <el-menu-item index="/manager/user">
              <el-icon><Avatar /></el-icon>
              <span>普通用户</span>
            </el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/manager/person">
            <el-icon><User /></el-icon>
            <span>个人信息</span>
          </el-menu-item>
          <el-menu-item index="/manager/password">
            <el-icon><Lock /></el-icon>
            <span>修改密码</span>
          </el-menu-item>

          <el-menu-item @click="logout">
            <el-icon><SwitchButton /></el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </aside>

      <div style="flex: 1; width: 0; background-color: #f5f5f7; padding: 20px">
        <router-view @updateUser="updateUser" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import router from '@/router'
import { ElMessage } from 'element-plus'

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}')
})

if (!data.user?.id) {
  ElMessage.error('请登录！')
  router.push('/login')
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('system-user') || '{}')
}

const logout = () => {
  localStorage.removeItem('system-user')
  ElMessage.success('退出成功')
  router.push('/login')
}
</script>

<style scoped>
.manager-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.manager-header .header-inner {
  max-width: 100%;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  padding: 0 24px;
}

.manager-header .header-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.manager-header .logo-img {
  width: 32px;
  height: 32px;
}

.manager-header .logo-text {
  font-size: 18px;
  font-weight: 700;
  color: #1a1a2e;
  letter-spacing: 1px;
}

.logo-badge {
  font-size: 12px;
  color: #4361ee;
  background: #eef0fd;
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: 500;
}

.manager-header .header-right {
  display: flex;
  align-items: center;
}

.user-dropdown {
  cursor: pointer;
}

.user-trigger {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px 12px 4px 4px;
  border-radius: 22px;
  transition: background 0.2s;
}

.user-trigger:hover {
  background: #f5f5f6;
}

.user-name {
  font-size: 14px;
  color: #333;
}

.arrow-icon {
  font-size: 12px;
  color: #999;
}

.manager-sidebar {
  width: 220px;
  flex-shrink: 0;
  background: #fff;
  border-right: 1px solid #eee;
  min-height: calc(100vh - 56px);
  overflow-y: auto;
}

.sidebar-menu {
  border-right: none !important;
  padding: 8px 0;
}

.sidebar-menu :deep(.el-menu-item) {
  height: 44px;
  line-height: 44px;
  font-size: 14px;
  color: #555;
  padding-left: 28px !important;
}

.sidebar-menu :deep(.el-menu-item:hover) {
  color: #4361ee;
  background: #f5f5f7;
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  color: #4361ee;
  background: #eef0fd;
  font-weight: 600;
}

.sidebar-menu :deep(.el-sub-menu__title) {
  height: 44px;
  line-height: 44px;
  font-size: 14px;
  color: #555;
  padding-left: 28px !important;
}

.sidebar-menu :deep(.el-sub-menu__title:hover) {
  color: #4361ee;
}

.sidebar-menu :deep(.el-sub-menu .el-menu-item) {
  padding-left: 52px !important;
}
</style>