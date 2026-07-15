<template>
  <div>
    <header class="front-header">
      <div class="header-inner">
        <div class="header-logo" @click="router.push('/front/home')">
          <img src="@/assets/imgs/logo.png" alt="logo" class="logo-img" />
          <span class="logo-text">校园小卖部</span>
        </div>
        <nav class="header-nav">
          <el-menu
            router
            :default-active="router.currentRoute.value.path"
            mode="horizontal"
            class="nav-menu"
            :ellipsis="false"
          >
            <el-menu-item index="/front/home">
              <el-icon><HomeFilled /></el-icon>
              <span>首页</span>
            </el-menu-item>
            <el-menu-item index="/front/goods">
              <el-icon><Goods /></el-icon>
              <span>精选商品</span>
            </el-menu-item>
            <el-menu-item index="/front/cart">
              <el-icon><ShoppingCart /></el-icon>
              <span>购物车</span>
            </el-menu-item>
          </el-menu>
        </nav>
        <div class="header-right">
          <div class="search-box" v-if="router.currentRoute.value.path !== '/front/goods'">
            <el-input
              v-model="data.goodsName"
              placeholder="搜索商品..."
              :prefix-icon="Search"
              size="large"
              class="search-input"
              @keyup.enter="search"
              clearable
            />
          </div>
          <el-dropdown trigger="click" class="user-dropdown">
            <div class="user-trigger">
              <el-avatar :size="36" :src="data.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
              <span class="user-name">{{ data.user.name || '代码小白' }}</span>
              <el-icon class="arrow-icon"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="personal">
                  <el-icon><User /></el-icon> 个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/userRecharge')">
                  <el-icon><Wallet /></el-icon> 我的充值
                </el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/order')">
                  <el-icon><Document /></el-icon> 我的订单
                </el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/userCollect')">
                  <el-icon><Star /></el-icon> 我的收藏
                </el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/password')">
                  <el-icon><Lock /></el-icon> 修改密码
                </el-dropdown-item>
                <el-dropdown-item divided @click="logout">
                  <el-icon><SwitchButton /></el-icon> 退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>
    <div style="background-color: #f0f2f5">
      <router-view @updateUser="updateUser" />
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import router from '../router'
import Footer from '../components/Footer.vue'

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  goodsName: null,
})

const search = () => {
  if (data.goodsName) {
    router.push('/front/goods/?name=' + data.goodsName)
    data.goodsName = null
  }
}

const logout = () => {
  localStorage.removeItem('system-user')
  ElMessage.success('退出登录成功')
  router.push('/login')
}

const personal = () => {
  router.push('/front/person')
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('system-user') || '{}')
}
</script>

<style scoped>
.front-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.header-inner {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  height: 64px;
  padding: 0 20px;
}

.header-logo {
  display: flex;
  align-items: center;
  cursor: pointer;
  flex-shrink: 0;
  margin-right: 32px;
}

.logo-img {
  width: 38px;
  height: 38px;
}

.logo-text {
  font-size: 20px;
  font-weight: 700;
  color: #1a1a2e;
  margin-left: 10px;
  letter-spacing: 1px;
}

.header-nav {
  flex: 1;
  display: flex;
  align-items: center;
}

.nav-menu {
  border-bottom: none !important;
  background: transparent;
}

.nav-menu :deep(.el-menu-item) {
  height: 64px;
  line-height: 64px;
  font-size: 15px;
  color: #555;
  border-bottom: 2px solid transparent;
  transition: color 0.2s, border-color 0.2s;
  padding: 0 20px;
}

.nav-menu :deep(.el-menu-item:hover) {
  color: #4361ee;
  background: transparent !important;
}

.nav-menu :deep(.el-menu-item.is-active) {
  color: #4361ee;
  border-bottom-color: #4361ee;
  background: transparent !important;
  font-weight: 600;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-shrink: 0;
}

.search-box {
  width: 260px;
}

.search-input :deep(.el-input__wrapper) {
  border-radius: 20px;
  background: #f5f5f6;
  box-shadow: none;
  transition: background 0.2s;
}

.search-input :deep(.el-input__wrapper:hover),
.search-input :deep(.el-input__wrapper.is-focus) {
  background: #eeeeef;
  box-shadow: none;
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
  max-width: 80px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.arrow-icon {
  font-size: 12px;
  color: #999;
  transition: transform 0.2s;
}
</style>
