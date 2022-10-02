<template>
    <Profile/>
</template>

<script>
// import { ElMessage } from 'element-plus'
import Profile from './Profile.vue'

export default {
  name: 'MyProfile',
  components: {
    Profile
  },
  created() {
    // if (!this.$store.getters.isLogIn) {
    //   ElMessage.info('Please log in first.')
    //   this.$router.push('/sign-in')
    // }
  }
}
</script>

<style lang="scss" scoped>
</style>