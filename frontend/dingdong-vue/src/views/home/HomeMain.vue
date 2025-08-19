<script setup></script>

<template>
  <v-container
    class="d-flex fill-height"
    fluid
  >
    <v-row class="justify-center row-height">
      <v-col
        class="d-flex flex-column justify-space-between"
        cols="5"
      >
        <button class="font-be-vietnam-pro-thin dingdong-text-button">budget plan</button>
        <button class="font-be-vietnam-pro-thin dingdong-text-button">schedule</button>
        <button class="font-be-vietnam-pro-thin dingdong-text-button">etc</button>
      </v-col>

      <v-divider
        class="mx-2"
        vertical
        opacity="0"
      />

      <v-col
        class="d-flex flex-column align-start justify-center"
        cols="5"
      >
        <p>
          <span
            class="d-block"
            style="font-size: 14pt"
          >
            기록하며 기억하는 우리의 '웨딩일기'
          </span>

          <span class="d-block mb-2 font-kaisei-decol-regular">
            WE ARE<br />
            GETTING<br />
            MARRIED
          </span>

          <span class="d-block font-kohi-nanum-otf-light">
            결혼 준비는 '웨딩일기'에서 한 번에 끝내자! 결혼을 준비하는 모든 커플을 응원합니다
          </span>
        </p>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.row-height {
  height: 50%;
}

.font-be-vietnam-pro-thin {
  font-size: 24pt;
}

.font-kaisei-decol-regular {
  font-size: 70pt;
  line-height: 1;
}

.font-kohi-nanum-otf-light {
  font-size: 10pt;
  font-weight: bold;
}
</style>
