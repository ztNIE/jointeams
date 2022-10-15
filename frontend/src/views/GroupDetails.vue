<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="header">
          <div>
            <span class="main_header">{{group.course}}_Group{{group.name}}</span>&nbsp;
            <el-tag class="ml-2 tag" type="warning" round>{{group.course}}</el-tag>&nbsp;
            <span class="grey_tag">{{group.tutorial}}</span>
          </div>
          <span class="grey_tag">Capacity: {{group["number of students"]}}/{{group.capacity}}</span>
        </div>
      </template>
      <el-container>
      <el-aside width="300px">
        <div class="aside_div">
          <div class="title aside">Team board</div>
          <div class="divider_space"></div>
          <el-input
              v-model="group.description"
              :rows="10"
              type="textarea"
              :placeholder=group.description
              :disabled="is_leader === false"
          />
          <div class="divider_space"></div>
          <el-button type="primary" v-if="is_leader" @click="handleEditDescription" class="rightBtn">Edit</el-button>
        </div>

      </el-aside>
      <el-main>
        <el-container>
          <div class="header">
            <span class="title">Members</span>
            <el-button type="warning" v-if="is_member == true" @click="handleLeave">Leave</el-button>
            <el-button type="primary" v-else @click="handleSendJoinRequest" class="rightBtn">Send Join Request</el-button>
          </div>
          <el-main>
            <el-scrollbar height="300px">
              <div v-for="member in members" :key="member.id">
                <el-card class="member_card" @click="handleToProfile(member.id)" shadow="hover">
                  <div class="member_div">
                    <el-avatar class="member_avatar" :src="member.filename" :size="45" />
                  </div>
                  <div class="member_div middle">
                    <div>
                      <span class="student_name_bold">{{member.name}}</span> &nbsp;
                      <el-tag class="ml-2 leader" type="danger" effect="dark" round v-if="member.is_leader">LEADER</el-tag>
                    </div>
                    <p class="student_email">{{member.email}}</p>
                    <p class="student_degree">{{member.degree}}</p>
                  </div>
                  <div class="member_div comment-btn">
                    <el-button type="primary" :disabled="is_comment_available === false || member.id === user_id || is_member === false" @click="handleComment(member)" @click.stop="handleToProfile()">Comment</el-button>
                  </div>
                </el-card>
                <div class="divider_space"></div>
              </div>
            </el-scrollbar>
          </el-main>
          <el-footer>
            <el-button type="primary" v-if="is_leader" @click="handleSendInvitation" class="rightBtn">Send Invitation</el-button>
          </el-footer>
        </el-container>
      </el-main>
      </el-container>
    </el-card>
  </div>

  <el-dialog v-model="outerVisible_comment">
    <template #header>
      <div class="my-header">
        <h3>Give comment to {{current_comment_member.name}}</h3>
      </div>
    </template>
    <template #default>
      <el-form :model="comment_form">
        <el-form-item label="Tag" label-width="90px">
          <el-select v-model="comment_form.tag_id" placeholder="Please select a tag">
            <el-option
                v-for="item in tags"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="*Comment" label-width="90px">
          <el-input v-model="comment_form.content" autocomplete="off" type="textarea" :rows="5"/>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleCommentCancel">Cancel</el-button>
        <el-button type="primary" @click="handleCommentConfirm">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="outerVisible_invitation">
    <template #header>
      <div class="my-header">
        <h3>Send Invitation</h3>
      </div>
    </template>
    <template #default>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input placeholder="Enter student's name" v-model="search_term"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">Search</el-button>
        </el-form-item>
      </el-form>

      <el-scrollbar height="200px" v-if="this.students !== null">
        <div v-for="student in searchResult" :key="student.id">
          <el-card class="student_card">
            <div class="student_div">
              <el-avatar class="student_avatar" :src="student.filename" :size="45" />
              <span class="student_name"> {{student.name}} </span>
              <span class="inviteBtn">
                <el-button type="warning" @click="handleInvitationConfirm(student)">Invite</el-button>
              </span>
            </div>
          </el-card>
          <div class="divider_space"></div>
        </div>
      </el-scrollbar>
      <el-empty description="No student in this course has no group." v-else />

    </template>
    <template #footer>
      <div class="dialog-footer">
          <el-button type="primary" @click="handleInvitationCancel">Done</el-button>
      </div>
    </template>
  </el-dialog>

</template>
<script>
// import cookieUtils from '../utils/cookie.js'
import GroupAPI from '../api/group.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import json from '../tags.json'
import userAPI from "../api/user";

export default {
  name: 'GroupDetails',
  data() {
    return {
      user_id: null,
      is_leader: false,
      is_member: false,
      is_comment_available: false,
      group: [],
      members: [],
      outerVisible_comment: false,
      current_comment_member: null,
      comment_form: {
        groupId: null,
        receiverId: null,
        senderId: null,
        tag: null,
        tag_id: null,
        content: null
      },
      tags: json,
      outerVisible_invitation: false,
      search_term: "",
      students: [],
      searchResult: []
    }
  },
  mounted() {
    this.user_id = parseInt(this.$store.getters.userId)

    GroupAPI.getGroupInformationById(this.$route.params.group_id).then((res) => {
      this.group = res.data.data

      GroupAPI.listStudentsNotInAGroup(this.group.course_id).then((res) => {
        this.students = res.data.data

        for (let i = 0; i < this.students.length; i++) {
          if(this.students[i].filename == null) {
            this.students[i].filename = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          } else {
            userAPI.getAvatar(this.students[i].filename).then((res) => {
              this.students[i].filename = 'data:image/jpeg;base64,' + res.data.data.image
            }).catch(() => {
              ElMessage.error('Fail to load the avatar')
            })
          }
        }
      })
    })

    GroupAPI.getAllMembers(this.$route.params.group_id).then((res) => {
      // We have at least one member, that is the leader.
      this.members = res.data.data

      for(let i = 0; i < this.members.length; i++) {
        if(this.members[i].is_leader === true) {
          if(this.members[i].id === this.user_id) {
            this.is_leader = true
            break
          }
          break
        }
      }

      for(let i = 0; i < this.members.length; i++) {
        if(this.members[i].id === this.user_id) {
          this.is_member = true
          break
        }
      }

      for (let i = 0; i < this.members.length; i++) {
        if(this.members[i].filename == null) {
          this.members[i].filename = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        } else {
          userAPI.getAvatar(this.members[i].filename).then((res) => {
            this.members[i].filename = 'data:image/jpeg;base64,' + res.data.data.image
          }).catch(() => {
            ElMessage.error('Fail to load the avatar')
          })
        }
      }
    })

    GroupAPI.isCommentFunctionOpen().then((res) => {
      this.is_comment_available = res.data.data.isCommentFunctionAvailable
    })

    this.comment_form.groupId = this.$route.params.group_id
    this.comment_form.senderId = this.user_id
  },
  methods: {
    errorHandler() {
      return true
    },
    handleToProfile(user_id) {
      this.$router.push({name: "userProfile", params: {id: user_id}});
    },
    handleLeave() {
      ElMessageBox.confirm(
          'You will be permanently deleted from this group. Continue?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        GroupAPI.leaveGroup(this.$route.params.group_id, this.user_id).then((res) => {
          ElMessage({
            type: 'success',
            message: res.data.msg,
          })
        })
        this.$router.push({name: 'myGroups'})
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Leave cancel',
        })
      })
    },
    handleEditDescription() {
      GroupAPI.updateDescription(this.$route.params.group_id, this.group.description).then(() => {
        ElMessage({
          message: 'Edit description successfully!',
          type: 'success',
          offset: -5
        })
      })
    },
    handleComment(member) {
      this.outerVisible_comment = true
      this.current_comment_member = member

      // Should get from cookie.
      this.comment_form.receiverId = member.id

      // Check whether comment exist --> fill the content and tag
      GroupAPI.checkCommentExistence(this.$route.params.group_id, this.comment_form.senderId, this.comment_form.receiverId).then((res) => {
        if(res.data.data !== null) {
          this.comment_form.content = res.data.data.content
          this.comment_form.tag = this.tags[parseInt(res.data.data.tag)-1]["value"]
          this.comment_form.tag_id = parseInt(res.data.data.tag)
        }
      })
    },
    handleCommentCancel() {
      this.outerVisible_comment = false
      this.comment_form.content = null
      this.comment_form.tag = null
    },
    handleCommentConfirm() {
      ElMessageBox.confirm(
          'You will submit a comment. Continue?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        var request_data = {
          "groupId": this.comment_form.groupId,
          "receiverId": this.comment_form.receiverId,
          "senderId": this.comment_form.senderId,
          "tag": parseInt(this.comment_form.tag_id),
          "content": this.comment_form.content
        }
        GroupAPI.leaveComment(request_data).then((res) => {
          if(res.data.data !== null) {
            ElMessage({
              type: 'success',
              message: 'Submit comment!',
            })
          } else {
            ElMessage({
              type: 'error',
              message: res.data.msg,
            })
          }
        })
        this.outerVisible_comment = false
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Cancel comment!',
        })
      })
    },
    handleSendInvitation() {
      this.outerVisible_invitation = true
      this.searchResult = this.students
    },
    handleInvitationCancel() {
      this.outerVisible_invitation = false
      this.search_term = ""
    },
    handleInvitationConfirm(student) {
      ElMessageBox.confirm(
          'Send an invitation to ' + student.name + '. Continue?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        GroupAPI.sendInvitation(this.$route.params.group_id, student.id).then((res) => {
          if(res.status === 200) {
            ElMessage({
              type: 'success',
              message: res.data.msg,
            })
          } else {
            ElMessage({
              type: 'warning',
              message: res.data.msg,
            })
          }
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Cancel Invitation',
        })
      })
    },
    handleSearch() {
      this.searchResult = []
      if(this.search_term !== "") {
        for (let i = 0; i < this.students.length; i++) {
          if(this.students[i].name.toLowerCase().indexOf(this.search_term.toLowerCase()) !== -1) {
            this.searchResult.push(this.students[i])
          }
        }
        if(this.searchResult.length === 0) {
          this.$message({
            type: "info",
            message: 'No such a student!',
            offset: -5
          })
        }
      } else {
        this.$message({
          type: "warning",
          message: 'Please give a student name before searching.',
          offset: -5
        })
        this.searchResult = this.students
      }
    },
    handleSendJoinRequest() {
      ElMessageBox.confirm(
          'Send an Join Request to ' + this.group.course + "_Group" + this.group.name + '. Continue?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        GroupAPI.sendJoinRequest(this.$route.params.group_id, this.user_id).then((res) => {
          if(res.status === 200) {
            ElMessage({
              type: 'success',
              message: res.data.msg,
            })
          } else {
            ElMessage({
              type: 'warning',
              message: res.data.msg,
            })
          }
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Cancel Request',
        })
      })
    },
    handleGetAvatar(res) {
      let data = res.data.data
      for (let i = 0; i < data.length; i++) {
        for (let j = 0; j < data[i].members.length; j++) {
          if(data[i].members[j].avatar == null) {
            data[i].members[j].avatar = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          } else {
            userAPI.getAvatar(data[i].members[j].avatar).then((res) => {
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
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-left: 10px;
    margin-right: 20px;
  }
  .member_div {
    float: left;
    padding: 10px;
  }
  .left {
    width: 10%;
  }
  .right {
    width: 15%;
  }
  .middle {
    width: 75%;
  }
  :deep(.el-card__body)::after {
    content: "";
    display: table;
    clear: both;
  }
  .member_card:hover {
    cursor: pointer;
  }
  .member_card {
    margin-bottom: 5px;
    position: relative;
  }
  .student_card {
    height: 60px;
  }
  .student_card:hover {
    background-color: #CBF3F0;
  }
  .student_div {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 25px;
  }
  .student_avatar {
    width: 30px;
    height: 30px;
  }
  .student_name {
    font-size: small;
    font-weight: bold;
    font-style: italic;
  }
  .divider_space {
    height: 5px;
  }
  .grey_tag {
    font-size: medium;
    color: #787878;
  }
  .title {
    font-size: x-large;
    font-weight: bolder;
  }
  .aside {
    margin-top: 35px;
  }
  .aside_div {
    width: 270px;
    margin-left: 15px;
  }
  .rightBtn {
    float: right;
  }
  .student_name_bold {
    color: black;
    font-weight: bold;
    font-size: large;
  }
  .student_email {
    color: #787878;
    font-size: small;
  }
  .student_degree {
    color: black;
    font-size: medium;
  }
  .main_header {
    color: black;
    font-weight: 500;
    font-size: 23px;
  }
  .box-card {
    min-height: 550px;
    height: 100%;
  }
  :deep(.el-card__body) {
    height: 90%;
  }
  .el-aside {
    box-shadow: none;
    border-right-style: solid;
    border-color: #e4e7ed;
    border-right-width: 1px;
  }
  .tag {
    position: relative;
    top: -3px;
  }
  .leader {
    position: relative;
    top: -3px;
    left: -3px;
  }
  .common-layout {
    height: 100%;
  }
  .comment-btn {
    position: absolute;
    right: 20px;
    top: 28%;
  }
  .member_avatar {
    margin-right: 8px;
  }
</style>