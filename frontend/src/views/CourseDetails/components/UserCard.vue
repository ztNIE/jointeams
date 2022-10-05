<template>
  <el-card>
    <template #header>
      <header>UserCard</header>
    </template>

    <div>
      <span>{{firstName}}</span>
      <span>{{lastName}}</span>
      <span>{{id}}</span>
      <span>{{email}}</span>
    </div>
  </el-card>
</template>

<script>
export default {
  name: "UserCard",
  props: {
    firstName: String,
    lastName: String,
    id: Number,
    email: String
  }
}
</script>

<style scoped>

</style>