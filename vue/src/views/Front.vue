
<template>
  <div>
  <div style="height: 60px; background-color: #2e3143; display: flex; align-items: center; border-bottom: 1px solid #ddd">
    <div style="flex: 1">
      <div style="padding-left: 20px; display: flex; align-items: center">
        <img src="@/assets/imgs/logo.png" alt="" style="width: 40px">
        <div style="font-weight: bold; font-size: 24px; margin-left: 5px; color: #fff">校园小卖部</div>
      </div>
    </div>
    <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
      <el-dropdown>
        <div style=" display: flex; align-items: center;">
        <img style="width: 40px; height: 40px; border-radius: 50%" :src="data.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="">
        <span style="color: #fff; margin-left: 5px">{{ data.user.name || '代码小白' }}</span>
        </div>

        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="personal">个人中心</el-dropdown-item>
            <el-dropdown-item @click="logout" >退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

    </div>
  </div>
  </div>

  <div style="background-color: #dddddd">
    <router-view @updateUser="updateUser"></router-view>
  </div>

</template>

<script setup>

import {reactive} from "vue";
import { ElDropdown, ElDropdownMenu, ElDropdownItem } from 'element-plus';
import {ElMessage} from "element-plus";
import router from "../router";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}')
})

const logout = () => {
  localStorage.removeItem('system-user');
  ElMessage.success("退出登录成功");
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
.el-tooltip__trigger{
  cursor: pointer;
  outline: none
}

</style>