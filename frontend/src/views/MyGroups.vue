<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">My Groups</div>
      </template>
      <el-main>
        <el-scrollbar max-height="450px" v-if="groups.length !== 0">
          <div v-for="group in groups" :key="group.group_id" class="text item">
            <el-card class="group-card" @click="handleToDetail(group.group_id)" shadow="hover">
              <el-icon id="detailBtn-icon"><Right /></el-icon>
              <div class="card-header">
                <span id="name">{{group.group_name}}</span>
                <span id="capacity">(Capacity: {{group.capacity}})</span>
              </div>
              <div class="divider_space"></div>
              <div class="card-content">
                <span v-for="member in group.members" :key="member.name" class="member">
                  <el-avatar :size="40" class="member_avatar" :src="member.avatar" />
                  <span class="member_name"> {{ member.name }} </span>
                </span>
              </div>
            </el-card>
            <div class="divider_space"></div>
          </div>
        </el-scrollbar>
        <el-empty description="No group" v-else />
      </el-main>
    </el-card>
  </div>
</template>

<script>
import GroupAPI from '../api/group.js'
import userAPI from '../api/user.js';
import {ElMessage} from "element-plus";

export default {
  name: 'MyGroups',
  data() {
    return {
      groups: [],
      user_id: null
    }
  },
  async created() {
    this.user_id = this.$store.getters.userId
    GroupAPI.findAllCurrentGroupsOfAUser(this.user_id).then(async (res) => {
      await this.handleGetAvatar(res)
    })
  },
  watch: {
    'groups': function() {
      GroupAPI.findAllCurrentGroupsOfAUser(this.user_id).then(async (res) => {
        await this.handleGetAvatar(res)
      })
    }
  },
  methods: {
    handleToDetail(group_id) {
      this.$router.push({name: "groupDetails", params: {group_id: group_id}});
    },
    async handleGetAvatar(res) {
      let data = res.data.data
      for (let i = 0; i < data.length; i++) {
        for (let j = 0; j < data[i].members.length; j++) {
          if(data[i].members[j].avatar == null) {
            data[i].members[j].avatar = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          } else {
            await userAPI.getAvatar(data[i].members[j].avatar).then((res) => {
              data[i].members[j].avatar = 'data:image/jpeg;base64,' + res.data.data.image
            }).catch(() => {
              ElMessage.error('Fail to load the avatar')
            })
          }
        }
      }
      this.groups = data
    }
  }
}
</script>

<style lang="scss" scoped>
  .common-layout {
    background-color: #CBF3F0;
    height: 100%;
  }
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 15px;
    padding-top: 5px;
  }
  .card-content {
    display: inline-flex;
    justify-content: space-between;
    align-items: center;
    position: relative
  }
  .member {
    display:inline-block;
    margin-right:20px;
    align-items: center;
    display: flex;
    align-items: center;
  }
  #detailBtn-icon {
    color: #2EC4B6;
    font-size: 2em;
    position: absolute;
    right: 38px;
    bottom: 42px;
  }
  #name {
    font-size: large;
    color: #FF9F1C;
    font-weight: bold;
  }
  #capacity {
    font-size: small;
    color: #787878;
    padding-right: 5px;
  }
  .member_avatar {
    vertical-align: middle;
    margin-right: 10px;
  }
  .member_name {
    display: block;
    font-size: 16px;
    position: relative;
    top: 4px;
    margin-left: 2px;
    margin-right: 13px;
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
  }
  .group-card {
    position: relative;
    margin-bottom: 5px;
  }
  .group-card:hover {
    background-color: #CBF3F0;
    cursor: pointer;
  }
</style>