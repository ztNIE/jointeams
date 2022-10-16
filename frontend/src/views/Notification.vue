<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">Notification</div>
      </template>
      <el-main>
        <el-scrollbar max-height="450px" v-if="notifications && notifications.length !== 0">
          <div v-for="notification in notifications" :key="notification.id" class="text item">
            <el-card class="notification-card" shadow="hover">
              <div class="action-button">
                <el-button v-if="notification.type === 0 || notification.type === 1" type="primary"
                            @click="actionOnNotification(notification,0)">{{ actionList[0] }}</el-button>
                <el-button v-if="notification.type === 0 || notification.type === 1" type="warning"
                            @click="actionOnNotification(notification,1)">{{actionList[1]}}</el-button>
                <el-button type="info" plain @click="actionOnNotification(notification,2)" >{{actionList[2]}}</el-button>
              </div>
              <span id="time">{{parseTime(notification)}}</span>
              <div class="card-header">
                <p id="type">{{notification.content}}</p>
                <p id="from"
                   @click="handleNotificationClick(notification)"

                >From: {{from(notification.type, notification)}}</p>
              </div>
              <div class="card-content">
                <span id="message" @click="handleNotificationClick2(notification)">{{notification.message}}</span>
              </div>
            </el-card>
            <div class="divider_space"></div>
          </div>
        </el-scrollbar>
        <el-empty description="No notification" v-else />
      </el-main>
    </el-card>
  </div>
</template>


<script>
import {parseTime} from '@/util/ParseTime'
import notificationAPI from '../api/notification.js'
import {ElMessage, ElMessageBox} from 'element-plus'
import store from "@/store";

export default {
  name: 'Notification',
  data() {
    return {
      actionList: ["Accept","Decline","Delete"],
      notifications: [],
    }
  },
  mounted() {
    let userId = store.getters.userId
    notificationAPI.findAllByUserId(userId).then((res) => {
      if(res.data.data.NotificationResponseDataList !== null)
        this.notifications = res.data.data.NotificationResponseDataList
    })
  },
  methods: {
    actionOnNotification(notification,action) {

      ElMessageBox.confirm(
          'You are going to ' + this.actionList[action].toLowerCase() + ' this ' + notification.content.toLowerCase() + '?',
          'Warning',
          {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        notificationAPI.actionOnNotification(notification.id, action).then((res) => {
          if(res !== null)
          {
            ElMessage({
              type: 'success',
              message: res.data.msg,
            })
            let index = this.notifications.indexOf(notification)
            this.notifications.splice(index, 1)
          }
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: this.actionList[action] + ' is cancelled',
        })
      })
    },
    parseTime(notification)
    {
      return parseTime(notification.timestamp)
    },
    from(type, notification){
      if(type === 1 || type === 4 || type === 5)
        return notification.userName
      else
        return notification.groupName
    },
    handleNotificationClick(notification) {
      if (notification.type === 1 || notification.type === 4 || notification.type === 5 ) {
        this.$router.push(`userProfile/${notification.userId}`)
      } else if (notification.type === 0 || notification.type === 2 || notification.type === 3) {
        this.$router.push(`groupDetails/${notification.groupId}`)
      }
    },
    handleNotificationClick2(notification) {
      if (notification.type === 1 || notification.type === 5 ) {
        this.$router.push(`userProfile/${notification.userId}`)
      } else if (notification.type === 0 || notification.type === 2 || notification.type === 3 || notification.type === 4) {
        this.$router.push(`groupDetails/${notification.groupId}`)
      }
    }
  }
}
</script>

<style lang="scss" scoped>

.card-header {
  // display: flex;
  // justify-content: space-between;
  // align-items: center;
  margin-bottom: 10px;
}
.card-content {
  display: block;
  justify-content: space-between;
  align-items: center;
  width: 75%;
}
#name {
  font-size: large;
  color: #FF9F1C;
  font-weight: bold;
}
.divider_space {
  height: 3px;
}

.main_header {
  color: black;
  font-weight: 500;
  font-size: 23px;
}
.box-card {
  min-height: 580px;
  height: 100%;
  min-width: 1200px;
}
.notification-card:hover {
  background-color: #CBF3F0;
}
.notification-card{
  min-height: 120px;
  min-width: 800px;
  position: relative;
}

#type{
  font-size: 20px;
  min-width: 400px;
  color: #f99827;
  font-weight: 500;
}

#from{
  width: 30%;
  color: gray;
  cursor: pointer;
}
.action-button {
  text-align:right;
  min-width: 300px;
  position: absolute;
  right: 30px;
  bottom: 35px;
}
.action-button > .el-button {
  width: 80px;
}
#time{
  font-size: 14px;
  color: gray;
  font-weight: 500;
  position: absolute;
  right: 30px;
  top: 20px;
}
#message{
  display: block;
  padding-top: 4px;
  font-size: 16px;
  // min-width: 1000px;
  color: black;
  font-style: italic;
  cursor: pointer;
}
.common-layout {
  height: 100%;
}
</style>