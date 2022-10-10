<template>
  <el-row :gutter="20">
    <el-col :span="4">
      <el-avatar
          :src="avatar"
      />
    </el-col>
    <el-col :span="20">
      <div>
        <header class="username">{{ fullName }}</header>
      </div>
      <div class="email">
        {{ email }}
      </div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "UserCard",
  props: ['fullName', 'email', 'id', 'avatar']
}
</script>

<style scoped>
.username {
  font-weight: bold;
}
.email {
  color: #787878;
}
</style>