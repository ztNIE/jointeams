<template>
  <div class="login" clearfix>
    <div class="login-wrap">
      <div class="slogan">
        <p>JoinTeams</p>
      </div>
      <el-row type="flex" justify="center">
        <slot></slot>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "AuthLayout"
}
</script>

<style lang="scss" scoped>
.login {
  background-image: url('../../assets/landing.jpg');
  background-size: cover;
  background-attachment: fixed;
  height: 100%;
  width: 100%;
  min-width: 1000px;
  display: flex;
  align-items: center;
  text-align: center;
  & .slogan {
    font-family: "Courier New", monospace;
    text-align: center;
    color: white;
    font-size: 80px;
    font-weight: 100;
    letter-spacing: 20px;
    padding: 20px;
  }
}

.login-wrap {
  background-size: cover;
  overflow: hidden;
  margin: 0 auto;
}
</style>