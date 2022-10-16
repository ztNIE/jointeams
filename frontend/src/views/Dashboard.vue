<template>
  <div class="wrapper" ref="wrapper">
    <el-row :gutter="20" class="full-height">
      <el-col :span="16" class="full-height">
        <el-row :gutter="20" class="el-row-height">
          <el-col :span="24" class="full-height">
            <!-- current course -->
            <el-card class="box-card full-height">
              <template #header>
                <div class="card-header">
                  <span class="card-title">Current Courses</span>
                  <span class="semester">{{ year }}&nbsp;Semester&nbsp;{{ semester }}</span>
                </div>
              </template>
              <el-scrollbar>
                <div class="current-courses-box">
                  <el-card v-for="item in currentCourse" :key="item.id" class="current-course-card" shadow="hover"
                           @click="handleClickCourse(item.id)">
                    <p class="current-code">{{ item.code }}</p>
                    <p class="current-name">{{ item.name }}</p>
                  </el-card>
                </div>
              </el-scrollbar>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="20" class="el-row-height">
          <!-- past course -->
          <el-col :span="12" class="full-height">
            <el-card class="box-card full-height">
              <template #header>
                <div class="card-header">
                  <span class="card-title">Past Courses</span>
                </div>
              </template>
              <ul class="past-courses-box full-height">
                <li class="past-li" v-for="item in pastCourse" :key="item.id" @click="handleClickCourse(item.id)">
                  <el-icon class="icon-book">
                    <Notebook/>
                  </el-icon>
                  <p><span class="past-code">{{ item.code }}</span>:&nbsp;{{ item.name }}</p>
                </li>
              </ul>
            </el-card>
          </el-col>
          <!-- interested course -->
          <el-col :span="12" class="full-height">
            <el-card class="box-card full-height">
              <template #header>
                <div class="card-header">
                  <span class="card-title">Interested Courses</span>
                </div>
              </template>
              <ul class="interested-courses-box full-height">
                <li class="interested-li" v-for="item in interestedCourse" :key="item.id"
                    @click="handleClickCourse(item.id)">
                  <el-icon class="icon-tag">
                    <CollectionTag/>
                  </el-icon>
                  <p><span class="past-code">{{ item.code }}</span>:&nbsp;{{ item.name }}</p>
                </li>
              </ul>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <!-- all courses -->
      <el-col :span="8" class="full-height">
        <el-card class="box-card full-height">
          <template #header>
            <div class="card-header">
              <span class="card-title">All Courses</span>
            </div>
          </template>
          <!-- search -->
          <div class="search-box">
            <el-input v-model="input" placeholder="course code / course name"/>
            <el-button type="warning" @click="handleSearch">Search</el-button>
          </div>
          <ul class="all-courses-box full-height">
            <li class="all-li" v-for="item in searchedAllCourse" :key="item.id" @click="handleClickCourse(item.id)">
              <p><span class="all-code">{{ item.code }}</span>:&nbsp;{{ item.name }}</p>
            </li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAPI from '@/api/user.js';
import courseAPI from '@/api/course.js';
import adminAPI from '@/api/admin.js';
import {ElMessage} from 'element-plus'
import {mapGetters} from "vuex";

export default {
  name: 'Dashboard',
  data() {
    return {
      year: null,
      semester: null,
      currentCourse: [],
      pastCourse: [],
      interestedCourse: [],
      allCourse: [],
      searchedAllCourse: [],
      input: ''
    }
  },
  computed: {
    ...mapGetters(['isUser'])
  },
  created() {

    let _this = this

    // get current, past and interested courses
    userAPI.getUserInfo(this.$store.getters.userId).then((res) => {
      let data = res.data.data
      _this.currentCourse = data.currentCourse.filter((course) => {
        return !course.is_lock
      })
      _this.pastCourse = data.previousCourse.filter((course) => {
        return !course.is_lock
      })
      _this.interestedCourse = data.interestedCourse.filter((course) => {
        return !course.is_lock
      })

    }).catch((err) => {
      ElMessage.error(err.data.msg)
    })

    // get all courses
    courseAPI.getAllCourse(this.$store.getters.userId).then((res) => {
      let data = res.data.data
      _this.allCourse = data.allCourse.filter((course) => {
        return !course.is_lock
      })
      _this.searchedAllCourse = this.allCourse
    }).catch((err) => {
      ElMessage.error(err.data.msg)
    })

    // get current semester
    adminAPI.getCurrentSemester().then((res) => {
      let data = res.data.data
      _this.year = data.Semester.year
      _this.semester = data.Semester.semesterNumber
    }).catch((err) => {
      ElMessage.error(err.data.msg)
    })
  },
  methods: {
    handleClickCourse(courseId) {
      this.$router.push(`/courseDetails/${courseId}`)
    },
    handleSearch() {
      let _this = this
      this.searchedAllCourse = this.allCourse.filter(function (item) {
        if (_this.input.toLowerCase().trim() !== '') {
          return item.code.toLowerCase().indexOf(_this.input.toLowerCase().trim()) != -1 || item.name.toLowerCase().indexOf(_this.input.toLowerCase().trim()) != -1
        } else {
          return true
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.full-height {
  height: 100%;
}

.el-row-height {
  height: 49%;
}

:deep(.el-card__body) {
  box-sizing: border-box;
  height: 90%;
  padding-top: 15px;
  padding-bottom: 15px;
}

.left {
  float: left;
}

.right {
  float: right;
}

.clear-fix:after {
  content: '';
  display: block;
  clear: both;
}

.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  & .card-title {
    font-size: 20px;
    font-weight: 600;
  }

  & .semester {
    font-size: 14px;
    color: gray;
  }
}

.wrapper {
  min-width: 1200px;
  height: 100%;
}

.current-courses-box {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;

  & .current-course-card {
    width: 235px;
    margin-bottom: 15px;
    margin-right: 15px;

    & .current-code {
      font-weight: 700;
    }

    & .current-name {
      padding-top: 5px;
    }
  }

  & .current-course-card:hover {
    cursor: pointer;
    background-color: #2EC4B6;
    color: white;
  }
}
.all-courses-box {
  margin-top: 15px;
}
.past-courses-box, .interested-courses-box, .all-courses-box {
  box-sizing: border-box;
  overflow: auto;
  margin-bottom: 10px;
  height: 87%;

  & li {
    padding: 15px 10px;
    display: flex;

    & .icon-book {
      font-size: 22px;
      color: #2EC4B6;
    }

    & .icon-tag {
      font-size: 22px;
      color: #FF9F1C;
    }

    & p {
      min-width: 290px;
      margin-left: 10px;

      & .past-code {
        font-weight: 700;
      }
    }
  }

  & .past-li:hover, .all-li:hover {
    background-color: rgba(203, 243, 240, 50%);
    cursor: pointer;
    transition: 0.5s;
  }

  & .interested-li:hover {
    background-color: rgba(255, 191, 105, 20%);
    cursor: pointer;
    transition: 0.5s;
  }
}

.search-box {
  margin-bottom: 10px;

  & .el-input {
    width: 250px;
  }

  & .el-button {
    margin-left: 10px;
  }
}

.all-code {
  font-weight: 700;
}
</style>
