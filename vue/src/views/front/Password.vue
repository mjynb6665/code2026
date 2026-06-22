
<template>
  <div class="front-container" style="width:40%">
  <div class="card">
  <el-form ref="formRef" :model="data.user" :rules="data.rules" label-width="120px" style="padding-top: 30px">
    <el-form-item label="原密码" prop="password">
      <el-input v-model="data.user.password" autocomplete="off" show-password />
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input v-model="data.user.newPassword" autocomplete="off" show-password />
    </el-form-item>

    <el-form-item label="请确认新密码" prop="confirmPassword">
      <el-input v-model="data.user.confirmPassword" autocomplete="off" show-password/>
    </el-form-item>

    <div style="text-align: center;">
      <el-button type="primary" @click="updatePassword" size="medium">保存</el-button>
    </div>
  </el-form>
</div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import request from "../../utils/request";
import {ElMessage, ElMessageBox, ElUpload} from "element-plus";
import router from "../../router";

const formRef = ref();
const data = ref({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  rules: {
    password: [
      {required: true, message: '请输入原密码', trigger: 'blur'}
    ],
    newPassword: [
      {required: true, message: '请输入新密码', trigger: 'blur'}
    ],
    confirmPassword: [
      { required: true, message: '请确认新密码', trigger: 'blur' },
    ]
  },
})


const updatePassword = () => {
  if (data.value.user.newPassword !== data.value.user.confirmPassword) {
    ElMessage.warning('两次输入的新密码不同，请确认！')
    return
  }
  request.put('/updatePassword', data.value.user).then(res => {
    if (res.code === '200') {
      ElMessage.success('更新成功')
      logout()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const logout = () => {
  router.push('/login')
  localStorage.removeItem('system-user')
}

</script>

<style scoped>

</style>