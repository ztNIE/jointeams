<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">Notification</div>
      </template>
      <el-main>
        <el-scrollbar max-height="450px" v-if="notifications.length !== 0">
          <div v-for="notification in notifications" :key="notification.id" class="text item">
            <el-card class="notification-card">
              <div class="card-header">
                <span id="type">{{notification.content}}</span>
                <span id="from">From: {{from(notification.type, notification)}}</span>
                <div class="action-button">
                  <el-button v-if="notification.type === 0 || notification.type === 1" type="primary"
                             @click="actionOnNotification(notification,0)">{{ actionList[0] }}</el-button>
                  <el-button v-if="notification.type === 0 || notification.type === 1" type="warning"
                             @click="actionOnNotification(notification,1)">{{actionList[1]}}</el-button>
                  <el-button type="info" @click="actionOnNotification(notification,2)" >{{actionList[2]}}</el-button>
                </div>
              </div>
              <div class="card-content">
                <span id="time">Time: {{parseTime(notification)}}</span>
                <br>
                <span id="message">{{notification.message}}</span>
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


<script >
import { parseTime } from '@/util/ParseTime'
import notificationAPI from '../api/notification.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import authUtil from "@/util/authUtil";
import store from "@/store";

export default {
  name: 'Notification',
  beforeCreate() {
    authUtil.authenticateIdentity("ROLE_USER")
  },
  data() {
    return {
      actionList: ["Accept","Decline","Delete"],
      notifications: []
    }
  },
  mounted() {
    let userId = store.getters.userId
    // let userId = 3
    notificationAPI.findAllByUserId(userId).then((res) => {
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
  }
}
</script>

<style lang="scss" scoped>

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.card-content {
  display: block;
  justify-content: space-between;
  align-items: center;
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
  font-weight: bold;
  font-size: xx-large;
}
.box-card {
  min-height: 580px;
}
.notification-card:hover {
  background-color: #CBF3F0;
}
.notification-card{
  min-height: 120px;
}

#type{
  font-size: 20px;
  min-width: 400px;
  color: #f99827;
}

#from{
  min-width: 300px;
}
.action-button {
  text-align:right;
  min-width: 300px;
}
.action-button > .el-button {
  width: 80px;
}
#time{
  font-size: 16px;
  min-width: 1000px;
  color: gray;
}
#message{
  font-size: 16px;
  min-width: 1000px;
  color: black;
}
</style>