<template>
  <footer class="site-footer">
    <div class="footer-top">
      <div class="footer-inner">
        <div class="footer-col brand-col">
          <div class="footer-brand">
            <img src="@/assets/imgs/logo.png" alt="logo" class="footer-logo" />
            <span class="footer-brand-name">校园小卖部</span>
          </div>
          <p class="footer-desc">校园一站式购物平台，为同学们提供便捷、优质的商品服务。</p>
          <div class="footer-social">
            <span class="social-item">微信小程序</span>
            <span class="social-item">公众号</span>
          </div>
        </div>
        <div class="footer-col">
          <h4 class="footer-title">快速导航</h4>
          <ul class="footer-links">
            <li><router-link to="/front/home">首页</router-link></li>
            <li><router-link to="/front/goods">精选商品</router-link></li>
            <li><router-link to="/front/cart">购物车</router-link></li>
            <li><router-link to="/front/order">我的订单</router-link></li>
          </ul>
        </div>
        <div class="footer-col">
          <h4 class="footer-title">用户服务</h4>
          <ul class="footer-links">
            <li><router-link to="/front/person">个人中心</router-link></li>
            <li><router-link to="/front/userCollect">我的收藏</router-link></li>
            <li><router-link to="/front/userRecharge">账户充值</router-link></li>
            <li><router-link to="/front/password">修改密码</router-link></li>
          </ul>
        </div>
        <div class="footer-col contact-col">
          <h4 class="footer-title">联系我们</h4>
          <ul class="footer-contact">
            <li>
              <el-icon><Phone /></el-icon>
              <span>客服热线：400-888-6666</span>
            </li>
            <li>
              <el-icon><Message /></el-icon>
              <span>联系邮箱：support@campus.cn</span>
            </li>
            <li>
              <el-icon><Location /></el-icon>
              <span>地址：校园创业中心 B-201</span>
            </li>
            <li>
              <el-icon><Clock /></el-icon>
              <span>服务时间：9:00 - 21:00</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      <div class="footer-inner">
        <p class="copyright">
          2026 校园小卖部 版权所有
          <span class="divider">|</span>
          <a href="#">ICP备20240001号</a>
          <span class="divider">|</span>
          <a href="#">浙公网安备 33010002000001号</a>
        </p>
      </div>
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
.site-footer {
  background: #1a1a2e;
  color: #b0b0c0;
}

.footer-top {
  padding: 48px 0 32px;
}

.footer-inner {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  gap: 40px;
}

.footer-col {
  flex: 1;
  min-width: 0;
}

.brand-col {
  flex: 1.4;
}

.footer-brand {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 16px;
}

.footer-logo {
  width: 36px;
  height: 36px;
}

.footer-brand-name {
  font-size: 20px;
  font-weight: 700;
  color: #fff;
  letter-spacing: 1px;
}

.footer-desc {
  font-size: 13px;
  line-height: 1.8;
  color: #888;
  margin: 0 0 16px;
}

.footer-social {
  display: flex;
  gap: 16px;
}

.social-item {
  font-size: 13px;
  color: #777;
  cursor: default;
}

.footer-title {
  font-size: 15px;
  font-weight: 600;
  color: #e0e0e0;
  margin: 0 0 16px;
}

.footer-links {
  list-style: none;
  margin: 0;
  padding: 0;
}

.footer-links li {
  margin-bottom: 10px;
}

.footer-links a {
  font-size: 13px;
  color: #888;
  text-decoration: none;
  transition: color 0.2s;
}

.footer-links a:hover {
  color: #fff;
}

.footer-contact {
  list-style: none;
  margin: 0;
  padding: 0;
}

.footer-contact li {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
  font-size: 13px;
  color: #888;
}

.footer-contact .el-icon {
  font-size: 16px;
  color: #666;
  flex-shrink: 0;
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.08);
  padding: 20px 0;
}

.footer-bottom .footer-inner {
  justify-content: center;
}

.copyright {
  margin: 0;
  font-size: 12px;
  color: #666;
  text-align: center;
}

.copyright a {
  color: #666;
  text-decoration: none;
  transition: color 0.2s;
}

.copyright a:hover {
  color: #999;
}

.divider {
  margin: 0 8px;
  color: #444;
}

@media (max-width: 768px) {
  .footer-inner {
    flex-direction: column;
    gap: 32px;
  }
  .footer-col {
    flex: none;
  }
}
</style>