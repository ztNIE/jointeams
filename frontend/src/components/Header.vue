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
          v-if="this.$store.getters.isUser"
      >
        <el-badge v-if="this.$store.getters.isUser" :is-dot="showDot" class="item">
          <BellFilled class="icon-bell" v-if="isUser" @click="handleClickBell"></BellFilled>
        </el-badge>
      </el-tooltip>
      <el-avatar @click="handleClickAvatar" shape="circle" :size="40" :src="avatar"/>
      <p>{{ this.currentUserName }}</p>
      <el-button type="primary" size="small" @click="handleLogOut">Log Out</el-button>
    </div>
  </div>
</template>

<script>
import {ElMessage, ElNotification} from 'element-plus'
import userAPI from '@/api/user.js'
import {mapActions} from "vuex";
import notificationAPI from "@/api/notification";
import authUtil from "@/util/authUtil";

export default {
  name: 'Header',
  data() {
    return {
      isCollapse: false,
      currentUserName: null,
      showDot: true,
      avatar: null,
      notifications: [],
      isUser: true,
      startPolling: false
    }
  },
  created() {
    let _this = this
    if (authUtil.isLogin()) {
      userAPI.getUserInfo(this.$store.getters.userId).then((res) => {
        let data = res.data.data
        _this.currentUserName = data.firstName + ' ' + data.lastName

        if (data.avatar == null) {
          _this.avatar = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        } else {
          userAPI.getAvatar(data.avatar).then((res) => {
            _this.avatar = 'data:image/jpeg;base64,' + res.data.data.image
          }).catch(() => {
            ElMessage.error('Failed to load the avatar')
          })
        }
      }).catch((err) => {
        ElMessage.error(err)
      })
    }

    if(this.$store.getters.role === "ROLE_ADMIN") {
      this.isUser = false
      this.showDot = false
    }

    this.handleInitNotification()

    this.$emitter.on('updateHeaderAvatar', (newAvatar) => {
      _this.avatar = newAvatar;
    })
  },
  mounted() {
    this.handlePolling()
  },
  methods: {
    ...mapActions(['logout']),
    handleOpenAndClose() {
      this.isCollapse = !this.isCollapse
      this.$emitter.emit('handleSidebar')
      this.$emitter.emit('handleLogoName')
      this.startPolling = false
    },
    handleLogOut() {
      this.logout()
      this.$router.push('/landing')
      ElMessage({
        message: 'You have logged out successfully!',
        type: 'success',
      })
    },
    handleInitNotification() {
      if(authUtil.isLogin()) {
        if(localStorage.getItem("role") === "ROLE_USER") {
          notificationAPI.findAllByUserId(this.$store.getters.userId).then((res) => {
            if(res.data.data.Null === null) {
              this.showDot = false
            } else {
              for(let i = 0; i < res.data.data.NotificationResponseDataList.length; i++) {
                this.notifications.push(res.data.data.NotificationResponseDataList[i].id)
              }
            }
            this.startPolling = true
          })
        }
      }
    },
    handlePolling() {
      window.setInterval(() => {
        if(authUtil.isLogin()) {
          if(localStorage.getItem("role") === "ROLE_USER") {
            if(this.startPolling) {
              notificationAPI.findAllByUserId(this.$store.getters.userId).then((res) => {
                if(res.data.data.Null === null) {
                  this.showDot = false
                } else {
                  this.showDot = true
                  let currentNotifications = res.data.data.NotificationResponseDataList
                  if(currentNotifications.length > this.notifications.length) {
                    for(let i = 0; i < currentNotifications.length; i++) {
                      if(!this.notifications.includes(currentNotifications[i].id)) {
                        this.notifications.push(currentNotifications[i].id)
                        ElNotification({
                          title: "New Notification",
                          message: currentNotifications[i].content + ": " + currentNotifications[i].message,
                          type: 'success',
                        })
                      }
                    }
                  }
                }
              })
            }
          }
        }
      }, 1000)
    },
    handleClickBell() {
      this.$router.push('/notification')
    },
    handleClickAvatar() {
      if(authUtil.isLogin()) {
        if(localStorage.getItem("role") === "ROLE_USER") {
          this.$router.push('/myProfile')
        }
      }
    }
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

  & .el-avatar:hover {
    cursor: pointer;
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