<template>
    <p>notification</p>
</template>

<script>


import authenticateIdentity from "@/util/login";

export default {
  name: 'Notification',
  beforeCreate() {
    authenticateIdentity("ROLE_USER")
  }
}
</script>

<style lang="scss" scoped>
</style>