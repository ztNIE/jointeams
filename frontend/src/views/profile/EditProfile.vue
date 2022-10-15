<template>
  <div class="wrapper">
    <el-card shadow="always" class="full-height card-box">
      <el-row class="full-height">
          <el-col :span="10" class="full-height info-box">
            <!-- avatar -->
            <div class="avatar-box">
              <el-avatar :size="90" :src="avatar" />
              <el-upload
                class="avatar-uploader"
                action="#"
                :auto-upload="false"
                :show-file-list="false"
                :on-change="handleUploadAvatar">
                <el-button type="primary" plain><el-icon><Edit /></el-icon>&nbsp;&nbsp;Change Avatar</el-button>
              </el-upload>
            </div>
            <!-- info form -->
            <div class="info-form">
              <el-form :model="userInfo" :rules="rules" ref="ruleForm" label-width="125px" class="demo-ruleForm">
                <!-- first name -->
                <el-form-item label="First Name" prop="firstName">
                  <el-input v-model="userInfo.firstName" class="input-width"></el-input>
                </el-form-item>
                <!-- last name -->
                <el-form-item label="Last Name" prop="lastName">
                  <el-input v-model="userInfo.lastName" class="input-width"></el-input>
                </el-form-item>
                <!-- self-tag -->
                <el-form-item label="Self-tag" prop="selfTag">
                  <el-select v-model="userInfo.selfTag">
                    <el-option label="None" value="None"></el-option>
                    <el-option v-for="tag in tagName" :key="tag.value" :label="tag.label" :value="tag.value"></el-option>
                  </el-select>
                </el-form-item>
                <!-- faculty -->
                <el-form-item label="Faculty" prop="faculty">
                  <el-select v-model="userInfo.faculty">
                    <el-option v-for="faculty in faculties" :key="faculty" :label="faculty.name" :value="faculty.name"></el-option>
                  </el-select>
                </el-form-item>
                <!-- degree -->
                <el-form-item label="Degree" prop="degree">
                  <el-input v-model="userInfo.degree"></el-input>
                </el-form-item>
                <!-- description -->
                <el-form-item label="Description" prop="description">
                  <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 4}" v-model="userInfo.description" maxlength="300" show-word-limit></el-input>
                </el-form-item>
                <el-form-item>
                  <div class="btns">
                    <el-button type="info" plain @click="handleCancel()">Cancel</el-button>
                    <el-button type="warning" @click="submitForm('ruleForm')">Confirm</el-button>
                  </div>
                </el-form-item>
              </el-form>
            </div>
          </el-col>

          <!-- comments -->
          <el-col :span="14" class="full-height">
            <p class="comment-title">Comments</p>
            <span class="none" v-if="comments.length === 0">None</span>
            <div class="comment-wrapper">
            <el-card class="el-card-comment" shadow="hover" v-for="comment in comments" :key="comment.id">
              <div class="card-body">
                <!-- switch -->
                <el-tooltip :content="comment.isHide ? 'Hide comment' : 'Show comment'" placement="left">
                  <el-switch
                    v-model="comment.isHide"
                    :active-value="false"
                    :inactive-value="true">
                  </el-switch>
                </el-tooltip>
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
            </el-card>
            </div>
          </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import userAPI from '@/api/user.js'
import {ElMessage} from 'element-plus'
import tagName from '@/tags.json'
import faculties from '@/faculties.json'

export default {
  name: 'EditProfile',
  data() {
    return {
      userId: null,
      avatar: null,
      userName: null,
      tagName: tagName,
      faculties: faculties,
      comments: [],
      userInfo: {
        avatar: '',
        selfTag: null
      },
      avatarFile: null,
      uploadAvatar: false,
      rules: {
        firstName: [
          { required: true, message: 'Please enter your first name', trigger: 'blur' },
          { max: 50, message: '0-50 characters', trigger: 'change'}
        ],
        lastName: [
          { required: true, message: 'Please enter your last name', trigger: 'blur' },
          { max: 50, message: '0-50 characters', trigger: 'change'}
        ],
        faculty: [
          { required: true, message: 'Please select a faculty', trigger: 'change' }
        ],
        degree: [
          { required: true, message: 'Please enter your degree', trigger: 'change' },
          { max: 50, message: '0-50 characters', trigger: 'change'}
        ],
        description: [
          { max: 300, message: '0-300 characters', trigger: 'change'}
        ]
      }
    }
  },
  created() {
    this.userId = this.$store.getters.userId
    let _this = this
    if (this.userId != null) {
      userAPI.getUserInfo(this.userId).then((res) => {
        let data = res.data.data
        _this.userName = data.firstName + ' ' + data.lastName
        _this.comments = data.comment
        _this.userInfo = data

        if (data.avatar == null) {
          _this.avatar = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        } else {
          userAPI.getAvatar(data.avatar).then((res) => {
            _this.avatar = 'data:image/jpeg;base64,' + res.data.data.image
          }).catch(() => {
            ElMessage.error('Failed to load the avatar')
          })
        }

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
    handleUploadAvatar(file) {
      const validType = file.raw.type === 'image/jpeg' || file.raw.type === 'image/jpg' || file.raw.type === 'image/png';
      const isLt10M = file.raw.size / 1024 / 1024 < 10;

      let valid = true
      if (!validType) {
        this.$message.error('Support file type: JPG, PNG, JPEG')
        valid = false
      }
      if (!isLt10M) {
        this.$message.error('Exceed maximum file size 10MB!')
        valid = false
      }

      if (valid) {
        this.avatar = URL.createObjectURL(file.raw)
        this.avatarFile = file.raw
        this.uploadAvatar = true
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // if updated avatar
          if (this.uploadAvatar) {
            this.userInfo['updateAvatar'] = true
            if (this.userInfo.selfTag === 'None') {
              this.userInfo.selfTag = null
            }

            // generate form data
            let formData = new FormData();
            formData.append("file", this.avatarFile);

            userAPI.uploadAvatar(formData).then((res) => {
              let path = res.data.data.path
              this.userInfo['oldAvatar'] = this.userInfo.avatar
              this.userInfo.avatar = path

              userAPI.updateUserInfoById(this.$store.getters.userId, this.userInfo).then(() => {
                ElMessage.success('Updated successfully!')
                this.$emitter.emit('updateHeaderAvatar', this.avatar)
                this.$router.push('/myProfile')
              }).catch(() => {
                ElMessage.error('Failed to modify the profile.')
              })

            }).catch(() => {
              ElMessage.error('Failed to upload the avatar.')
            })

          // if not updated avatar
          } else {
            this.userInfo['updateAvatar'] = false
            userAPI.updateUserInfoById(this.$store.getters.userId, this.userInfo).then(() => {
              ElMessage.success('Updated successfully!')
              this.$router.push('/myProfile')
            }).catch(() => {
              ElMessage.error('Failed to modify the profile.')
            })
          }

        } else {
          ElMessage.error('Failed to update your profile!')
          return false;
        }
      });
      // location.reload()
    },
    handleCancel() {
      ElMessage.info('Modification cancelled.')
      this.$router.push('/myProfile')
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
  //height: 100%;
  overflow: auto;
}
.card-box {
  height: 100%;
}
:deep(.el-card__body) {
  height: 100%;
}
.none {
  margin-left: 30px;
  color: #B5B5B5;
  font-weight: 700;
  line-height: 35px;
}
.info-box {
  border-right: solid #E8E8E8 1.5px;

  & .el-avatar{
    display: block;
    margin: 0 auto;
    margin-top: 5%;
  }
  & .avatar-uploader {
    padding-top: 15px;
    text-align: center;
  }

  & .info-form {
    margin-top: 40px;
    width: 90%;
    & .input-width {
      width: 235px;
    }
  }
  & .btns {
    width: 100%;
    text-align: right;
    padding-top: 5px;

    & .el-button {
      margin-left: 8px;
    }
  }
}
.comment-wrapper {
  height: 90%;
  overflow: auto;
}
.comment-title {
  font-size: 20px;
  font-weight: 600;
  padding: 10px 0 5px 25px;
}
.el-card-comment {
  width: 95%;
  margin: 0 auto;
  margin-top: 10px;
  position: relative;

  & .el-switch {
    position: absolute;
    right: 32px;
    top: 42%;
  }
  & .card-body {
    display: flex;
    width: 100%;
    & .avatar {
      width: 80px;
      margin-left: 10px;
      margin-right: 5px;
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