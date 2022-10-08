<template>
    <Profile :userId="userId"/>
</template>

<script>
import Profile from './Profile.vue'
import { useRoute } from 'vue-router'
export default {
  name: 'UserProfile',
  data() {
    return {
      userId: null
    }
  },
  components: {
    Profile
  },
  created() {
    const route = useRoute()
    this.userId = route.params.id
  }
}
</script>

<style lang="scss" scoped>
</style>