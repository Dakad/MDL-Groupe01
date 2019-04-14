<template>
    <div class="sotaProfile">
      <div class="ownedSota">
        <md-content class="md-scrollbar">
          <h7>Owned SotA</h7>
          <sota
            title="Action Movies"
            date="28-03-2019"
            v-bind:authors="['Jean-claude Van Damme', 'Bruce Willis']"
          />
          <sota
            title="Computer Sciences"
            date="20-06-1945"
            v-bind:authors="['Von neuman', 'Alan Turing', 'Ada Lovelace']"
          />
        </md-content>
      </div>
      <div class="followedSota">
        <h7>Followed SotA</h7>
        <md-content class="md-scrollbar">
          <sota
            title="Action Movies"
            date="28-03-2019"
            v-bind:authors="['Jean-claude Van Damme', 'Bruce Willis']"
          />
          <sota
            title="Computer Sciences"
            date="20-06-1945"
            v-bind:authors="['Von neuman', 'Alan Turing', 'Ada Lovelace']"
          />
        </md-content>
      </div>
    </div>
</template>

<script>
  import sota from "@/components/resulat/Sota";

    export default {
      components: {
        sota,
      },
        name: "Sota"
    }
</script>

<style scoped>
  .ownedSota{
  }
  .followedSota{
  }
</style>
