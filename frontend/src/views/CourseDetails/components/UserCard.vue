<template>
  <el-row :gutter="20">
    <el-col :span="4">
      <el-avatar
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
      />
    </el-col>
    <el-col :span="20">
      <div>
        <header class="username">{{ firstName }} {{ lastName }}</header>
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
  props: ['firstName', 'lastName', 'email', 'id']
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