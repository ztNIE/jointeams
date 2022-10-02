<template>
  <div class="clear-fix header">
    <!-- left -->
    <div class="left vertical-center">
      <Expand v-if="isCollapse" class="icon-expand" @click="handleOpenAndClose"></Expand>
      <Fold v-else class="icon-expand" @click="handleOpenAndClose"></Fold>
    </div>

    <!-- right -->
    <div class="right vertical-center">
      <el-tooltip
          class="box-item"
          effect="light"
          content="Notification"
          placement="left"
      >
        <el-badge :is-dot="showDot" class="item">
          <BellFilled class="icon-bell"></BellFilled>
        </el-badge>
      </el-tooltip>
      <el-avatar shape="circle" :size="40" :src="avatar"/>
      <p>{{ this.currentUserName }}</p>
      <el-button type="primary" size="small" @click="handleLogOut">Log Out</el-button>
    </div>
  </div>
</template>

<script>
import {ElMessage} from 'element-plus'
import userAPI from '@/api/user.js'
import {mapActions} from "vuex";


export default {
  name: 'Header',
  data() {
    return {
      isCollapse: false,
      currentUserName: null,
      showDot: true,
      avatar: null
    }
  },
  created() {
    let _this = this
    if (this.$store.getters.userId != null) {
      userAPI.getUserInfo(this.$store.getters.userId).then((res) => {
        let data = res.data.data
        _this.currentUserName = data.firstName + ' ' + data.lastName
        _this.avatar = data.avatar == null ? 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' : data.avatar
      }).catch((err) => {
        ElMessage.error(err.data.msg)
      })
    }
  },
  methods: {
    ...mapActions(['logout']),
    handleOpenAndClose() {
      this.isCollapse = !this.isCollapse
      this.$emitter.emit('handleSidebar')
      this.$emitter.emit('handleLogoName')
    },
    handleLogOut() {
      this.logout()
      this.$router.push('/landing')
      ElMessage({
        message: 'You have logged out successfully!',
        type: 'success',
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.icon-expand {
  width: 30px;
  color: #B5B5B5;
  cursor: pointer;
  margin-top: 7px;
}

.icon-bell {
  width: 25px;
  color: #B5B5B5;
}

.icon-bell:hover {
  cursor: pointer;
  color: #2EC4B6;
}

:deep(.el-page-header__left::after) {
  height: 0;
}

.toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.clear-fix::after {
  content: '';
  display: block;
  clear: both;
}

.header {
  padding: 10px 0;
  font-size: 18px;
  height: 40px;

  & .left {
    float: left;
  }

  & .right {
    float: right;
  }

  & .vertical-center {
    display: flex;
    align-items: center;
  }

  & p {
    vertical-align: middle;
    margin-right: 30px;
    margin-top: 5px;
  }

  & .el-avatar {
    margin-right: 10px;
    margin-left: 30px;
  }

  & .el-button {
    height: 32px;
  }

  & .el-badge {
    margin-top: 10px;
  }
}

// tooltip
.left .el-tooltip__popper,
.right .el-tooltip__popper {
  padding: 8px 10px;
}
</style>