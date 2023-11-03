<script setup>
import dplayer from '@/components/DPlayer.vue'
import Header from '../../components/Header.vue'
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { videoGetService } from '@/api/video.js'
import { likeLikeService } from '@/api/like.js'
import { useUserStore } from '@/stores/index.js'
import { commentSendService, commentGetService } from '@/api/comment.js'
import { fanFollowService } from '@/api/fan.js'
const route = useRoute()
const userStore = useUserStore()
//视频
const video = ref({
  video_name: '',
  video_url: '',
  user: ''
})
const getVideo = async () => {
  const res = await videoGetService(route.query.id)
  video.value = res.data.data
  dplayerObj.value.video.url = video.value.video_url
  like.value = video.value.like
  if (video.value.user.fan) fan.value = video.value.user.fan
  if (like.value.like) {
    likeColor.value = 'rgb(0,174,236)'
  }
}
//点赞
const likeColor = ref('rgb(97,102,109)')
const like = ref({
  video_id: '',
  user_id: '',
  like: ''
})
const onLike = async () => {
  if (like.value.like !== true) {
    like.value.video_id = video.value.id
    like.value.user_id = userStore.user.id
    like.value.like = true
    likeColor.value = 'rgb(0,174,236)'
    video.value.like_count++
  } else {
    like.value.video_id = video.value.id
    like.value.user_id = userStore.user.id
    like.value.like = false
    likeColor.value = 'rgb(97,102,109)'
    video.value.like_count--
  }
  await likeLikeService(like.value)
}
//评论
const comment = ref({
  id: '',
  comment_user_id: '',
  to_user_id: '',
  video_id: '',
  comment_id: '',
  text: ''
})
const sendComment = async () => {
  comment.value.video_id = video.value.id
  await commentSendService(comment.value)
  ElMessage.success('评论成功')
  comment.value.text = ''
  getCommentList()
}
const commentList = ref()
const getCommentList = async () => {
  const res = await commentGetService(route.query.id)
  commentList.value = res.data.data
}
//回复
const reply = ref({
  id: '',
  comment_user_id: '',
  to_user_id: '',
  video_id: '',
  comment_id: '',
  text: ''
})
const onReply = (first, second) => {
  first.displayReply = true
  console.log(first)
  console.log(second)
  reply.value.video_id = video.value.id
  reply.value.comment_id = first.id
  reply.value.to_user_id = first.comment_user_id
  if (second) {
    reply.value.to_user_id = second.comment_user_id
  }
}
const onReplyButton = async () => {
  await commentSendService(reply.value)
  ElMessage.success('回复成功')
  reply.value.text = ''
  getCommentList()
}
//关注
const fan = ref({
  id: '',
  user_id: '',
  fan_id: '',
  status: 0,
  create_time: ''
})
const onFan = async () => {
  fan.value.user_id = userStore.user.id
  fan.value.fan_id = video.value.user.id
  fan.value.status = fan.value.status === 0 ? 1 : 0
  await fanFollowService(fan.value)
}
getVideo()
getCommentList()
//视频配置
const dplayerObj = ref({
  video: {
    url: '', //视频地址
    type: 'mp4'
  },
  danmaku: {
    id: 'BV1Na411r7tN', //视频
    api: 'https://api.mdzz.pro/danmaku/server/entrance/',
    // token: 'tokendemo',
    // maximum: 1000,//弹幕大数量
    // addition: ['http://localhost:8080/api/v1/barrage/get'],
    // user: 'DIYgod',
    // bottom: '15%',
    unlimited: true,
    speedRate: 0.5
  }
})
</script>
<template>
  <div class="common-layout">
    <el-container>
      <el-header height="70px" class="header"><Header></Header></el-header>
      <el-container>
        <!-- <el-aside width="13%"></el-aside> -->
        <el-main class="main">
          <!-- 左侧部分 -->
          <div class="left">
            <!-- 视频名字 -->
            <div class="video-name">
              {{ video.video_name }}
            </div>
            <!-- 视频播放数量 -->
            <div class="play-comment">
              <SvgIcon
                name="play"
                size="17"
                color="rgb(151, 156, 160)"
              ></SvgIcon>
              {{ video.play_count }}
              <!-- 视频评论数量 -->
              <SvgIcon
                name="comment"
                size="17"
                color="rgb(151, 156, 160)"
              ></SvgIcon>
              {{ video.comment_count }} {{ video.create_time }}
            </div>
            <!-- 视频 -->
            <div class="dplayer">
              <dplayer
                :key="video.video_url"
                :video="dplayerObj.video"
                :danmaku="dplayerObj.danmaku"
              />
            </div>
            <!-- 用户行为 -->
            <div class="user-action">
              <span class="like"
                ><SvgIcon
                  name="like"
                  :color="likeColor"
                  @click="onLike"
                  size="40"
                ></SvgIcon>
                <span>{{ video.like_count }}</span>
              </span>
            </div>
            <!-- 视频描述 -->
            <div class="video-deriction">
              <h5 style="font-size: 15px; font-weight: 100; color: #18191c">
                {{ video.video_description }}
              </h5>
            </div>
            <!-- 评论 -->
            <div class="comment">
              <div class="comment-header">
                <span style="font-weight: 400; font-size: 25px">评论</span>
              </div>
              <div class="comment-send">
                <span class="span-center"
                  ><el-avatar :size="40" :src="userStore.user.avatar"
                /></span>
                <el-input
                  resize="none"
                  v-model="comment.text"
                  :rows="2"
                  type="textarea"
                  placeholder="发个评论吧！"
                  style="margin-left: 10px; margin-right: 10px"
                />
                <span class="span-center"
                  ><el-button @click="sendComment" type="primary" size="large">
                    发送</el-button
                  ></span
                >
              </div>
              <!-- 评论列表 -->
              <div class="comment-list">
                <div
                  class="comment-item"
                  v-for="item in commentList"
                  :key="item.id"
                >
                  <!-- 头像 -->
                  <span class="comment-avatar">
                    <el-avatar :src="item.comment_user.avatar"></el-avatar>
                  </span>
                  <!-- 评论主体 -->
                  <span class="comment-main">
                    <div class="comment-user-name">
                      {{ item.comment_user.name }}
                    </div>
                    <div class="comment-text">
                      {{ item.text }}
                    </div>
                    <div class="comment-time">
                      <span> {{ item.create_time }}</span>
                      <a class="reply" @click="onReply(item)">回复</a>
                    </div>
                    <!-- 二级评论 -->
                    <div
                      class="comment-two"
                      v-for="second in item.comment_list"
                      :key="second"
                    >
                      <div class="item-center">
                        <span
                          ><el-avatar
                            :size="30"
                            :src="second.comment_user.avatar"
                          ></el-avatar
                        ></span>
                        <span class="second-comment-user-name">{{
                          second.comment_user.name
                        }}</span>
                        <span class="item-center" style="margin-left: 10px"
                          >回复<a class="second-comment-user-name">{{
                            second.to_user.name
                          }}</a
                          >：</span
                        >
                        <span class="second-comment-text">{{
                          second.text
                        }}</span>
                      </div>
                      <div class="comment-time">
                        <span>{{ second.create_time }}</span>
                        <span
                          ><a class="reply" @click="onReply(item, second)"
                            >回复</a
                          ></span
                        >
                      </div>
                    </div>
                    <!-- 回复 -->
                    <div
                      class="comment-send"
                      style="margin-bottom: 10px"
                      v-if="item.displayReply"
                    >
                      <span class="span-center"
                        ><el-avatar :size="40" :src="userStore.user.avatar"
                      /></span>
                      <el-input
                        ref="replyRef"
                        resize="none"
                        v-model="reply.text"
                        :rows="2"
                        type="textarea"
                        placeholder="发个评论吧！"
                        style="margin-left: 10px; margin-right: 10px"
                      />
                      <span class="span-center"
                        ><el-button
                          @click="onReplyButton"
                          type="primary"
                          size="large"
                        >
                          发送</el-button
                        ></span
                      >
                    </div>
                    <div class="comment-line"></div>
                  </span>
                </div>
              </div>
            </div>
          </div>
          <!-- 右侧部分 -->
          <div class="right">
            <!-- 创作者 -->
            <div class="create-user">
              <el-avatar size="large" :src="video.user.avatar" />
              <div class="name-like">
                <div class="user-name">{{ video.user.name }}</div>
                <el-button
                  v-if="fan.status === 0"
                  @click="onFan"
                  style="width: 100px"
                  type="primary"
                  round
                  >+关注</el-button
                >
                <el-button
                  v-else
                  @click="onFan"
                  style="width: 100px"
                  type="info"
                  round
                  >已关注</el-button
                >
              </div>
            </div>
            <div class="commend-video">
              <ul>
                <li><el-image></el-image></li>
              </ul>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style scoped>
.header {
  line-height: 70px;
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  top: 0;
  z-index: 3;
  box-shadow: 0 2px 4px #00000014;
}
.dplayer {
  /* background-color: #0093ff; */
  width: 100%;
  height: auto;
  aspect-ratio: 16/9;
}
.main {
  margin-top: 70px;
  height: 100%;
  display: flex;
  justify-content: space-around;
  .left {
    width: 60%;
    .video-name {
      height: 50px;
      line-height: 50px;
      font-size: 22px;
      font-weight: 400;
      text-overflow: ellipsis;
      overflow: hidden;
      word-break: break-all;
      white-space: nowrap;
    }
    .play-comment {
      height: 40px;
      color: rgb(151, 156, 160);
      font-size: 15px;
      line-height: 40px;
      word-spacing: 5px;
    }
    .user-action {
      height: 100px;
      line-height: 100px;

      color: rgb(97, 102, 109);
      font-size: 20px;
      border-bottom-style: solid;
      border-bottom-width: 1px;
      border-bottom-color: rgb(97, 102, 109);
      .like {
        width: 100px;
        display: flex;
        justify-content: space-around;
        align-items: center;
      }
    }
    .video-deriction {
      border-bottom-style: solid;
      border-bottom-width: 1px;
      border-bottom-color: rgb(97, 102, 109);
    }
    .comment {
      .comment-header {
        height: 100px;
        line-height: 100px;
      }
      .comment-send {
        display: flex;
        justify-content: space-around;
      }
      .comment-list {
        margin-top: 40px;
        width: 100%;
        .comment-item {
          display: flex;
          width: 100%;
          margin-bottom: 20px;

          .comment-main {
            margin-left: 10px;
            width: 100%;

            .comment-user-name {
              color: #61666d;
              font-size: 12px;
              font-weight: 400;
            }
            .comment-text {
              margin-top: 10px;
              font-size: 16px;
            }
            .comment-time {
              margin-top: 10px;
              font-size: 12px;
              color: #9499a0;
              margin-bottom: 20px;
              .reply {
                margin-left: 4%;
                cursor: pointer;
              }
              .reply:hover {
                color: rgba(87, 127, 184, 1);
              }
            }
            .comment-line {
              border-bottom: solid 1px rgb(227, 229, 231);
            }
            .second-comment-user-name {
              color: #61666d;
              font-size: 12px;
              font-weight: 400;
              margin-left: 10px;
            }
            .second-comment-text {
              margin-left: 10px;
            }
          }
        }
      }
    }
  }
}

.item-center {
  display: flex;
  align-items: center;
}
.span-center {
  display: flex;
  align-items: center;
}
.right {
  height: 100%;
  width: 20%;

  .create-user {
    width: 70%;
    display: flex;
    justify-content: space-around;
    align-items: center;
    .name-like {
      display: flex;
      flex-direction: column;
      align-items: center;
      .user-name {
        margin-bottom: 10px;
        font-size: 14px;
        font-weight: 400;
      }
    }
  }
}
</style>
