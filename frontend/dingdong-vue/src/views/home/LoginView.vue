<script setup>
import RouterLinkButton from '@/components/button/RouterLinkButton.vue';
</script>

<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row class="mb-15 justify-center">
      <v-col
        class="text-center"
        cols="12"
        sm="6"
        md="4"
      >
        <h2 class="mb-3 font-kaisei-decol-regular">Login</h2>

        <v-form class="font-kohi-nanum-otf-light">
          <v-text-field
            label="아이디"
            variant="outlined"
            rounded="lg"
            type="text"
          />

          <v-text-field
            label="비밀번호"
            variant="outlined"
            rounded="lg"
            type="password"
          />

          <v-btn
            size="x-large"
            block
            rounded="lg"
            elevation="0"
            color="#c8e6c9"
          >
            로그인
          </v-btn>
        </v-form>

        <div class="mt-2 font-kohi-nanum-otf-light">
          <router-link-button
            class="mx-3"
            text="아이디 찾기"
            to="id/help"
          />

          <router-link-button
            class="mx-3"
            text="비밀번호 찾기"
            to="password/help"
          />

          <router-link-button
            class="mx-3"
            text="회원가입"
            to="signup"
          />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
div.font-kohi-nanum-otf-light {
  font-size: 13px;
}
</style>
