<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <el-header class="header">
        <div>
          <span>{{group.course}}_Group{{group.name}}</span>&nbsp;
          <el-tag class="ml-2" type="warning" round>{{group.course}}</el-tag>&nbsp;&nbsp;
          <span class="grey_tag">{{group.tutorial}}</span>
        </div>
        <span class="grey_tag">Capacity: {{group["number of students"]}}/{{group.capacity}}</span>
      </el-header>
      <el-container>
      <el-aside width="270px">
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
          <el-header class="header">
            <span class="title">Members</span>
            <el-button type="warning" v-if="is_member == true" @click="handleLeave">Leave</el-button>
            <el-button type="primary" v-else @click="handleSendJoinRequest" class="rightBtn">Send Join Request</el-button>
          </el-header>
          <el-main>
            <el-scrollbar height="300px">
              <div v-for="member in members" :key="member.id">
                <el-card class="member_card">
                  <div class="member_div left">
                    <el-avatar class="member_avatar" src={{member.filename}} @error="errorHandler">
                      <img
                          src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                      />
                    </el-avatar>
                  </div>
                  <div class="member_div middle">
                    <div>
                      <span class="student_name_bold">{{member.name}}</span> &nbsp;
                      <el-tag class="ml-2" type="danger" effect="dark" round v-if="member.is_leader">LEADER</el-tag>
                    </div>
                    <p class="student_email">{{member.email}}</p>
                    <p class="student_degree">{{member.degree}}</p>
                  </div>
                  <div class="member_div right">
                    <el-button type="primary" :disabled="is_comment_available === false || member.id === user_id" @click="handleComment(member)">Comment</el-button>
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
          <el-select v-model="comment_form.tag" placeholder="Please select a tag">
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

      <el-scrollbar height="200px">
        <div v-for="student in searchResult" :key="student.id">
          <el-card class="student_card">
            <div class="student_div">
              <el-avatar class="student_avatar" src={{student.filename}} @error="errorHandler">
                <img
                    src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                />
              </el-avatar>
              <span class="student_name"> {{student.name}} </span>
              <span class="inviteBtn">
                <el-button type="warning" @click="handleInvitationConfirm(student)">Invite</el-button>
              </span>
            </div>
          </el-card>
          <div class="divider_space"></div>
        </div>
      </el-scrollbar>

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
// import GroupAPI from '../api/group.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import json from '../tags.json'

export default {
  name: 'GroupDetails',
  props: {
    group_id: Object
  },
  data() {
    return {
      user_id: null,
      is_leader: false,
      is_member: false,
      is_comment_available: false,
      group: {
        "name": 1,
        "course": "ELEC5619",
        "description": "Good",
        "id": 1,
        "tutorial": "CC02",
        "number of students": 2,
        "capacity": 3
      },
      members: [
        {
          "filename": null,
          "name": "Yuyun Liu",
          "degree": "Bachelor of Engineering",
          "id": 1,
          "is_leader": true,
          "email": "devu0001@uni.sydney.edu.au"
        },
        {
          "filename": null,
          "name": "admin null",
          "degree": null,
          "id": 2,
          "is_leader": false,
          "email": "jointeamsspring@gmail.com"
        }
      ],
      outerVisible_comment: false,
      current_comment_member: null,
      comment_form: {
        groupId: null,
        receiverId: null,
        senderId: null,
        tag: null,
        content: null
      },
      tags: json,
      outerVisible_invitation: false,
      search_term: "",
      students: [
        {
          "filename": null,
          "name": "Jin Ma",
          "id": 5
        },
        {
          "filename": null,
          "name": "Alice Cole",
          "id": 9
        },
        {
          "filename": null,
          "name": "Zam Samern",
          "id": 11
        }
      ],
      searchResult: []
    }
  },
  mounted() {
    // var userId = cookieUtils.getTokenInCookies()
    // console.log(userId)
    this.user_id = 1
    // TODO
    // this.group = ...
    // console.log(this.user_id)
    if(this.$router.options.history.state.back == "/myGroups") {
      this.is_member = true
    }
    this.is_leader = true
    this.is_comment_available = true
    this.comment_form.groupId = this.group_id
    this.comment_form.senderId = this.user_id
  },
  methods: {
    errorHandler() {
      return true
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
        ElMessage({
          type: 'success',
          message: 'Leave group',
        })
        this.$router.push(this.$router.options.history.state.back)
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Leave cancel',
        })
      })
    },
    handleEditDescription() {
      ElMessage({
        message: 'Edit description successfully!',
        type: 'success',
        offset: -5
      })
    },
    handleComment(member) {
      this.outerVisible_comment = true
      this.current_comment_member = member
      // Should get from cookie.
      // var senderId = 1
      this.comment_form.receiverId = member.id

      // Check whether comment exist --> fill the content and tag
      this.comment_form.content = "exist one"
      this.comment_form.tag = this.tags[1-1]["value"]
    },
    handleCommentCancel() {
      this.outerVisible_comment = false
      this.comment_form.content = null
      this.comment_form.tag = null
    },
    handleCommentConfirm() {
      // this.innerVisible_comment = true
      ElMessageBox.confirm(
          'You will submit a comment. Continue?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        ElMessage({
          type: 'success',
          message: 'Submit comment!',
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
        ElMessage({
          type: 'success',
          message: 'Invitation sent!',
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
        ElMessage({
          type: 'success',
          message: 'Request sent!',
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Cancel Request',
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
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
  .member_card:after {
    content: "";
    display: table;
    clear: both;
  }
  .student_card {
    height: 60px;
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
    font-width: bold;
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
    width: 250px;
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
</style>