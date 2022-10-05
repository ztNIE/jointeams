<template>
  <div class="wrapper">
    <el-row :gutter="20" class="half-height">
      <!-- basic user info -->
      <div class="info-box full-height">
        <el-card class="full-height info-card">
          <el-row :gutter="0" class="full-height">
            <!-- avatar & tag -->
            <el-col :span="8" class="full-height avatar-tag">
              <el-avatar :size="90" :src="avatar" />
              <!-- self-tag -->
              <div class="tag-box">
                <span>Self-tag: </span>
                <el-tag v-if="selfTag != null" class="mx-1 info-tag" effect="dark" type="" size="large">{{tagName[selfTag - 1].label}}</el-tag>
                <span v-else>None</span>
              </div>
              <!-- gift-tag -->
              <div class="tag-box">
                <span>Gift-tag: </span>
                <el-tag v-for="comment in comments" :key="comment.id" class="mx-1 info-tag" effect="dark" type="warning" size="large">{{tagName[comment.tag - 1].label}}</el-tag>
                <span v-if="comments.length === 0">None</span>
              </div>
            </el-col>

            <!-- basic info -->
            <el-col :span="16" class="full-height">
              <!-- personal info -->
              <div class="personal-info">
                <p class="name">{{this.userName}}</p>
                <p class="info-list">Faculty: {{this.userInfo.faculty}}</p>
                <p class="info-list">Degree: {{this.userInfo.degree}}</p>
                <p class="info-list">Email: {{this.userInfo.email}}</p>
              </div>
              <!-- courses -->
              <div class="courses">
                <!-- current course -->
                <div class="current-course">
                  <p class="name">Current Courses</p>
                </div>
                <!-- past course -->
                <div class="past-course"></div>
              </div>
            </el-col>
          </el-row> 
        </el-card>
      </div>
    </el-row>
    <el-row :gutter="20" class="half-height">
      <!-- description -->
      <el-col :span="8" class="full-height">
        <el-card class="full-height">
          <template #header>
            <div class="card-header">
              <span class="card-title">Description</span>
            </div>
          </template>
          <div class="full-height"></div>
        </el-card>
      </el-col>
      <!-- comments -->
      <el-col :span="16" class="full-height">
        <el-card class="full-height">
          <template #header>
            <div class="card-header">
              <span class="card-title">Comments</span>
            </div>
          </template>
          <div class="full-height"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAPI from '@/api/user.js'
import {ElMessage} from 'element-plus'
import tagName from '@/tags.json'

export default {
  name: 'Profile',
  props: { userId: String },
  data() {
    return {
      avatar: null,
      userName: null,
      tagName: tagName,
      selfTag: null,
      giftTag: [],
      comments: [],
      userInfo: {}
    }
  },
  created() {
    let _this = this
    if (this.$props.userId != null) {
      userAPI.getUserInfo(this.$props.userId).then((res) => {
        let data = res.data.data
        console.log(data)
        _this.userName = data.firstName + ' ' + data.lastName
        _this.avatar = data.avatar == null ? 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' : data.avatar
        _this.selfTag = data.selfTag
        _this.comments = data.comment
        _this.userInfo = data

      }).catch((err) => {
        ElMessage.error(err.data.msg)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.wrapper {
  height: 100%;
  min-width: 1200px;
}
.full-height {
  height: 100%;
}
.half-height {
  height: 50%;
}
:deep(.el-card__body) {
  height: 100%;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  & .card-title {
    font-size: 20px;
    font-weight: 600;
  }
}
.info-box {
  box-sizing: border-box;
  width: 100%;
  padding: 0 10px 15px 10px;
  & .info-card {
    width: 100%;
    & .avatar-tag {
      border-right: solid #E8E8E8 1.5px;
      & .el-avatar {
        display: block;
        margin-left: 40%;
        margin-top: 8%;
        margin-bottom: 20px;
      }
      & .tag-box {
        padding-left: 10px;
        & .info-tag{
          margin-left: 5px;
          margin-top: 10px;
        }
      }
    }
  }
}
</style>