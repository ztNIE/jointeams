<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">My Groups</div>
      </template>
      <el-main>
        <el-scrollbar max-height="450px" v-if="groups.length !== 0">
          <div v-for="group in groups" :key="group.group_id" class="text item">
            <el-card class="group-card" @click="handleToDetail(group.group_id)">
              <div class="card-header">
                <span id="name">{{group.group_name}}</span>
                <span id="capacity">(Capacity: {{group.capacity}})</span>
                <el-icon id="detailBtn-icon"><Right /></el-icon>
              </div>
              <div class="divider_space"></div>
              <div class="card-content">
                <span v-for="member in group.members" :key="member.name" class="member">
                  <el-avatar class="member_avatar" :src="member.avatar" />
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
  }
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .card-content {
    display: inline-flex;
    justify-content: space-between;
    align-items: center;
  }
  .member {
    display:inline-block;
    margin-right:20px;
    align-items: center;
  }
  #detailBtn-icon {
    color: #2EC4B6;
    font-size: 3em;
  }
  #name {
    font-size: large;
    color: #FF9F1C;
    font-weight: bold;
  }
  #capacity {
    font-size: small;
    color: #787878;
  }
  .member_avatar {
    height: 25px;
    width: 25px;
    vertical-align: middle;
    margin-right: 10px;
  }
  .member_name {
    font-size: small;
    vertical-align: center;
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
  .group-card:hover {
    background-color: #CBF3F0;
  }
</style>