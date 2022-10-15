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
                <span class="none" v-else>None</span>
              </div>
              <!-- gift-tag -->
              <div class="tag-box">
                <span>Gift-tag: </span>
                <el-tag v-for="comment in comments" :key="comment.id" class="mx-1 info-tag" effect="dark" type="warning" size="large">{{tagName[comment.tag - 1].label}}</el-tag>
                <span class="none" v-if="comments.length === 0">None</span>
              </div>
            </el-col>

            <!-- basic info -->
            <el-col :span="16" class="full-height relative-parent">
              <el-button type="primary" link @click="handleEdit" v-if="this.userId === this.$store.getters.userId">edit</el-button>
              <!-- personal info -->
              <div class="personal-info">
                <p class="name userName">{{this.userName}}</p>
                <p class="info-list"><span class="bold">Faculty:</span> Faculty of {{this.userInfo.faculty}}</p>
                <p class="info-list"><span class="bold">Degree:</span> {{this.userInfo.degree}}</p>
                <p class="info-list"><span class="bold">Email:</span> {{this.userInfo.email}}</p>
              </div>
              <!-- courses -->
              <div class="courses">
                <!-- current course -->
                <div class="current-course">
                  <p class="name">Current Courses</p>
                  <span class="none" v-if="currentCourse.length === 0">None</span>
                  <ul v-else>
                    <li v-for="course in currentCourse" :key="course.id"><span class="">{{course.code}}</span> <span class="course-name">{{course.name}}</span></li>
                  </ul>
                </div>
                <!-- past course -->
                <div class="past-course">
                  <p class="name">Past Courses</p>
                  <span class="none" v-if="pastCourse.length === 0">None</span>
                  <ul v-else>
                    <li v-for="course in pastCourse" :key="course.id"><span class="">{{course.code}}</span> <span class="course-name">{{course.name}}</span></li>
                  </ul>
                </div>
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
          <div class="full-height">
            <p v-if="description != null || description === ''">{{description}}</p>
            <span class="none" v-else>None</span>
          </div>
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
          <div class="comments">
            <span class="none" v-if="comments.length === 0">None</span>
            <div class="comment-box" v-for="comment in comments" :key="comment.id">
              <!-- avatar -->
              <div class="avatar">
                <el-avatar :size="60" :src="comment.image" />
              </div>
              <!-- content -->
              <div class="content">
                <div class="first-line">
                  <p class="sender-name"><span class="name">{{comment.senderName}}</span> from {{comment.courseCode}}</p>
                  <p class="time-stamp">{{comment.timeStamp}}</p>
                </div>
                <p v-if="comment.tag != null">gives {{userName}} a&nbsp;
                  <el-tag class="mx-1 info-tag" type="warning" size="small">{{tagName[comment.tag - 1].label}}</el-tag>
                </p>
                <p class="comment-content">"{{comment.content}}"</p>
              </div>
            </div>
          </div>
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
  props: { userId: [String, Number] },
  data() {
    return {
      avatar: null,
      userName: null,
      tagName: tagName,
      selfTag: null,
      giftTag: [],
      comments: [],
      userInfo: {},
      currentCourse: [],
      pastCourse: [],
      description: null
    }
  },
  created() {
    let _this = this
    if (this.$props.userId != null) {
      userAPI.getUserInfo(this.$props.userId).then((res) => {
        let data = res.data.data
        _this.userName = data.firstName + ' ' + data.lastName
        _this.selfTag = data.selfTag
        _this.comments = data.comment
        _this.currentCourse = data.currentCourse
        _this.pastCourse = data.previousCourse
        _this.description = data.description
        _this.userInfo = data

        // get avatar
        if (data.avatar == null) {
          _this.avatar = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        } else {
          userAPI.getAvatar(data.avatar).then((res) => {
            _this.avatar = 'data:image/jpeg;base64,' + res.data.data.image
          }).catch(() => {
            ElMessage.error('Failed to load the avatar')
          })
        }

        _this.comments = _this.comments.filter((comment) => {
          return !comment.isHide
        })

        for (let i = 0; i < _this.comments.length; i ++) {
          let timeStamp = new Date(_this.comments[i].timeStamp)
          let date = timeStamp.getDate() < 10 ? '0' + timeStamp.getDate() : timeStamp.getDate()
          let month = timeStamp.getMonth() < 10 ? '0' + timeStamp.getMonth() : timeStamp.getMonth()
          let year = timeStamp.getFullYear()
          _this.comments[i].timeStamp = date + '/' + month + '/' + year
          
          // get avatar
          if (_this.comments[i].avatar == null) {
            _this.comments[i].image = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
          } else {
            userAPI.getAvatar(_this.comments[i].avatar).then((res) => {
              _this.comments[i].image = 'data:image/jpeg;base64,' + res.data.data.image
            }).catch(() => {
              ElMessage.error('Failed to load the avatar')
            })
          }
        }
      }).catch((err) => {
        ElMessage.error(err.data.msg)
      })
    }
  },
  methods: {
    handleEdit() {
      this.$router.push('/editProfile')
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
.none {
  margin-left: 5px;
  color: #B5B5B5;
  font-weight: 700;
  line-height: 35px;
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
    & .name {
      font-size: 18px;
      font-weight: 500;
      padding-top: 5px;
      padding-bottom: 5px;
    }
    & .userName {
      color: #EE7600;
      font-weight: 700;
    }
    & .bold {
      font-weight: 500;
    }
    & .relative-parent {
      position: relative;
    }
    & .el-button {
      position: absolute;
      right: 20px;
      top: 5px;
    }
    & .el-button:hover {
      text-decoration: underline;
    }
    & .personal-info {
      box-sizing: border-box;
      height: 40%;
      min-height: 105px;
      padding-left: 25px;
    }
    & .courses {
      height: 70%;
      display: flex;
      flex-direction: row;
      & .past-course {
        margin-left: 2%;
      }
      & .current-course, .past-course {
        box-sizing: border-box;
        width: 380px;
        height: 90%;
        padding-left: 25px;

        & ul {
          list-style: none;
          height: 80%;
          overflow: auto;
          & li {
            padding: 2px 0;
            font-size: 15px;
            & .course-name {
              color: #828282;
            }
          }
        }
      }
    }
  }
}
.comments {
  height: 80%;
  overflow: auto;

  & .comment-box {
    display: flex;
    margin-bottom: 25px;

    & .avatar {
      width: 80px;
      margin-left: 10px;
    }
    & .content {
      width: 100%;

      & .first-line {
        content: '';
        display: table;
        clear: both;
        width: 100%;

        & .sender-name {
          float: left;

          & .name {
            font-weight: 600;
          }
        }
        & .time-stamp {
          float: right;
          font-size: 13px;
          font-weight: 500;
          color:#828282;
        }
      }

      & .comment-content {
        font-style: italic;
        padding-top: 7px;
        max-width: 85%;
      }
    }
  }
}
</style>