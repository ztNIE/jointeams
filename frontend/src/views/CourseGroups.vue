<template>
  <div class="common-layout">
    <el-card class="box-card">
      <template #header>
        <el-row :gutter="20">
          <el-col :span="24" class="course-name">
            <span class="bold">{{ course.code }}</span> {{ course.name }}
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24" class="tutorial">
            <div>
              My Tutorial/Lab:
              <el-input v-model="tutorialNumber"
                        style="width: 110px"
                        size="small"
                        :maxlength="2"
                        :disabled="!isEditing"
                        class="tut-input"
              >
                <template #prepend>
                  <el-select v-model="tutorialType"
                             placeholder=" "
                             style="width: 60px; font: inherit"
                             size="small"
                             :disabled="!isEditing"
                  >
                    <el-option label="RE" value="RE"/>
                    <el-option label="CC" value="CC"/>
                  </el-select>
                </template>
              </el-input>
              <el-button type="warning"
                         size="small"
                         v-if="!isEditing"
                         @click="toggleIsEditing"
                         class="edit-btn"
              >Edit
              </el-button>
              <el-button type="primary"
                         size="small"
                         v-else
                         @click="editTutorial()"
              >OK
              </el-button>
            </div>
          </el-col>
        </el-row>
      </template>
      <el-main>
        <div>
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item class="search-term">
              <el-input class="search-input" placeholder="Enter group name" v-model="search_term"/>
            </el-form-item>
            <el-form-item class="search-btn">
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
            <el-card class="group-card" @click="handleToDetail(group.group_id)" shadow="hover">
              <el-icon id="detailBtn-icon">
                <Right/>
              </el-icon>
              <div class="card-header">
                <span id="name">{{ group.group_name }}</span>
                <span id="capacity">(Capacity: {{ group.capacity }})</span>
              </div>
              <div class="divider_space"></div>
              <div class="card-content">
                <span v-for="member in group.members" :key="member.name" class="member">
                  <el-avatar class="member_avatar" :size="40" :src="getAvatar(member.id)" />
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
import courseDetailAPI from "@/api/courseDetail";
import userAPI from "../api/user";

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
      members: [],
      search_term: "",
      search_tut: "",
      user_id: null,
      searchResult: [],
      dialogFormVisible: false,
      group_capacity: 1,
      tutorialType: null,
      tutorialNumber: null,
      isEditing: false
    }
  },
  computed: {
    tutorial() {
      return this.tutorialType + this.tutorialNumber;
    },
  },
  async created() {
    this.user_id = this.$store.getters.userId
    this.course.id = this.$route.params.course_id

    CourseGroupAPI.getTutorial(this.user_id, this.course.id).then((res) => {
      if (res !== null) {
        let tutorial = res.data.data.tutorial
        this.tutorialType = tutorial.substring(0,2)
        this.tutorialNumber = tutorial.substring(2)
      }
    })

    CourseGroupAPI.getAllGroupsInOneCourse(this.course.id, this.user_id).then(async (res) => {
      if (res.data.data === null) {
        this.groups = []
      } else {
        await this.handleGetAvatar(res)
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
    // handleEditTutorial() {
    //   CourseGroupAPI.setTutorial(this.user_id, this.course.id, this.tutorial).then((res) => {
    //     ElMessage({
    //       type: res.data.msg,
    //       message: "Edit success!",
    //     })
    //   })
    // },
    toggleIsEditing() {
      this.isEditing = true;
    },
    async editTutorial(external = false) {
      try {
        if (!this.tutorialType || !this.tutorialNumber || this.tutorialNumber.length === 0) {
          throw new Error("Please enter tutorial information")
        }
        if (this.tutorialNumber.length === 1) {
          this.tutorialNumber = "0" + this.tutorialNumber
        }
        await courseDetailAPI.putSetTutorial(this.course.id, this.user_id, this.tutorial)
        this.isEditing = false
        ElMessage({
          type: "success",
          message: `Set tutorial successfully`
        })
      } catch (error) {
        if (!external) {
          ElMessage({
            type: "info",
            message: error.message
          })
        } else {
          throw error
        }
      }
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
            CourseGroupAPI.getAllGroupsInOneCourse(this.course.id, this.user_id).then(async (res) => {
              if (res.data.data === null) {
                this.groups = []
              } else {
                await this.handleGetAvatar(res)
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
          this.members.push(data[i].members[j])
        }
      }
      this.groups = data
    },
    getAvatar(id) {
      for(let i = 0; i < this.members.length; i++) {
        if(this.members[i].id === id) {
          return this.members[i].avatar
        }
      }
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
  margin-bottom: 15px;
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
  position: absolute;
  top: 40%;
  right: 20px;
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
  vertical-align: middle;
  margin-right: 10px;
}

.member_name {
  font-size: 16px;
  position: relative;
  top: 3px;
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

.sub_header {
  color: black;
  font-weight: normal;
  font-size: x-large;
}

.box-card {
  min-height: 580px;
  height: 100%;
}

.group-card:hover {
  background-color: #CBF3F0;
  cursor: pointer;
}

.group-card {
  position: relative;
  margin-bottom: 5px;
}

.el-form-item.asterisk-left.tutorial_form {
  margin-bottom: 0;
}

.createGroupBtn {
  text-align: center;
}

.bold {
  font-weight: bold;
}

.course-name {
  font-size: 24px;
}

.tutorial {
  font-size: 16px;
  padding-top: 10px;
}

.edit-btn {
  margin-left: 5px;
}

.common-layout {
  height: 100%;
}

.search-term {
  margin-right: 8px;
}

.search-btn {
  margin-right: 40px;
}

.search-input {
  width: 200px;
}
</style>