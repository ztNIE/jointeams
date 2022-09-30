<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <el-header class="main_header">My Groups</el-header>
      <el-main>
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
                      src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                  />
                </el-avatar>
                <span class="member_name"> {{ member.name }} </span>
              </span>
            </div>
          </el-card>
          <div class="divider_space"></div>
        </div>
      </el-main>
    </el-card>
  </div>
</template>

<script>
// import cookieUtils from '../utils/cookie.js'
// import GroupAPI from '../api/group.js'

export default {
  name: 'MyGroups',
  data() {
    return {
      groups: [
        {
          "group_id": 1,
          "group_name": "ELEC5619_Group1",
          "members": [
            {
              "name": "Yuyun Liu",
              "avatar": null
            },
            {
              "name": "Alice Smith",
              "avatar": null
            },
            {
              "name": "Vera Wiesley",
              "avatar": null
            }
          ],
          "capacity": 3
        },
        {
          "group_id": 2,
          "group_name": "SOFT3888_Group1",
          "members": [
            {
              "name": "Yuyun Liu",
              "avatar": null
            },
            {
              "name": "Cole Miloska",
              "avatar": null
            }
          ],
          "capacity": 4
        },
        {
          "group_id": 5,
          "group_name": "COMP3308_Group2",
          "members": [
            {
              "name": "Yuyun Liu",
              "avatar": null
            }
          ],
          "capacity": 2
        }
      ]
    }
  },
  mounted() {
    // var userId = cookieUtils.getTokenInCookies()
    // console.log(userId)
    // var userId = 1
    // GroupAPI.findAllCurrentGroupsOfAUser(userId).then((res) => {
    //   console.log((res))
    //   this.groups = res
    // })

  },
  methods: {
    errorHandler() {
      return true
    },
    handleToDetail(group_id) {
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
</style>