<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">My Groups</div>
      </template>
      <el-main>
        <el-scrollbar max-height="450px" v-if="groups.length !== 0">
          <div v-for="group in groups" :key="group.group_id" class="text item">
            <el-card class="group-card">
              <div class="card-header">
                <span id="name">{{group.group_name}}</span>
                <span id="capacity">(Capacity: {{group.capacity}})</span>
                <el-button @click="handleToDetail(group.group_id)" text>
                  <el-icon id="detailBtn-icon"><Right /></el-icon>
                </el-button>
              </div>
              <div class="divider_space"></div>
              <div class="card-content">
                <span v-for="member in group.members" :key="member.name" class="member">
                  <el-avatar class="member_avatar" src={{member.avatar}} @error="errorHandler">
                    <img
                        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                    />
                  </el-avatar>
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
// import cookieUtils from '../utils/cookie.js'
import GroupAPI from '../api/group.js'
import authUtil from "@/util/authUtil";

export default {
  name: 'MyGroups',
  data() {
    return {
      groups: [],
      user_id: null
    }
  },
  mounted() {
    this.user_id = this.$store.getters.userId
    console.log(this.user_id)
    GroupAPI.findAllCurrentGroupsOfAUser(this.user_id).then((res) => {
      this.groups = res.data.data
    })
  },
  watch: {
    'groups': function() {
      GroupAPI.findAllCurrentGroupsOfAUser(this.user_id).then((res) => {
        this.groups = res.data.data
      })
    }
  },
  beforeCreate() {
    authUtil.authenticateIdentity("ROLE_USER")
  },
  methods: {
    errorHandler() {
      return true
    },
    handleToDetail(group_id) {
      // console.log(group_id)
      this.$router.push({name: "groupDetails", params: {group_id: group_id}});
    },
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