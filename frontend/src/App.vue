<template>
  <!-- landing page -->
  <Landing v-if="route.name === '/landing'" />

  <!-- log in -->
  <Login v-else-if="route.name === 'login'" />
  
  <!-- register -->
  <SignUp v-else-if="route.name === 'register'" />

  <!-- other pages -->
  <div v-else>
    <router-view />
  </div>

</template>

<script>
import { useRoute } from 'vue-router'
import Landing from './views/landing.vue'
import SignUp from './views/signUp.vue'
import Login from './views/login.vue'

export default {
  name: 'App',
  components: {
    Landing,
    Login,
    SignUp,
  },
  setup() {
    const route = useRoute();
    // expose to template and other options API hooks
    return {
      route
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
