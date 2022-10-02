<template>
  <el-menu :default-active="this.$router.currentRoute.value.meta.highlight" :router="false"
           class="el-menu-vertical-demo" :collapse="isCollapse">
    <el-menu-item v-for="route in routes" :key="route.name" :index="route.name" @click="handleTransfer(route.path)">
      <el-icon>
        <component :is="route.meta.icon"></component>
      </el-icon>
      <span>{{ route.meta.capitalName }}</span>
    </el-menu-item>
    <el-menu-item :disabled="true" class="back">
      <el-icon @click="handleBack()">
        <Back></Back>
      </el-icon>
      <span @click="handleBack()" class="back-text">Back</span>
    </el-menu-item>
  </el-menu>
</template>

<script>

export default {
  name: 'Sidebar',
  data() {
    return {
      isCollapse: false,
      routes: [],
      active: 'dashboard'
    }
  },
  created() {
    let _this = this
    this.$emitter.on('handleSidebar', () => {
      _this.isCollapse = !_this.isCollapse
    })

    // filter out the routes that does not need a tab
    // store the routes in local storage since addRoutes() does not save the new route in this.$router.options
    let localStorageRoutes = localStorage.getItem('routes')
    if (localStorageRoutes && JSON.parse(localStorageRoutes)[0].children.length > 5) {
        let temp = JSON.parse(localStorageRoutes)[0].children.slice(0, JSON.parse(localStorageRoutes)[0].children.length - 5)
        this.routes = temp.filter((item) => {
            return !item.meta.hidden
        })
        this.routes.reverse()
    }
  },
  methods: {
    handleBack() {
      this.$router.back()
    },
    handleTransfer(path) {
      this.$router.push(path)
    }
  }
}
</script>

<style lang="scss" scoped>
.el-menu {
  border: none;
}

.el-menu-item {
  padding-left: 25px !important;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  background-color: #F0FFFF;
}

.el-menu-vertical-demo {
  min-height: 100%;
  background-color: #F0FFFF;
}

.el-menu--collapse {
  width: calc(var(--el-menu-icon-width) + var(--el-menu-base-level-padding) * 2 + 1);
}

span {
  margin-left: 5px
}

.back {
  width: 200px;
  position: absolute;
  bottom: 80px;
}

.back:hover {
  color: #FF9F1C;
}

.el-menu-item.is-disabled {
  opacity: 1;
  cursor: pointer;
  background: none !important;
}

.back-text {
  display: inline-block;
  height: 56px;
  width: 110px;
}
</style>