<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">Comment</div>
      </template>
      <el-input
          style=""
          id="search-input"
          v-model=input
          class="w-50 m-2"
          placeholder="Enter context of comments"
      />
      <el-button type="primary" @click="searchComments()" >Search</el-button>
      <el-main>
        <el-scrollbar max-height="400px" v-if="commentsAfterSearch.length !== 0">
          <div v-for="comment in commentsAfterSearch" :key="comment.id" class="text item">
            <el-card class="comment-card" shadow="hover">
              <p class="time-stamp">{{parseTime(comment)}}</p>
              <div class="content">
                <div class="first-line">
                  <p class="sender-name"><span class="name">{{comment.senderName}}</span></p>
                </div>
                <p v-if="comment.tag != null">gives {{comment.receiverName}} a&nbsp;
                  <el-tag class="mx-1 info-tag" type="warning" size="small">{{findTagByTagType(comment)}}</el-tag>
                </p>
                <p class="comment-content">"{{comment.content}}"</p>
              </div>
              <div>
                <el-button type="info" class="delete" @click="deleteAComment(comment)">Delete</el-button>
              </div>
            </el-card>
            <div class="divider_space"></div>
          </div>
        </el-scrollbar>
        <el-empty description="No comment" v-else />
      </el-main>
    </el-card>
  </div>
</template>


<script >
import { parseTime } from '@/util/ParseTime'
import adminAPI from '../../api/admin.js'
import { ElMessage, ElMessageBox } from 'element-plus'
// import authUtil from "@/util/authUtil";
import json from '../../tags.json'


export default {
  name: 'Comment',
  beforeCreate() {
    // authUtil.authenticateIdentity("ROLE_ADMIN")
  },
  data() {
    return {
      labels: ['From:', 'To:'],
      input: '',
      comments: [],
      commentsAfterSearch: []
    }
  },
  mounted() {
    adminAPI.findAllComments().then((res) => {
      if(res.data.data['Null'] !== null) {
        this.comments = res.data.data.CommentResponseDataList
        this.commentsAfterSearch = this.comments
      }
    })
  },
  methods: {
    parseTime(notification)
    {
      return parseTime(notification.timestamp)
    },
    deleteAComment(comment){
      ElMessageBox.confirm(
            'You are going to delete this comment?',
            'Warning',
            {
              confirmButtonText: 'Confirm',
              cancelButtonText: 'Cancel',
              type: 'warning',
            }
        ).then(() => {
          adminAPI.deleteAComment(comment.id).then((res) => {
            if(res !== null)
            {
              ElMessage({
                type: 'success',
                message: res.data.msg,
              })
              let index = this.comments.indexOf(comment)
              this.comments.splice(index, 1)
              let index2 = this.commentsAfterSearch.indexOf(comment)
              if(index2 !== -1)
                this.commentsAfterSearch.splice(index2, 1)
            }
          })
        }).catch(() => {
          ElMessage({
            type: 'info',
            message: 'Delete is cancelled',
          })
        })
    },
    searchComments(){
      this.commentsAfterSearch = this.comments.filter(
          comment => comment.content.match(this.input))
    },
    findTagByTagType(comment){
      return json[comment.tag-1].label
    }
  }
}
</script>

<style lang="scss" scoped>
.common-layout {
  background-color: #CBF3F0;
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  display: block;
  justify-content: space-between;
  align-items: center;
}

#name {
  font-size: large;
  color: #232323;
  font-weight: bold;
}

.divider_space {
  height: 6px;
}

.main_header {
  color: black;
  font-weight: bold;
  font-size: xx-large;
  font-weight: 500;
  font-size: 23px;
}

.box-card {
  min-height: 580px;
  height: 100%;
}

// .comment-card:hover {
//   background-color: #CBF3F0;
// }

.comment-card{
  min-height: 120px;
  display: flex;
  position: relative;
  margin-bottom: 3px;
}

.el-input{
  width: 500px;
  margin-right: 20px;
}

#label{
  font-size: 20px;
  min-width: 80px;
  color: #f99827;
  display:inline-block;
}

#time{
  font-size: 16px;
  color: gray;
}

#is-hidden{
  font-size: 16px;
  color: gray;
}

#content{
  font-size: 20px;
  min-width: 1000px;
  color: black;
}

.delete {
  position: absolute;
  right: 26px;
  bottom: 40px;
}

.content {
  width: 800px;

  & .first-line {
    content: '';
    display: table;
    clear: both;
    // width: 100%;

    & .sender-name {
      float: left;

      & .name {
        font-weight: 600;
      }
    }
  }

  & .comment-content {
    font-style: italic;
    padding-top: 7px;
  }
}

.time-stamp {
  // float: right;
  font-size: 13px;
  font-weight: 500;
  color:#828282;
  position: absolute;
  right: 26px;
}
</style>