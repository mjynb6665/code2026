<template>
  <div class="login-container">
    <div class="login-left">
      <div class="brand-logo">SB</div>
      <div class="brand-title">欢迎来到商城管理系统</div>
      <div class="brand-desc">安全登录 · 高效管理 · 一站式后台服务</div>
    </div>

    <!-- 右侧登录表单卡片 -->
    <div class="login-right">
      <div class="login-box">
        <div class="login-title">账号登录</div>
        <el-form :model="data.form" ref="formRef" :rules="data.rules" label-width="0">
          <el-form-item prop="username">
            <el-input
                :prefix-icon="User"
                size="large"
                v-model="data.form.username"
                placeholder="请输入账号"
                class="jd-input"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                :prefix-icon="Lock"
                size="large"
                v-model="data.form.password"
                placeholder="请输入密码"
                show-password
                class="jd-input"
            />
          </el-form-item>
          <el-form-item prop="role">
            <el-select
                size="large"
                style="width: 100%"
                v-model="data.form.role"
                class="jd-input"
            >
              <el-option value="管理员" label="管理员"></el-option>
              <el-option value="普通用户" label="普通用户"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
                size="large"
                type="danger"
                class="login-btn"
                @click="login"
            >
              登 录
            </el-button>
          </el-form-item>
        </el-form>
        <div class="register-tip">
          还没有账号？
          <a href="/register" class="register-link">立即注册</a>
        </div>
        <div class="safe-tip">安全登录，保障您的账号隐私</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { User, Lock } from "@element-plus/icons-vue";
import request from "@/utils/request";
import { ElMessage } from "element-plus";
import router from "@/router";

const data = reactive({
  form: { role: "管理员" },
  rules: {
    username: [
      { required: true, message: "请输入账号", trigger: "blur" },
    ],
    password: [
      { required: true, message: "请输入密码", trigger: "blur" },
    ],
  },
});

const formRef = ref();

const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post("/login", data.form).then((res) => {
        if (res.code === "200") {
          ElMessage.success("登录成功");
          localStorage.setItem("system-user", JSON.stringify(res.data));
          if (res.data.role === "管理员") {
            router.push("/manager/home");
          } else {
            router.push("/front/home");
          }
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  }).catch((error) => {
    console.error(error);
  });
};
</script>

<style scoped>
/* 全局容器 仿京东左右分栏布局 */
.login-container {
  height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f4f4f4;
}

/* 左侧品牌区域 */
.login-left {
  width: 520px;
  padding-right: 60px;
}
.brand-logo {
  width: 120px;
  height: 120px;
  background-color: #e6162d;
  color: #fff;
  font-size: 60px;
  font-weight: bold;
  text-align: center;
  line-height: 120px;
  border-radius: 12px;
  margin-bottom: 24px;
}
.brand-title {
  font-size: 28px;
  color: #333;
  font-weight: 500;
  margin-bottom: 12px;
}
.brand-desc {
  font-size: 16px;
  color: #666;
}

/* 右侧登录卡片区域 */
.login-right {
  width: 380px;
}
.login-box {
  background: #fff;
  padding: 40px 32px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08);
}

/* 登录标题 */
.login-title {
  font-size: 24px;
  color: #333;
  text-align: center;
  margin-bottom: 32px;
  font-weight: 500;
  position: relative;
}
.login-title::after {
  content: "";
  display: block;
  width: 60px;
  height: 3px;
  background-color: #e6162d;
  margin: 10px auto 0;
  border-radius: 2px;
}

/* ==========核心修复：输入框统一美化========== */
:deep(.jd-input .el-input__wrapper) {
  height: 46px;
  border-radius: 2px;
  box-shadow: none;
  border: 1px solid #e0e0e0;
  transition: all 0.2s ease;
}
/* 输入框文字 */
:deep(.jd-input .el-input__inner) {
  font-size: 15px;
  color: #333;
}
/* 前缀图标样式 */
:deep(.jd-input .el-input__prefix-inner) {
  font-size: 18px;
  color: #999;
}
/* hover边框变红 */
:deep(.jd-input .el-input__wrapper:hover) {
  border-color: #e6162d;
}
/* 聚焦状态 */
:deep(.jd-input .el-input__wrapper.is-focus) {
  border-color: #e6162d;
  box-shadow: 0 0 0 1px #e6162d inset;
}
/* 下拉选择框同步样式 */
:deep(.jd-input .el-select__wrapper) {
  height: 46px;
  border-radius: 2px;
  box-shadow: none;
  border: 1px solid #e0e0e0;
}
:deep(.jd-input .el-select__wrapper:hover) {
  border-color: #e6162d;
}
:deep(.jd-input .el-select__wrapper.is-focus) {
  border-color: #e6162d;
  box-shadow: 0 0 0 1px #e6162d inset;
}
/* 表单item统一间距 */
:deep(.el-form-item) {
  margin-bottom: 18px;
}

/* 登录按钮 京东红色 */
.login-btn {
  width: 100%;
  height: 46px;
  background-color: #e6162d;
  border: none;
  font-size: 17px;
}
.login-btn:hover {
  background-color: #c80e23 !important;
}

/* 注册提示 */
.register-tip {
  text-align: right;
  margin-top: 16px;
  font-size: 14px;
  color: #666;
}
.register-link {
  color: #e6162d;
  text-decoration: none;
}
.register-link:hover {
  text-decoration: underline;
}

/* 底部安全提示 */
.safe-tip {
  text-align: center;
  margin-top: 24px;
  font-size: 12px;
  color: #999;
}
</style>
