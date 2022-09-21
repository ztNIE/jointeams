<template>
<el-menu default-active="1" :router="true" class="el-menu-vertical-demo" :collapse="isCollapse">
  <el-menu-item index="0" disabled=true class="logo">
  </el-menu-item>
  <el-menu-item index="1">
    <i class="el-icon-menu"></i>
    <span>导航二</span>
  </el-menu-item>
  <el-menu-item index="4">
    <i class="el-icon-setting"></i>
    <span>导航四</span>
  </el-menu-item>
</el-menu>
</template>

<script>
// import {router} from '@/router/index'

export default {
    name: 'Sidebar',
    components: {
        // router
    },
    data() {
        return {
            isCollapse: false
        };
    },
    created() {
        let _this = this
        this.$emitter.on('handleSidebar', () => {
            _this.isCollapse = !_this.isCollapse
        })
    },
    methods: {
    }
}
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
}
.el-menu-vertical-demo {
    min-height: 100%;
}
.logo:hover {
    cursor: default;
}
</style>