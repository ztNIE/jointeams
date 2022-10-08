<template>
    <Profile :userId="userId"/>
</template>

<script>
import Profile from './Profile.vue'

export default {
  name: 'MyProfile',
  data() {
    return {
      userId: null
    }
  },
  components: {
    Profile
  },
  created() {
    this.userId = this.$store.getters.userId
  }
}
</script>

<style lang="scss" scoped>
</style>