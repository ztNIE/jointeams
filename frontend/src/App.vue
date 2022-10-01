<template>
  <!-- landing page -->
  <Landing v-if="route.name === 'landing'" />

  <!-- log in -->
  <Login v-else-if="route.name === 'login'" />
  
  <!-- register -->
  <Register v-else-if="route.name === 'register'" />

  <!-- 404 -->
  <PageNotFound v-else-if="route.name === '404'" />

  <!-- other pages -->
  <div class="box" v-else>
    <!-- container -->
    <el-container>
      <!-- sidebar -->
      <el-aside :width="asideWidth">
        <Logo />
        <Sidebar />
      </el-aside>

      <el-container>
        <!-- header -->
        <el-header>
          <Header/>
        </el-header>
        <!-- main content -->
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>

</template>

<script>
import { useRoute } from 'vue-router'
import Landing from './views/Landing'
import Register from './views/Register'
import Login from './views/Login'
import Sidebar from './components/Sidebar'
import Header from './components/Header'
import Logo from './components/Logo'
import PageNotFound from './views/404/404'
import {mapActions, mapGetters} from "vuex";
import {ElMessage} from "element-plus";

export default {
  name: 'App',
  components: {
    Landing,
    Login,
    Register,
    Sidebar,
    Header,
    Logo,
    PageNotFound
  },
  data() {
    return {
      asideWidth: "200",
    }
  },
  setup() {
    const route = useRoute();
    // expose to template and other options API hooks
    return {
      route
    }
  },
  methods: {
    ...mapActions(['tryAutoLogin'])
  },
  computed: {
    ...mapGetters(['isUser'])
  },
  created() {
    this.tryAutoLogin().then((result) => {
      console.log(result);
      // TODO: jump to dashboard if in landing/login/register page, stay otherwise
      ElMessage("Auto login success")
    });
  }
}
</script>

<style lang="scss">
* {
  padding: 0;
  margin: 0;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  /* margin-top: 60px; */
}
html, body, #app, .box{
  height: 100%;
}
.el-container {
  height: 100%;
}
.el-container:last-child {
  min-width: 700px;
}
.el-header {
  padding: 0;
  font-size: 35px;
  box-shadow: 3px 8px 10px #E8E8E8;
}
.el-aside {
  box-shadow: 3px 8px 10px #E8E8E8;
  min-height: 500px;
}
</style>
