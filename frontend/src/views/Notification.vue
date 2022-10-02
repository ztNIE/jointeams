<template>
    <p>notification</p>
</template>

<script>


import authUtil from "@/util/authUtil";

export default {
  name: 'Notification',
  beforeCreate() {
    authUtil.authenticateIdentity("ROLE_USER")
  }
}
</script>

<style lang="scss" scoped>
</style>