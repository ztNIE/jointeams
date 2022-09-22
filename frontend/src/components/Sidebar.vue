<template>
<el-menu :default-active="active" :router="true" class="el-menu-vertical-demo" :collapse="isCollapse">
  <el-menu-item v-for="route in routes" :key="route.name" :index="route.name">
    <el-icon>
        <component :is="route.icon"></component>
    </el-icon>
    <span>{{route.capitalName}}</span>
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
// import { useRoute } from 'vue-router'

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
        this.routes = this.$router.options.routes[0].children.slice(3)
        console.log(this.$router.currentRoute.value.name)
    },
    methods: {
        handleBack() {
            this.$router.back()
        }
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
.back {
    width: 200px;
    position: absolute;
    bottom: 80px;
}
.back:hover{
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