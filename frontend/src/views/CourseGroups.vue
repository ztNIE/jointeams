<template>
  <div class="common-layout">
    <el-card class="box-card">
      <template #header>
        <div>
          <div>
            <span class="main_header">{{ course.code }}</span>&nbsp;&nbsp;
            <span class="sub_header">{{ course.name }} - Groups</span>
          </div>
          <div class="divider_space"/>
          <div>
            <el-form :inline="true">
              <el-form-item label="My Tutorial/Lab:" class="tutorial_form">
                <el-input v-model="tutorial"/>
              </el-form-item>
              <el-form-item class="tutorial_form">
                <el-button type="warning" @click="handleEditTutorial">Edit</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </template>
      <el-main>
        <div>
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
              <el-input placeholder="Enter group name" v-model="search_term"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearch">Search</el-button>
            </el-form-item>
            <el-form-item>
              <el-form-item label="Tutorial/Lab:" label-width="90px">
                <el-select v-model="search_tut" placeholder="Please select a tutorial">
                  <el-option
                      v-for="tut in getAllTutorials()"
                      :key="tut"
                      :label="tut"
                      :value="tut"
                      @click="handleSelect"
                  />
                </el-select>
              </el-form-item>
            </el-form-item>
          </el-form>
        </div>
        <el-scrollbar max-height="300px" v-if="searchResult.length !== 0">
          <div v-for="group in searchResult" :key="group.group_id" class="text item">
            <el-card class="group-card">
              <div class="card-header">
                <span id="name">{{ group.group_name }}</span>
                <span id="capacity">(Capacity: {{ group.capacity }})</span>
                <el-button @click="handleToDetail(group.group_id)" text>
                  <el-icon id="detailBtn-icon">
                    <Right/>
                  </el-icon>
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
        <el-empty description="No group" v-else/>
      </el-main>
      <el-footer class="createGroupBtn">
        <el-button type="primary" @click="handleCreateGroup">Create Group</el-button>
      </el-footer>
    </el-card>
  </div>
  <el-dialog v-model="dialogFormVisible" title="Create a group">
    <el-form>
      <el-form-item label="Please specify the group capacity:">
        <el-select v-model="group_capacity">
          <el-option
              v-for="i in 10"
              :key="i"
              :label="i"
              :value="i"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleCreateGroupCancel">Cancel</el-button>
        <el-button type="primary" @click="handleCreateGroupConfirm">Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import CourseGroupAPI from '../api/courseGroup.js'
import {ElMessage, ElMessageBox} from 'element-plus'
import authUtil from "@/util/authUtil";
import courseDetailAPI from "@/api/courseDetail";

export default {
  name: 'CourseGroups',
  props: ['course_id'],
  data() {
    return {
      course: {
        code: null,
        name: null,
        id: null
      },
      groups: [],
      tutorial: "",
      search_term: "",
      search_tut: "",
      user_id: null,
      searchResult: [],
      dialogFormVisible: false,
      group_capacity: 1
    }
  },
  beforeCreate() {
    authUtil.authenticateIdentity("ROLE_USER")
  },
  created() {
    this.user_id = this.$store.getters.userId
    this.course.id = this.$route.params.course_id

    CourseGroupAPI.getTutorial(this.user_id, this.course.id).then((res) => {
      if (res !== null) {
        this.tutorial = res.data.data.tutorial
      }
    })

    CourseGroupAPI.getAllGroupsInOneCourse(this.course.id, this.user_id).then((res) => {
      if (res.data.data === null) {
        this.groups = []
      } else {
        this.groups = res.data.data
      }

      this.searchResult = this.groups
    })

    courseDetailAPI.getCourseById(this.course.id).then((res) => {
      if (res.status !== 200) {
        throw new Error(res.msg)
      }
      this.course.code = res.data.code
      this.course.name = res.data.name
    })
  },
  methods: {
    errorHandler() {
      return true
    },
    handleSearch() {
      this.searchResult = []
      if (this.search_term !== "") {
        for (let i = 0; i < this.groups.length; i++) {
          if (this.groups[i].group_name.toLowerCase().indexOf(this.search_term.toLowerCase()) !== -1) {
            this.searchResult.push(this.groups[i])
          }
        }
        if (this.searchResult.length === 0) {
          this.$message({
            type: "info",
            message: 'No such a group!',
            offset: -5
          })
        }
      } else {
        this.$message({
          type: "warning",
          message: 'Please give a group name before searching.',
          offset: -5
        })
        this.searchResult = this.groups
      }
    },
    getAllTutorials() {
      var tutorials = []
      tutorials.push('<All Tutorials>')
      for (let i = 0; i < this.groups.length; i++) {
        var tut = this.groups[i].tutorial
        if (!tutorials.includes(tut)) {
          tutorials.push(tut)
        }
      }
      return tutorials
    },
    handleSelect() {
      this.searchResult = []
      if (this.search_tut !== '<All Tutorials>') {
        for (let i = 0; i < this.groups.length; i++) {
          if (this.groups[i].tutorial.toLowerCase().indexOf(this.search_tut.toLowerCase()) !== -1) {
            this.searchResult.push(this.groups[i])
          }
        }
        if (this.searchResult.length === 0) {
          this.$message({
            type: "info",
            message: 'No such a tutorial!',
            offset: -5
          })
        }
      } else {
        this.searchResult = this.groups
      }
    },
    handleEditTutorial() {
      CourseGroupAPI.setTutorial(this.user_id, this.course.id, this.tutorial).then((res) => {
        ElMessage({
          type: res.data.msg,
          message: "Edit success!",
        })
      })
    },
    handleToDetail(group_id) {
      this.$router.push({name: "groupDetails", params: {group_id: group_id}});
    },
    handleCreateGroup() {
      this.dialogFormVisible = true
    },
    handleCreateGroupConfirm() {
      ElMessageBox.confirm(
          'Create a group in tutorial ' + this.tutorial + '. Continue?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        CourseGroupAPI.addAGroup(this.course.id, this.user_id, this.group_capacity).then((res) => {
          if (res.status === 200) {
            ElMessage({
              type: 'success',
              message: res.data.msg,
            })
            CourseGroupAPI.getAllGroupsInOneCourse(this.course.id, this.user_id).then((res) => {
              if (res.data.data === null) {
                this.groups = []
              } else {
                this.groups = res.data.data
              }
              this.searchResult = this.groups
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
      this.dialogFormVisible = false
    },
    handleCreateGroupCancel() {
      this.dialogFormVisible = false
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
  display: inline-block;
  margin-right: 20px;
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

.sub_header {
  color: black;
  font-weight: normal;
  font-size: x-large;
}

.box-card {
  min-height: 580px;
}

.group-card:hover {
  background-color: #CBF3F0;
}

.el-form-item.asterisk-left.tutorial_form {
  margin-bottom: 0px;
}

.createGroupBtn {
  text-align: center;
}
</style>