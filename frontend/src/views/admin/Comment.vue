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
        <el-scrollbar max-height="450px" v-if="commentsAfterSearch.length !== 0">
          <div v-for="comment in commentsAfterSearch" :key="comment.id" class="text item">
            <el-card class="comment-card">
              <div class="card-header">
                <div class="from-to">
                  <label id="label">{{labels[0]}}</label>
                  <span id="name">{{comment.senderName}}</span>
                  <br/>
                  <label id="label">{{labels[1]}}</label>
                  <span id="name">{{comment.receiverName}}</span>
                </div>
                <el-button type="info" @click="deleteAComment(comment)" >Delete</el-button>
              </div>
              <div class="card-content">
                <span id="time">Time: {{parseTime(comment)}}</span>
                <br>
                <span id="is-hidden">Is hidden: {{comment.isHidden}}</span>
                <br>
                <label id="label">Tag: </label>
                <span id="tag">{{findTagByTagType(comment)}}</span>
                <br>
                <span id="content">{{comment.content}}</span>
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
import authUtil from "@/util/authUtil";
import json from '../../tags.json'


export default {
  name: 'Comment',
  beforeCreate() {
    authUtil.authenticateIdentity("ROLE_ADMIN")
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
      console.log(this.commentsAfterSearch)
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
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
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
  height: 3px;
}

.main_header {
  color: black;
  font-weight: bold;
  font-size: xx-large;
}

.box-card {
  min-height: 580px;
}

.comment-card:hover {
  background-color: #CBF3F0;
}

.comment-card{
  min-height: 120px;
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
</style>