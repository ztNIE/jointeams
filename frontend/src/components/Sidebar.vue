<template>
<el-menu default-active="dashboard" :router="true" class="el-menu-vertical-demo" :collapse="isCollapse">
  <el-menu-item v-for="route in routes" :key="route.name" :index="route.name">
    <el-icon>
        <component :is="route.icon"></component>
    </el-icon>
    <span>{{route.capitalName}}</span>
  </el-menu-item>
</el-menu>
</template>

<script>

export default {
    name: 'Sidebar',
    data() {
        return {
            isCollapse: false,
            routes: []
        };
    },
    created() {
        let _this = this
        this.$emitter.on('handleSidebar', () => {
            _this.isCollapse = !_this.isCollapse
        })
        this.routes = this.$router.options.routes[0].children
        this.routes.splice(0, 3)
        console.log(this.routes);
    },
    methods: {
    }
}
</script>

<style lang="scss" scoped>
.el-menu {
    border: none;
}
.el-menu-item {
    padding-left: 25px!important;
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
    width: calc( var(--el-menu-icon-width) + var(--el-menu-base-level-padding) * 2 + 1 );
}
span {
    margin-left: 5px
}
</style>