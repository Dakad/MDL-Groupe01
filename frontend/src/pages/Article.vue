<template>
  <div class="article">
    <Navbar></Navbar>
    <Search></Search>
    <h1> {{ articleTitle }} </h1>
    <menuArticle></menuArticle>
    <p> {{ abstract }} </p>
    <infoNav></infoNav>
  </div>
</template>

<script>
  import InfoNav from '@/components/article/InfoNav';
  import MenuArticle from '@/components/article/MenuArticle';
  import Navbar from '@/components/Navbar';

    export default {
        name: "Article",
        components: {
          InfoNav,
          MenuArticle,
          Navbar
      },
      data() {
        return {
          articleTitle: 'Dummy title',
          abstract: 'Dummy Abstract that do nothing but just show you some text because why not'
        };
      },
    }

</script>

