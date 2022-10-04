<template>
  <p>admin comment</p>
</template>

<script>




import authUtil from "@/util/authUtil";

export default {
  name: 'Dashboard',
  beforeCreate() {
    authUtil.authenticateIdentity("ROLE_ADMIN")
  },
}
</script>

<style lang="scss" scoped>
</style>