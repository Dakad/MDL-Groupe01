<!-- Original Source :  https://github.com/creativetimofficial/vue-material-kit/blob/a7791837dce5d5e80aea1b93e835644b2e6eb4c6/src/components/Pagination.vue -->
<!-- Original LICENSE : https://github.com/creativetimofficial/vue-material-kit/blob/a7791837dce5d5e80aea1b93e835644b2e6eb4c6/LICENSE.md -->

<template>
  <ul class="pagination">
    <li class="page-item prev-page" :class="{ disabled: value === 1, 'no-arrows': noArrows }">
      <md-button
        :class="{'md-icon-button' : !withText}"
        :md-ripple="true"
        :disabled="value === 1"
        aria-label="Previous"
        @click="prevPage()"
      >
        <template v-if="withText">Prev</template>
        <md-icon v-else>keyboard_arrow_left</md-icon>
      </md-button>
    </li>
    <li v-for="item in range(minPage, maxPage)" :key="item">
      <md-button
        class="md-icon-button"
        :class="{'md-accent' : value === item, 'md-raised' : value === item}"
        :md-ripple="true"
        @click="changePage(item)"
      >{{ item }}</md-button>

      <a class="page-link"></a>
    </li>
    <li
      class="page-item page-pre next-page"
      :class="{ disabled: value === totalPages, 'no-arrows': noArrows }"
    >
      <md-button
        :class="{'md-icon-button' : !withText, disabled: value === totalPages}"
        :disabled="value === totalPages"
        :md-ripple="true"
        aria-label="Next"
        @click="nextPage()"
      >
        <template v-if="withText">Next</template>
        <md-icon v-else>keyboard_arrow_right</md-icon>
      </md-button>
    </li>
  </ul>
</template>


<script>
export default {
  name: "Pagination",
  props: {
    withText: Boolean,
    noArrows: Boolean,
    pageCount: {
      type: Number,
      default: 0
    },
    perPage: {
      type: Number,
      default: 10
    },
    total: {
      type: Number,
      default: 0
    },
    value: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      defaultPagesToDisplay: 5
    };
  },
  computed: {
    totalPages() {
      if (this.pageCount > 0) return this.pageCount;
      if (this.total > 0) {
        return Math.ceil(this.total / this.perPage);
      }
      return 1;
    },
    pagesToDisplay() {
      if (this.totalPages > 0 && this.totalPages < this.defaultPagesToDisplay) {
        return this.totalPages;
      }
      return this.defaultPagesToDisplay;
    },
    minPage() {
      if (this.value >= this.pagesToDisplay) {
        const pagesToAdd = Math.floor(this.pagesToDisplay / 2);
        const newMaxPage = pagesToAdd + this.value;
        if (newMaxPage > this.totalPages) {
          return this.totalPages - this.pagesToDisplay + 1;
        }
        return this.value - pagesToAdd;
      } else {
        return 1;
      }
    },
    maxPage() {
      if (this.value >= this.pagesToDisplay) {
        const pagesToAdd = Math.floor(this.pagesToDisplay / 2);
        const newMaxPage = pagesToAdd + this.value;
        if (newMaxPage < this.totalPages) {
          return newMaxPage;
        } else {
          return this.totalPages;
        }
      } else {
        return this.pagesToDisplay;
      }
    }
  },

  methods: {
    range(min, max) {
      let arr = [];
      for (let i = min; i <= max; i++) {
        arr.push(i);
      }
      return arr;
    },
    changePage(item) {
      this.$emit("input", item);
    },
    nextPage() {
      if (this.value < this.totalPages) {
        this.$emit("input", this.value + 1);
      }
    },
    prevPage() {
      if (this.value > 1) {
        this.$emit("input", this.value - 1);
      }
    }
  },
  watch: {
    perPage() {
      this.$emit("input", 1);
    },
    total() {
      this.$emit("input", 1);
    }
  }
};
</script>


<style lang="css" scoped>
.page-link {
  position: relative;
  display: block;
  padding: 0.5rem 0.75rem;
  margin-left: 0;
  line-height: 1.25;
  color: #2196f3;
  background-color: transparent;
  border: 0 solid #dee2e6;
}

.no-arrows {
  display: none;
}

.pagination {
  display: flex;
  padding-left: 0;
  list-style: none;
  border-radius: 0.25rem;
}
.pagination .page-item > .page-link,
.pagination .page-item > span {
  border: 0;
  border-radius: 30px !important;
  transition: all 0.3s;
  margin: 3px;
  padding: 3px;
  min-width: 30px;
  height: 30px;
  line-height: 30px;
  font-weight: 400 default;
  font-size: 12px default;
  text-transform: uppercase;
  background: transparent;
  text-align: center;
  cursor: pointer;
}
</style>
