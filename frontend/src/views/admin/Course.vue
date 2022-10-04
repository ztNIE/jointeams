<template>
  <p>admin course</p>
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